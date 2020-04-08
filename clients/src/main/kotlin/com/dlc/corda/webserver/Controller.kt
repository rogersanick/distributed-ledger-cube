package com.dlc.corda.webserver

import com.dlc.corda.flows.ExitCubeFlow
import com.dlc.corda.flows.TransformCubeFlow
import com.dlc.corda.states.CubeState
import com.dlc.corda.utilities.CubeFace
import com.dlc.corda.utilities.Moves
import net.corda.core.contracts.UniqueIdentifier
import net.corda.core.crypto.SecureHash
import net.corda.core.identity.Party
import net.corda.core.messaging.startFlow
import net.corda.core.messaging.vaultQueryBy
import net.corda.core.node.services.vault.QueryCriteria
import net.corda.core.utilities.getOrThrow
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*
import javax.json.Json
import javax.json.JsonArray
import javax.json.JsonObject

/**
 * Define your API endpoints here.
 */
@CrossOrigin(origins = arrayOf("*"), maxAge = 3600)
@RestController
@RequestMapping("/api") // The paths for HTTP requests are relative to this base path.
class Controller(rpc: NodeRPCConnection) {

    companion object {
        private val logger = LoggerFactory.getLogger(RestController::class.java)
    }

    private val proxy = rpc.proxy

    @GetMapping(value = ["/cube"], produces = ["text/plain"])
    private fun getAllCubes(): String {
        val jsonArray = Json.createArrayBuilder()
        proxy.vaultQueryBy<CubeState>().states
                .forEach { jsonArray.add(it.state.data.toJSON()) }
        return jsonArray.build().toString()
    }

    @GetMapping(value = ["/cube/{id}"], produces = ["text/plain"])
    private fun getCube(@RequestParam id: String): String {
        val parsedUniqueIdentifier = UniqueIdentifier.fromString(id)
        val linearStateQueryCriteria = QueryCriteria
                .LinearStateQueryCriteria(linearId = listOf(parsedUniqueIdentifier))
        return proxy.vaultQueryBy<CubeState>(linearStateQueryCriteria)
                .states.single()
                .state.data
                .toJSON().toString()
    }

    @PostMapping(value = ["/cube"])
    private fun makeMove(@RequestBody payload: CubeMoves): String {
        val parsedUniqueIdentifier = UniqueIdentifier.fromString(payload.cubeId)
        println(payload.cubeId)
        println(payload.moves)
        val stx = this.proxy.startFlow(::TransformCubeFlow, parsedUniqueIdentifier, payload.moves).returnValue.getOrThrow()
        return stx.coreTransaction.id.toString()
    }

    /**
     * Simple class to help with de-serializing JSON from cube client.
     */
    private class CubeMoves(
            val cubeId: String,
            val moves: ArrayList<Moves>
    )

    @DeleteMapping(value = ["/cube/{id}"])
    private fun deleteCube(@PathVariable id: String): String {
        val parsedUniqueIdentifier = UniqueIdentifier.fromString(id)
        return this.proxy
                .startFlow(::ExitCubeFlow, parsedUniqueIdentifier).returnValue
                .getOrThrow().toString()
    }

    /**
     * Simple class to help with de-serializing JSON from cube client.
     */
    private class CubeId (val cubeId: String)

    private fun CubeState.toJSON(): JsonObject {
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