package com.dlc.corda.contracts

import com.dlc.corda.states.CubeState
import com.dlc.corda.states.generateCleanCubeState
import com.dlc.corda.utilities.Moves
import net.corda.core.contracts.CommandData
import net.corda.core.contracts.Contract
import net.corda.core.contracts.requireThat
import net.corda.core.transactions.LedgerTransaction

// ************
// * Contract *
// ************
class CubeContract : Contract {
    companion object {
        // Used to identify our contract when building a transaction.
        const val ID = "com.dlc.corda.contracts.CubeContract"
    }

    // A transaction is valid if the verify() function of the contract of all the transaction's input and output states
    // does not throw an exception.
    override fun verify(tx: LedgerTransaction) {
        when (tx.commands.single().value) {
            is Commands.Issue -> requireThat {
                "There should be no inputs to this transaction" using (tx.inputStates.isEmpty())
                "There should only be one output of type CubeState" using (tx.outputStates.size == 1 && tx.outputStates.single() is CubeState)
                val cubeState = tx.outputsOfType<CubeState>().single()
                "The CubeState produced should be a unaltered state" using (cubeState.state == generateCleanCubeState())
            }
            is Commands.Exit -> requireThat {
                "There should be no output for this transaction" using (tx.outputStates.isEmpty())
                "There should be one input state of type cube state" using (tx.inputStates.single() is CubeState)
            }
            is Commands.EditSolvers -> requireThat {
                "There should only be one input to this transaction" using (tx.inputStates.size == 1 && tx.outputStates.single() is CubeState)
                "There should only be one output of type CubeState" using (tx.outputStates.size == 1 && tx.outputStates.single() is CubeState)
                val inputCubeState = tx.inputsOfType<CubeState>().single()
                val outputCubeState = tx.outputsOfType<CubeState>().single()
                "The CubeState should not be otherwise altered" using (inputCubeState.updateSolvers(outputCubeState.solvers) == outputCubeState)
            }
            is Commands.MakeMoves -> requireThat {
                "There should only be one input to this transaction" using (tx.inputStates.size == 1 && tx.outputStates.single() is CubeState)
                "There should only be one output of type CubeState" using (tx.outputStates.size == 1 && tx.outputStates.single() is CubeState)
                val inputCubeState = tx.inputsOfType<CubeState>().single()
                val outputCubeState = tx.outputsOfType<CubeState>().single()
                "The CubeState should not be otherwise altered" using (inputCubeState.updateCubeState(outputCubeState.state) == outputCubeState)
                val verifiedOutputCubeState = (tx.commands.single().value as Commands.MakeMoves).moves.fold(inputCubeState) {
                    accCubeState, move -> accCubeState.handleMove(move)
                }
                "The CubeState should be correctly mutated" using (verifiedOutputCubeState == outputCubeState)
            }
        }
    }

    // Used to indicate the transaction's intent.
    interface Commands : CommandData {
        class Issue : Commands
        class Exit : Commands
        class EditSolvers: Commands
        class MakeMoves(val moves: List<Moves>) : Commands
    }
}