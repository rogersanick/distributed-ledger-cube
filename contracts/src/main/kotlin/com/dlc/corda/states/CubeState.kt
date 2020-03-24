package com.dlc.corda.states

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
class CubeState private constructor(
        val issuer: Party,
        val solvers: List<Party>,
        val state: List<ComponentCube>,
        override val linearId: UniqueIdentifier
) : ContractState, LinearState {

    constructor(issuer: Party, solvers: List<Party>): this(issuer, solvers, generateCleanCubeState(), UniqueIdentifier())

    fun updateCubeState(newState: List<ComponentCube>) = CubeState(issuer, solvers, newState, linearId)

    init {
        if (state.size != 27) {
            throw CubeInitializationException("A cube state must have exactly 27 cubes")
        }
    }

    override val participants: List<Party> = solvers

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
private fun generateCleanCubeState(): List<ComponentCube> {
    return listOf(
        ComponentCube(CubeColor.WHITE, null, null, CubeColor.GREEN, CubeColor.ORANGE, null),
        ComponentCube(null, null, null, CubeColor.GREEN, CubeColor.ORANGE, null),
        ComponentCube(null, CubeColor.YELLOW, null, CubeColor.GREEN, CubeColor.ORANGE, null),
        ComponentCube(CubeColor.WHITE, null, null, null, CubeColor.ORANGE, null),
        ComponentCube(null, null, null, null, CubeColor.ORANGE, null),
        ComponentCube(null, CubeColor.YELLOW, null, null, CubeColor.ORANGE, null),
        ComponentCube(CubeColor.WHITE, null, CubeColor.BLUE, null, CubeColor.ORANGE, null),
        ComponentCube(null, null, CubeColor.BLUE, null, CubeColor.ORANGE, null),
        ComponentCube(null, CubeColor.YELLOW, CubeColor.BLUE, null, CubeColor.ORANGE, null),
        ComponentCube(CubeColor.WHITE, null, null, CubeColor.GREEN, null, null),
        ComponentCube(null, null, null, CubeColor.GREEN, null, null),
        ComponentCube(null, CubeColor.YELLOW, null, CubeColor.GREEN, null, null),
        ComponentCube(CubeColor.WHITE, null, null, null, null, null),
        ComponentCube(CubeColor.WHITE, CubeColor.YELLOW, CubeColor.WHITE, CubeColor.WHITE, CubeColor.WHITE, CubeColor.WHITE),
        ComponentCube(null, CubeColor.YELLOW, null, null, null, null),
        ComponentCube(CubeColor.WHITE, null, CubeColor.BLUE, null, null, null),
        ComponentCube(null, null, CubeColor.BLUE, null, null, null),
        ComponentCube(null, CubeColor.YELLOW, CubeColor.BLUE, null, null, null),
        ComponentCube(CubeColor.WHITE, null, null, CubeColor.GREEN, null, CubeColor.RED),
        ComponentCube(null, null, null, CubeColor.GREEN, null, CubeColor.RED),
        ComponentCube(null, CubeColor.YELLOW, null, CubeColor.GREEN, null, CubeColor.RED),
        ComponentCube(CubeColor.WHITE, null, null, null, null, CubeColor.RED),
        ComponentCube(null, null, null, null, null, CubeColor.RED),
        ComponentCube(null, CubeColor.YELLOW, null, null, null, CubeColor.RED),
        ComponentCube(CubeColor.WHITE, null, CubeColor.BLUE, null, null, CubeColor.RED),
        ComponentCube(null, null, CubeColor.BLUE, null, null, CubeColor.RED),
        ComponentCube(null, CubeColor.YELLOW, CubeColor.BLUE, null, null, CubeColor.RED)
    )
}



