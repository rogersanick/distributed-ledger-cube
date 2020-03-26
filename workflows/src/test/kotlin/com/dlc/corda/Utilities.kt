package com.dlc.corda

import net.corda.core.flows.FlowLogic
import net.corda.core.utilities.getOrThrow
import net.corda.testing.node.MockNetwork
import net.corda.testing.node.StartedMockNode

/**
 * A utility function that starts a flow, runs the network and retrieves the
 * value returned by the call method.
 *
 * @param flow Flow logic to be executed by the subject node
 * @param manuallyPumped Whether the network should be manually pumped.
 * @param mockNetwork The [MockNetwork] to pump.
 */
fun <T> StartedMockNode.runFlowAndReturn(flow : FlowLogic<T>,
                                         manuallyPumped: Boolean = false, mockNetwork: MockNetwork? = null) : T {

    if (manuallyPumped) {
        if (mockNetwork == null) {
            throw IllegalArgumentException("mockNetwork cannot be null when manually pumped.")
        }
    }

    val future = startFlow(flow)

    // "Pump" the network.
    if (manuallyPumped) {
        mockNetwork!!.runNetwork()
    }

    return future.getOrThrow()
}

fun StartedMockNode.identity() = this.info.legalIdentities.first()