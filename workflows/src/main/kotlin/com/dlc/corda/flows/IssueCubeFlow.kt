package com.dlc.corda.flows

import co.paralleluniverse.fibers.Suspendable
import com.dlc.corda.contracts.CubeContract
import com.dlc.corda.states.CubeState
import net.corda.core.contracts.Requirements.using
import net.corda.core.contracts.requireThat
import net.corda.core.flows.*
import net.corda.core.identity.Party
import net.corda.core.transactions.SignedTransaction
import net.corda.core.transactions.TransactionBuilder
import net.corda.core.utilities.ProgressTracker

// *********
// * Flows *
// *********
@StartableByRPC
@InitiatingFlow
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
        tb.addCommand(CubeContract.Commands.Issue(), cleanCube.solvers.map { it.owningKey })

        // Sign the command
        val ptx = serviceHub.signInitialTransaction(tb)

        // Collect signatures as required
        val sessions = (cleanCube.solvers - ourIdentity).map { initiateFlow(it) }
        val stx = subFlow(CollectSignaturesFlow(ptx, sessions))

        // Finalize the TX, no need to collect signatures here as we are the only signer
        subFlow(FinalityFlow(stx, sessions))

        // Return the issued cube state
        return cleanCube
    }
}

@InitiatedBy(IssueCubeFlow::class)
class IssueCubeFlowResponder(val flowSession: FlowSession): FlowLogic<SignedTransaction>() {

    @Suspendable
    override fun call(): SignedTransaction {
        val signedTransactionFlow = object : SignTransactionFlow(flowSession) {
            override fun checkTransaction(stx: SignedTransaction) = requireThat {
                val output = stx.tx.outputs.single().data
                "The output must be a CubeState" using (output is CubeState)
                "The initiating party must be the issuer" using (flowSession.counterparty == (output as CubeState).issuer)
            }
        }
        val txWeJustSignedId = subFlow(signedTransactionFlow)
        return subFlow(ReceiveFinalityFlow(otherSideSession = flowSession, expectedTxId = txWeJustSignedId.id))
    }
}