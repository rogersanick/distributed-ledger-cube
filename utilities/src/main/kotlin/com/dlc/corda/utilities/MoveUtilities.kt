package com.dlc.corda.utilities

import java.lang.IllegalArgumentException

private val transformations = listOf("F", "Fi", "B", "Bi", "U", "Ui", "D", "Di", "L", "Li", "R", "Ri")

fun transform(currCubeState: List<CubeFace>, transformation: String) {
    if (transformation !in transformations)
        throw IllegalArgumentException("The specified transformation does not exist.")
}

fun List<MutableCubeFace>.toImmutableState(): List<CubeFace> {
    return this.map { CubeFace(it) }
}

fun List<CubeFace>.toMutableState(): List<MutableCubeFace> {
    return this.map { it.toMutableCubeFace() }
}

fun rotateF(state: List<CubeFace>): List<CubeFace> {
    return state
            .toMutableState()
            .handleRotateEdgesFrontClockwise()
            .handleRotateCubeFaceClockwise(0)
            .toImmutableState()
}

fun rotateFi(state: List<CubeFace>): List<CubeFace> {
    return state
            .toMutableState()
            .handleRotateEdgesFrontCounterClockwise()
            .handleRotateCubeFaceCounterClockwise(0)
            .toImmutableState()
}

fun rotateB(state: List<CubeFace>): List<CubeFace> {
    return state
            .toMutableState()
            .handleRotateEdgesBackClockwise()
            .handleRotateCubeFaceClockwise(3)
            .toImmutableState()
}

fun rotateBi(state: List<CubeFace>): List<CubeFace> {
    return state
            .toMutableState()
            .handleRotateEdgesBackCounterClockwise()
            .handleRotateCubeFaceCounterClockwise(3)
            .toImmutableState()
}

fun rotateL(state: List<CubeFace>): List<CubeFace> {
    return state
            .toMutableState()
            .handleRotateEdgesLeftClockwise()
            .handleRotateCubeFaceClockwise(4)
            .toImmutableState()
}

fun rotateLi(state: List<CubeFace>): List<CubeFace> {
    return state
            .toMutableState()
            .handleRotateEdgesLeftCounterClockwise()
            .handleRotateCubeFaceCounterClockwise(4)
            .toImmutableState()
}

fun rotateR(state: List<CubeFace>): List<CubeFace> {
    return state
            .toMutableState()
            .handleRotateEdgesRightClockwise()
            .handleRotateCubeFaceClockwise(2)
            .toImmutableState()
}

fun rotateRi(state: List<CubeFace>): List<CubeFace> {
    return state
            .toMutableState()
            .handleRotateEdgesRightCounterClockwise()
            .handleRotateCubeFaceCounterClockwise(2)
            .toImmutableState()
}

fun rotateU(state: List<CubeFace>): List<CubeFace> {
    return state
            .toMutableState()
            .handleRotateEdgesUpClockwise()
            .handleRotateCubeFaceClockwise(1)
            .toImmutableState()
}

fun rotateUi(state: List<CubeFace>): List<CubeFace> {
    return state
            .toMutableState()
            .handleRotateEdgesUpCounterClockwise()
            .handleRotateCubeFaceCounterClockwise(1)
            .toImmutableState()
}

fun rotateD(state: List<CubeFace>): List<CubeFace> {
    return state
            .toMutableState()
            .handleRotateEdgesDownClockwise()
            .handleRotateCubeFaceClockwise(1)
            .toImmutableState()
}

fun rotateDi(state: List<CubeFace>): List<CubeFace> {
    return state
            .toMutableState()
            .handleRotateEdgesDownCounterClockwise()
            .handleRotateCubeFaceCounterClockwise(1)
            .toImmutableState()
}