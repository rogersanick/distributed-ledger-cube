package com.dlc.corda.utilities

import co.paralleluniverse.fibers.Suspendable

fun List<MutableCubeFace>.toImmutableState(): List<CubeFace> {
    return this.map { CubeFace(it) }
}

fun List<CubeFace>.toMutableState(): List<MutableCubeFace> {
    return this.map { it.toMutableCubeFace() }
}

@Suspendable
fun rotateF(state: List<CubeFace>): List<CubeFace> {
    return state
            .toMutableState()
            .handleRotateEdgesFrontClockwise()
            .handleRotateCubeFaceClockwise(0)
            .toImmutableState()
}

@Suspendable
fun rotateFi(state: List<CubeFace>): List<CubeFace> {
    return state
            .toMutableState()
            .handleRotateEdgesFrontCounterClockwise()
            .handleRotateCubeFaceCounterClockwise(0)
            .toImmutableState()
}

@Suspendable
fun rotateB(state: List<CubeFace>): List<CubeFace> {
    return state
            .toMutableState()
            .handleRotateEdgesBackClockwise()
            .handleRotateCubeFaceClockwise(3)
            .toImmutableState()
}

@Suspendable
fun rotateBi(state: List<CubeFace>): List<CubeFace> {
    return state
            .toMutableState()
            .handleRotateEdgesBackCounterClockwise()
            .handleRotateCubeFaceCounterClockwise(3)
            .toImmutableState()
}

@Suspendable
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

@Suspendable
fun rotateR(state: List<CubeFace>): List<CubeFace> {
    return state
            .toMutableState()
            .handleRotateEdgesRightClockwise()
            .handleRotateCubeFaceClockwise(2)
            .toImmutableState()
}

@Suspendable
fun rotateRi(state: List<CubeFace>): List<CubeFace> {
    return state
            .toMutableState()
            .handleRotateEdgesRightCounterClockwise()
            .handleRotateCubeFaceCounterClockwise(2)
            .toImmutableState()
}

@Suspendable
fun rotateU(state: List<CubeFace>): List<CubeFace> {
    return state
            .toMutableState()
            .handleRotateEdgesUpClockwise()
            .handleRotateCubeFaceClockwise(1)
            .toImmutableState()
}

@Suspendable
fun rotateUi(state: List<CubeFace>): List<CubeFace> {
    return state
            .toMutableState()
            .handleRotateEdgesUpCounterClockwise()
            .handleRotateCubeFaceCounterClockwise(1)
            .toImmutableState()
}

@Suspendable
fun rotateD(state: List<CubeFace>): List<CubeFace> {
    return state
            .toMutableState()
            .handleRotateEdgesDownClockwise()
            .handleRotateCubeFaceClockwise(5)
            .toImmutableState()
}

@Suspendable
fun rotateDi(state: List<CubeFace>): List<CubeFace> {
    return state
            .toMutableState()
            .handleRotateEdgesDownCounterClockwise()
            .handleRotateCubeFaceCounterClockwise(5)
            .toImmutableState()
}