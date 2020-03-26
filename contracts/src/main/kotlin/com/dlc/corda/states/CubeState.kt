package com.dlc.corda.states

import co.paralleluniverse.fibers.Suspendable
import com.dlc.corda.contracts.CubeContract
import com.dlc.corda.utilities.*
import net.corda.core.contracts.BelongsToContract
import net.corda.core.contracts.ContractState
import net.corda.core.contracts.LinearState
import net.corda.core.contracts.UniqueIdentifier
import net.corda.core.identity.Party
import net.corda.core.serialization.CordaSerializable

/**
 * **************
 * * Cube State *
 * **************
 *
 * This is a simple state designed to represent a digital rubik's cube.
 *
 * @param issuer The [Party] that initially issued the cube onto the ledger
 * @param solvers A [List] of [Party]s that have permission to attempt to solve the puzzle
 */

@BelongsToContract(CubeContract::class)
@CordaSerializable
data class CubeState private constructor(
        val issuer: Party,
        val solvers: List<Party>,
        val state: List<CubeFace>,
        override val linearId: UniqueIdentifier
) : ContractState, LinearState {

    constructor(issuer: Party, solvers: List<Party>): this(issuer, solvers, generateCleanCubeState(), UniqueIdentifier())

    @Suspendable
    fun updateCubeState(newState: List<CubeFace>) = CubeState(issuer, solvers, newState, linearId)

    @Suspendable
    fun updateSolvers(newSolvers: List<Party>) = CubeState(issuer, newSolvers, state, linearId)

    init {
        if (state.size != 6) {
            throw CubeInitializationException("A cube state must have exactly 6 cube faces")
        }
    }

    override val participants: List<Party> = solvers

    @Suspendable
    fun handleMove(move: Moves): CubeState {
        return when(move) {
            Moves.F -> { updateCubeState(rotateF(state)) }
            Moves.Fi -> { updateCubeState(rotateFi(state)) }
            Moves.B -> { updateCubeState(rotateB(state)) }
            Moves.Bi -> { updateCubeState(rotateBi(state)) }
            Moves.L -> { updateCubeState(rotateL(state)) }
            Moves.Li -> { updateCubeState(rotateLi(state)) }
            Moves.R -> { updateCubeState(rotateR(state)) }
            Moves.Ri -> { updateCubeState(rotateRi(state)) }
            Moves.D -> { updateCubeState(rotateD(state)) }
            Moves.Di -> { updateCubeState(rotateDi(state)) }
            Moves.U -> { updateCubeState(rotateU(state)) }
            Moves.Ui -> { updateCubeState(rotateUi(state)) }
        }
    }
}

/**
 * A utility function designed to generate a clean cube state.
 */
@Suspendable
fun generateCleanCubeState(): List<CubeFace> {
    return listOf(
        CubeFace(List(9) { CubeColor.ORANGE }),
        CubeFace(List(9) { CubeColor.BLUE }),
        CubeFace(List(9) { CubeColor.WHITE }),
        CubeFace(List(9) { CubeColor.RED }),
        CubeFace(List(9) { CubeColor.YELLOW }),
        CubeFace(List(9) { CubeColor.GREEN })
    )
}



