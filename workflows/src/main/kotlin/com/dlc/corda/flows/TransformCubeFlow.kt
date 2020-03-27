package com.dlc.corda.flows

import co.paralleluniverse.fibers.Suspendable
import com.dlc.corda.contracts.CubeContract
import com.dlc.corda.utilities.Moves
import com.dlc.corda.states.CubeState
import net.corda.core.contracts.UniqueIdentifier
import net.corda.core.contracts.requireThat
import net.corda.core.flows.*
import net.corda.core.node.services.queryBy
import net.corda.core.node.services.vault.QueryCriteria
import net.corda.core.transactions.SignedTransaction
import net.corda.core.transactions.TransactionBuilder
import net.corda.core.utilities.ProgressTracker

// ***********************
// * Transform Cube Flow *
// ***********************
@InitiatingFlow
@StartableByRPC
class TransformCubeFlow(
        private val cubeId: UniqueIdentifier,
        private val moves: List<Moves>
) : FlowLogic<SignedTransaction>() {
    constructor(cubeId: UniqueIdentifier, move: Moves): this(cubeId, listOf(move))
    constructor(cubeId: UniqueIdentifier, move: String): this(cubeId, listOf(Moves.valueOf(move)))
    override val progressTracker = ProgressTracker()

    @Suspendable
    override fun call(): SignedTransaction {
        // Get access to the notary
        val notary = this.serviceHub.networkMapCache.notaryIdentities.first()

        // Create a transaction builder
        val tb = TransactionBuilder(notary)

        // Retrieve the specified cube state from the vault
        val queryCriteria = QueryCriteria.LinearStateQueryCriteria(linearId = listOf(cubeId))
        val cubeStateAndRef = serviceHub.vaultService.queryBy<CubeState>(queryCriteria).states.single()
        val cubeState = cubeStateAndRef.state.data

        // Apply the appropriate transformations to the retrieved cubeState
        val newCubeState = moves.fold(cubeState) { accCubeState, move -> accCubeState.handleMove(move) }

        // Add the newly issued cube to the ledger
        tb.addInputState(cubeStateAndRef)
        tb.addOutputState(newCubeState)

        // Add the make moves command
        tb.addCommand(CubeContract.Commands.MakeMoves(moves), cubeState.participants.map { it.owningKey })

        // Sign the command
        val ptx = serviceHub.signInitialTransaction(tb)

        // Collect signatures
        val stx = subFlow(CollectSignaturesFlow(ptx, (cubeState.participants - ourIdentity).map { initiateFlow(it) }))

        // Finalize the TX, no need to collect signatures here as we are the only signer
        return subFlow(FinalityFlow(stx, (cubeState.participants - ourIdentity).map { initiateFlow(it) }))
    }
}

@InitiatedBy(TransformCubeFlow::class)
class TransformCubeFlowResponder(val flowSession: FlowSession): FlowLogic<SignedTransaction>() {

    @Suspendable
    override fun call(): SignedTransaction {
        val signedTransactionFlow = object : SignTransactionFlow(flowSession) {
            override fun checkTransaction(stx: SignedTransaction) = requireThat {
                val output = stx.tx.outputs.single().data
                "The output must be a CubeState" using (output is CubeState)
                "The initiating party must be a solver" using (flowSession.counterparty in output.participants)
            }
        }
        val txWeJustSignedId = subFlow(signedTransactionFlow)
        return subFlow(ReceiveFinalityFlow(otherSideSession = flowSession, expectedTxId = txWeJustSignedId.id))
    }
}
