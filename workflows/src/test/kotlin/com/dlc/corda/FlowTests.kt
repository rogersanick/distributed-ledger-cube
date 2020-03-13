package com.dlc.corda

import com.dlc.corda.flows.IssueCubeFlow
import com.dlc.corda.states.CubeState
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
    private val network = MockNetwork(MockNetworkParameters(cordappsForAllNodes = listOf(
            TestCordapp.findCordapp("com.dlc.corda.flows"),
            TestCordapp.findCordapp("com.dlc.corda.contracts"),
            TestCordapp.findCordapp("com.dlc.corda.states")
    )))
    private val a = network.createNode()

    @Before
    fun setup() = network.runNetwork()

    @After
    fun tearDown() = network.stopNodes()

    @Test
    fun `A node can issue a cube onto the ledger`() {
        val testIdentity = TestIdentity(CordaX500Name("Nathanial", "NYC", "US"))
        val futureWithCleanCube = a.startFlow(IssueCubeFlow(listOf(a.info.legalIdentities.first())))
        network.runNetwork()
        val cleanCubeState = futureWithCleanCube.getOrThrow()
        assertEquals(
                CubeState(testIdentity.party, listOf(testIdentity.party)).cubeState,
                cleanCubeState.cubeState
        )
    }
}