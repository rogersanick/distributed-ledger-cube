package com.dlc.corda.flows

import co.paralleluniverse.fibers.Suspendable
import com.dlc.corda.contracts.CubeContract
import com.dlc.corda.states.CubeState
import net.corda.core.flows.*
import net.corda.core.identity.Party
import net.corda.core.transactions.TransactionBuilder
import net.corda.core.utilities.ProgressTracker

// *********
// * Flows *
// *********
@InitiatingFlow
@StartableByRPC
class IssueCubeFlow(private val solvers: List<Party>) : FlowLogic<CubeState>() {
    override val progressTracker = ProgressTracker()

    @Suspendable
    override fun call(): CubeState {
        // Get access to the notary
        val notary = this.serviceHub.networkMapCache.notaryIdentities.first()

        // Create a transaction builder
        val tb = TransactionBuilder(notary)

        // Build the TX for issuing a Rubik's cube
        val cleanCube = CubeState(ourIdentity, solvers)

        // Add the newly issued cube to the ledger
        tb.addOutputState(cleanCube)

        // Add the issuance command to the ledger
        tb.addCommand(CubeContract.Commands.Issue(), listOf(ourIdentity.owningKey))

        // Sign the command
        val ptx = serviceHub.signInitialTransaction(tb)

        // Finalize the TX, no need to collect signatures here as we are the only signer
        subFlow(FinalityFlow(ptx, listOf()))

        // Return the issued cube state
        return cleanCube
    }
}
