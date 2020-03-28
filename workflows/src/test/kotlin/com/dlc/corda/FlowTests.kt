package com.dlc.corda

import com.dlc.corda.flows.EditSolversFlow
import com.dlc.corda.flows.IssueCubeFlow
import com.dlc.corda.flows.TransformCubeFlow
import com.dlc.corda.states.CubeState
import com.dlc.corda.states.generateCleanCubeState
import com.dlc.corda.utilities.Moves
import net.corda.core.identity.CordaX500Name
import net.corda.core.utilities.getOrThrow
import net.corda.testing.core.TestIdentity
import net.corda.testing.node.MockNetwork
import net.corda.testing.node.MockNetworkParameters
import net.corda.testing.node.TestCordapp
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class FlowTests {
    private val network = MockNetwork(MockNetworkParameters(
            threadPerNode = true,
            cordappsForAllNodes = listOf(
                TestCordapp.findCordapp("com.dlc.corda.flows"),
                TestCordapp.findCordapp("com.dlc.corda.contracts"),
                TestCordapp.findCordapp("com.dlc.corda.states")
            )
    ))
    private val a = network.createNode()
    private val b = network.createNode()

    @After
    fun tearDown() = network.stopNodes()

    @Test
    fun `A node can issue a cube onto the ledger`() {
        val cleanCubeState = a.runFlowAndReturn(IssueCubeFlow(listOf(a.info.legalIdentities.first())))
        assertEquals(
                generateCleanCubeState(),
                cleanCubeState.state
        )
    }

    @Test
    fun `A node can issue a cube with multiple solvers onto the ledger`() {
        val cleanCubeState = a.runFlowAndReturn(
                IssueCubeFlow(
                        listOf(
                                a.identity(),
                                b.identity()
                        )
                )
        )
        assertEquals(
                generateCleanCubeState(),
                cleanCubeState.state
        )
    }

    @Test
    fun `A node can edit the solvers of a cube`() {
        val cleanCubeState = a.runFlowAndReturn(IssueCubeFlow(listOf(a.info.legalIdentities.first())))
        val txNewSolvers = a.runFlowAndReturn(EditSolversFlow(cleanCubeState.linearId, listOf(a.identity(), b.identity())))
        val cubeStateWithNewSolvers = txNewSolvers.tx.outputsOfType<CubeState>().single()
        assertEquals(
                listOf(a.identity(), b.identity()),
                cubeStateWithNewSolvers.solvers
        )
    }


    @Test
    fun `A node may attempt to solve a cube`() {
        val cleanCubeState = a.runFlowAndReturn(IssueCubeFlow(listOf(a.info.legalIdentities.first())))
        val moves = listOf(Moves.U, Moves.Di, Moves.D)
        val txWithTransformedCubeState = a.runFlowAndReturn(TransformCubeFlow(cleanCubeState.linearId, moves))
        val mutatedCubeState = txWithTransformedCubeState.tx.outputsOfType<CubeState>().single()
        assertEquals(
                moves.fold(cleanCubeState) { accCubeState, move -> accCubeState.handleMove(move) },
                mutatedCubeState
        )
    }
}