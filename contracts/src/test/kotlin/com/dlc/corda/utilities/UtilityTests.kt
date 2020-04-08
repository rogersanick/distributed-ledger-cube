package com.dlc.corda.utilities

import com.dlc.corda.states.CubeState
import net.corda.core.identity.CordaX500Name
import net.corda.testing.core.TestIdentity
import net.corda.testing.node.MockServices
import org.junit.Test
import kotlin.test.assertEquals

class UtilityTests {
    private val ledgerServices = MockServices()

    @Test
    fun `Check any random set of moves can be made and reversed`() {
        val testIdentity = TestIdentity(CordaX500Name("CUBE", "NY", "US"))
        val cubeState = CubeState(testIdentity.party, listOf(testIdentity.party))

        for (i in 0..50) {
            val listOfMoves = Moves.values().toList().shuffled()
            val listOfMovesInReverse = listOfMoves.reversed().map { it.getOpposite() }
            val updatedCubeState = cubeState.makeMoves(listOfMoves)
            val fixedCubeState = updatedCubeState.makeMoves(listOfMovesInReverse)
            assertEquals(cubeState.state, fixedCubeState.state)
        }
    }

    @Test
    fun `Check that cube is correctly mutated in a complicated move set`() {
        val testIdentity = TestIdentity(CordaX500Name("CUBE", "NY", "US"))
        val cubeState = CubeState(testIdentity.party, listOf(testIdentity.party))
        val complicatedMoveSet = listOf(
                Moves.L, Moves.Li, Moves.Ri, Moves.Ui, Moves.Li,
                Moves.Li, Moves.L, Moves.Di, Moves.L)
        var updateCubeState = cubeState.copy()
        for (move in complicatedMoveSet) {
            updateCubeState = updateCubeState.handleMove(move)
            val test = 0
        }
    }

    @Test
    fun `Check that cube is correctly mutated in an even more complicated move set`() {
        val testIdentity = TestIdentity(CordaX500Name("CUBE", "NY", "US"))
        val cubeState = CubeState(testIdentity.party, listOf(testIdentity.party))
        val complicatedMoveSet = listOf(Moves.Fi, Moves.R, Moves.B, Moves.U,
                Moves.Bi, Moves.L, Moves.B, Moves.F, Moves.Di, Moves.R,
                Moves.R, Moves.Bi, Moves.D, Moves.Ui, Moves.Di, Moves.L,
                Moves.D, Moves.Ui, Moves.Bi, Moves.F)
        var updateCubeState = cubeState.copy()
        for (move in complicatedMoveSet) {
            updateCubeState = updateCubeState.handleMove(move)
        }
    }

}