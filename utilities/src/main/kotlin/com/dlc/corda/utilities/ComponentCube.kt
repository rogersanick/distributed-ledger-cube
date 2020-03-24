package com.dlc.corda.utilities

/**
 * A class to represent a single component cube as part of the Rubik's cube puzzle.
 */
data class ComponentCube(
        private val side0: CubeColor?,
        private val side1: CubeColor?,
        private val side2: CubeColor?,
        private val side3: CubeColor?,
        private val side4: CubeColor?,
        private val side5: CubeColor?
) {
    constructor(mutableComponentCube: MutableComponentCube):
            this(
                    mutableComponentCube.sides[0],
                    mutableComponentCube.sides[1],
                    mutableComponentCube.sides[2],
                    mutableComponentCube.sides[3],
                    mutableComponentCube.sides[4],
                    mutableComponentCube.sides[5]
            )

    /**
     * Convert the component cube to a mutable component cube for transformation
     */
    fun toMutableComponentCube() = MutableComponentCube(this)

    val sides = listOf(side0, side1, side2, side3, side4, side5)
}

/**
 * A class to represent a single component cube that will be mutated as part of a transformation.
 */
data class MutableComponentCube(private val componentCube: ComponentCube) {
    val sides = componentCube.sides.toMutableList()
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