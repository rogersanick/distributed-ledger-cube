package com.dlc.corda.utilities

import net.corda.core.serialization.CordaSerializable

/**
 * A class to represent a single component cube as part of the Rubik's cube puzzle.
 */
@CordaSerializable
data class CubeFace(
        val faceState: List<CubeColor>
) {

    constructor(mutableCubeFace: MutableCubeFace): this(mutableCubeFace.faceState.toList())

    init {
        if (faceState.size != 9) {
            throw CubeInitializationException("A cube face must have exactly 9 CubeColors")
        }
    }

    /**
     * Convert the component cube to a mutable component cube for transformation
     */
    fun toMutableCubeFace() = MutableCubeFace(this)
}

/**
 * A class to represent a single component cube that will be mutated as part of a transformation.
 */
data class MutableCubeFace(private val cubeFace: CubeFace) {
    val faceState = cubeFace.faceState.toMutableList()
}

/**
 * An enum class representing the colors the comprise all component cubes of the Rubik's cube puzzle.
 */
@CordaSerializable
enum class CubeColor {
    WHITE,
    YELLOW,
    GREEN,
    BLUE,
    ORANGE,
    RED
}