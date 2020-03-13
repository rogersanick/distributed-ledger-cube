package com.dlc.corda.states

import com.dlc.corda.contracts.CubeContract
import net.corda.core.contracts.BelongsToContract
import net.corda.core.contracts.ContractState
import net.corda.core.flows.FlowException
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
data class CubeState(
        val issuer: Party,
        val solvers: List<Party>
) : ContractState {
    val cubeState: List<ComponentCube> = generateCleanCubeState()
    init {
        if (cubeState.size != 27) {
            throw CubeInitializationException("A cube state must have exactly 27 cubes")
        }
    }

    override val participants: List<Party> = solvers
}

/**
 * A class to represent a single component cube as part of the Rubik's cube puzzle.
 */
data class ComponentCube(
        private val side1: CubeColor?,
        private val side2: CubeColor?,
        private val side3: CubeColor?,
        private val side4: CubeColor?,
        private val side5: CubeColor?,
        private val side6: CubeColor?
) {
    val sides = listOf(
            side1, side2, side3, side4, side5, side6
    )
}

/**
 * An enum class representing the colors the comprise all component cubes of the Rubik's cube puzzle.
 */
enum class CubeColor {
    WHITE,
    YELLOW,
    GREEN,
    BLUE,
    ORANGE,
    RED
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



