package com.dlc.corda.webserver

import com.dlc.corda.states.CubeState
import com.dlc.corda.utilities.CubeFace
import net.corda.core.identity.Party
import net.corda.core.messaging.vaultQueryBy
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.json.Json
import javax.json.JsonArray
import javax.json.JsonObject

/**
 * Define your API endpoints here.
 */
@CrossOrigin(origins = arrayOf("*"), maxAge = 3600)
@RestController
@RequestMapping("/api/") // The paths for HTTP requests are relative to this base path.
class Controller(rpc: NodeRPCConnection) {

    companion object {
        private val logger = LoggerFactory.getLogger(RestController::class.java)
    }

    private val proxy = rpc.proxy

    @GetMapping(value = ["cubes"], produces = ["text/plain"])
    private fun getAllCubes(): String {
        val jsonArray = Json.createArrayBuilder()
        val results = proxy.vaultQueryBy<CubeState>().states
                .forEach { jsonArray.add(it.state.data.toJSONArrayOfCubeFaces()) }
        return  jsonArray.build().toString()
    }

    private fun CubeState.toJSONArrayOfCubeFaces(): JsonObject {
        return Json.createObjectBuilder()
                .add("linearId", this.linearId.toString())
                .add("state", this.state.toJSONArrayOfCubeFaces())
                .add("issuer", this.issuer.toString())
                .add("solver", this.solvers.toJSONArrayOfParties())
                .build()
    }

    private fun List<CubeFace>.toJSONArrayOfCubeFaces(): JsonArray {
        val jsonArrayBuilder = Json.createArrayBuilder()
        this.forEach {
            val jsonBuilder = Json.createObjectBuilder()
            it.faceState.forEachIndexed { index, face -> jsonBuilder.add(index.toString(), face.toString()) }
            jsonArrayBuilder.add(jsonBuilder.build())
        }
        return jsonArrayBuilder.build()
    }

    private fun List<Party>.toJSONArrayOfParties(): JsonArray {
        return Json.createArrayBuilder().let {
            this.forEach { party -> it.add(party.toString()) }
            it
        }.build()
    }
}