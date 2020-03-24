package com.dlc.corda.utilities

import java.awt.Component
import java.lang.IllegalArgumentException

private val transformations = listOf("F", "Fi", "B", "Bi", "U", "Ui", "D", "Di", "L", "Li", "R", "Ri")

fun transform(currCubeState: List<ComponentCube>, transformation: String) {
    if (transformation !in transformations)
        throw IllegalArgumentException("The specified transformation does not exist.")
}

fun List<MutableComponentCube>.toImmutableState(): List<ComponentCube> {
    return this.map { ComponentCube(it) }
}

fun List<ComponentCube>.toMutableState(): List<MutableComponentCube> {
    return this.map { it.toMutableComponentCube() }
}

fun rotateF(state: List<ComponentCube>): List<ComponentCube> {
    return state
            .toMutableState()
            .handleRotateEdgesFrontClockwise()
            .handleRotateCubeFaceClockwise(0)
            .toImmutableState()
}

fun rotateFi(state: List<ComponentCube>): List<ComponentCube> {
    return state
            .toMutableState()
            .handleRotateEdgesFrontCounterClockwise()
            .handleRotateCubeFaceCounterClockwise(0)
            .toImmutableState()
}

fun rotateB(state: List<ComponentCube>): List<ComponentCube> {
    return state
            .toMutableState()
            .handleRotateEdgesBackClockwise()
            .handleRotateCubeFaceClockwise(3)
            .toImmutableState()
}

fun rotateBi(state: List<ComponentCube>): List<ComponentCube> {
    return state
            .toMutableState()
            .handleRotateEdgesBackCounterClockwise()
            .handleRotateCubeFaceCounterClockwise(3)
            .toImmutableState()
}

fun rotateL(state: List<ComponentCube>): List<ComponentCube> {
    return state
            .toMutableState()
            .handleRotateEdgesLeftClockwise()
            .handleRotateCubeFaceClockwise(4)
            .toImmutableState()
}

fun rotateLi(state: List<ComponentCube>): List<ComponentCube> {
    return state
            .toMutableState()
            .handleRotateEdgesLeftCounterClockwise()
            .handleRotateCubeFaceCounterClockwise(4)
            .toImmutableState()
}

fun rotateR(state: List<ComponentCube>): List<ComponentCube> {
    return state
            .toMutableState()
            .handleRotateEdgesRightClockwise()
            .handleRotateCubeFaceClockwise(2)
            .toImmutableState()
}

fun rotateRi(state: List<ComponentCube>): List<ComponentCube> {
    return state
            .toMutableState()
            .handleRotateEdgesRightCounterClockwise()
            .handleRotateCubeFaceCounterClockwise(2)
            .toImmutableState()
}

fun rotateU(state: List<ComponentCube>): List<ComponentCube> {
    return state
            .toMutableState()
            .handleRotateEdgesUpClockwise()
            .handleRotateCubeFaceClockwise(1)
            .toImmutableState()
}

fun rotateUi(state: List<ComponentCube>): List<ComponentCube> {
    return state
            .toMutableState()
            .handleRotateEdgesUpCounterClockwise()
            .handleRotateCubeFaceCounterClockwise(1)
            .toImmutableState()
}

fun rotateD(state: List<ComponentCube>): List<ComponentCube> {
    return state
            .toMutableState()
            .handleRotateEdgesDownClockwise()
            .handleRotateCubeFaceClockwise(1)
            .toImmutableState()
}

fun rotateDi(state: List<ComponentCube>): List<ComponentCube> {
    return state
            .toMutableState()
            .handleRotateEdgesDownCounterClockwise()
            .handleRotateCubeFaceCounterClockwise(1)
            .toImmutableState()
}