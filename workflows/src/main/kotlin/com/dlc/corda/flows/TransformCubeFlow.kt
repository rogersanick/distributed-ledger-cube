package com.dlc.corda.flows

import co.paralleluniverse.fibers.Suspendable
import com.dlc.corda.contracts.CubeContract
import com.dlc.corda.states.CubeState
import com.dlc.corda.utilities.Moves
import net.corda.core.contracts.UniqueIdentifier
import net.corda.core.flows.FinalityFlow
import net.corda.core.flows.FlowLogic
import net.corda.core.flows.InitiatingFlow
import net.corda.core.flows.StartableByRPC
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
        val newCubeState = moves.fold(cubeState) { cubeState, move -> cubeState.handleMove(move) }

        // Add the newly issued cube to the ledger
        tb.addOutputState(newCubeState)

        // Add the issuance command to the ledger
        tb.addCommand(CubeContract.Commands.Update(), listOf(ourIdentity.owningKey))

        // Sign the command
        val ptx = serviceHub.signInitialTransaction(tb)

        // Finalize the TX, no need to collect signatures here as we are the only signer
        return subFlow(FinalityFlow(ptx, cubeState.participants.map { initiateFlow(it) }))
    }
}
