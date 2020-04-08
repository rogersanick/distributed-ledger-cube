package com.dlc.corda.flows

import co.paralleluniverse.fibers.Suspendable
import com.dlc.corda.contracts.CubeContract
import com.dlc.corda.states.CubeState
import net.corda.core.contracts.UniqueIdentifier
import net.corda.core.contracts.requireThat
import net.corda.core.crypto.SecureHash
import net.corda.core.flows.*
import net.corda.core.node.services.queryBy
import net.corda.core.node.services.vault.QueryCriteria
import net.corda.core.transactions.SignedTransaction
import net.corda.core.transactions.TransactionBuilder
import net.corda.core.utilities.ProgressTracker

// *********
// * Flows *
// *********
@StartableByRPC
@InitiatingFlow
class ExitCubeFlow(private val linearId: UniqueIdentifier) : FlowLogic<SecureHash>() {
    override val progressTracker = ProgressTracker()

    @Suspendable
    override fun call(): SecureHash {
        // Get access to the notary
        val notary = this.serviceHub.networkMapCache.notaryIdentities.first()

        // Create a transaction builder
        val tb = TransactionBuilder(notary)

        // Query for the cube state
        val linearStateQueryCriteria = QueryCriteria.LinearStateQueryCriteria(linearId = listOf(linearId))
        val cubeStateResults = serviceHub.vaultService.queryBy<CubeState>(linearStateQueryCriteria)
        val cubeStateAndRef = cubeStateResults.states.single()
        val cubeState = cubeStateAndRef.state.data

        // Add the newly issued cube to the ledger
        tb.addInputState(cubeStateAndRef)

        // Add the issuance command to the ledger
        tb.addCommand(CubeContract.Commands.Exit(), cubeState.solvers.map { it.owningKey })

        // Sign the command
        val ptx = serviceHub.signInitialTransaction(tb)

        // Collect signatures as required
        val sessions = (cubeState.solvers - ourIdentity).map { initiateFlow(it) }
        val stx = subFlow(CollectSignaturesFlow(ptx, sessions))

        // Finalize the TX, no need to collect signatures here as we are the only signer
        return subFlow(FinalityFlow(stx, sessions)).tx.id
    }
}

@InitiatedBy(ExitCubeFlow::class)
class ExitCubeFlowResponder(val flowSession: FlowSession): FlowLogic<SignedTransaction>() {

    @Suspendable
    override fun call(): SignedTransaction {
        val signedTransactionFlow = object : SignTransactionFlow(flowSession) {
            override fun checkTransaction(stx: SignedTransaction) = requireThat {
                "There should be no outputs in an exiting flow" using (stx.tx.outputStates.isEmpty())
            }
        }
        val txWeJustSignedId = subFlow(signedTransactionFlow)
        return subFlow(ReceiveFinalityFlow(otherSideSession = flowSession, expectedTxId = txWeJustSignedId.id))
    }
}