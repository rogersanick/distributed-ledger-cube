package com.dlc.corda.webserver

import com.dlc.corda.states.CubeState
import com.google.gson.Gson
import net.corda.core.messaging.vaultQueryBy
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Define your API endpoints here.
 */
@RestController
@RequestMapping("/api/") // The paths for HTTP requests are relative to this base path.
class Controller(rpc: NodeRPCConnection) {

    companion object {
        private val logger = LoggerFactory.getLogger(RestController::class.java)
    }

    private val proxy = rpc.proxy

    @GetMapping(value = ["cubes"], produces = ["text/plain"])
    private fun getAllCubes(): String {
        return Gson().toJson(proxy.vaultQueryBy<CubeState>().states.map { it.state.data.state }.flatten())
    }
}