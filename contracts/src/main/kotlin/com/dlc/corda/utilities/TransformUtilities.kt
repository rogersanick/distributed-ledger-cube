package com.dlc.corda.utilities

import com.dlc.corda.utilities.MutableCubeFace

// ROTATE FRONT FACE EDGES
// TODO: Research matrix transformations
fun List<MutableCubeFace>.handleRotateEdgesFrontClockwise(): List<MutableCubeFace> {
    val temp1 = this[1].faceState[0]
    val temp2 = this[1].faceState[1]
    val temp3 = this[1].faceState[2]
    this[1].faceState[0] = this[4].faceState[6]
    this[1].faceState[1] = this[4].faceState[3]
    this[1].faceState[2] = this[4].faceState[0]
    this[4].faceState[6] = this[5].faceState[8]
    this[4].faceState[3] = this[5].faceState[7]
    this[4].faceState[0] = this[5].faceState[6]
    this[5].faceState[8] = this[2].faceState[2]
    this[5].faceState[7] = this[2].faceState[5]
    this[5].faceState[6] = this[2].faceState[8]
    this[2].faceState[2] = temp1
    this[2].faceState[5] = temp2
    this[2].faceState[8] = temp3
    return this
}

fun List<MutableCubeFace>.handleRotateEdgesFrontCounterClockwise(): List<MutableCubeFace> {
    val temp1 = this[1].faceState[0]
    val temp2 = this[1].faceState[1]
    val temp3 = this[1].faceState[2]
    this[1].faceState[0] = this[2].faceState[2]
    this[1].faceState[1] = this[2].faceState[5]
    this[1].faceState[2] = this[2].faceState[8]
    this[2].faceState[2] = this[5].faceState[8]
    this[2].faceState[5] = this[5].faceState[7]
    this[2].faceState[8] = this[5].faceState[6]
    this[5].faceState[8] = this[4].faceState[6]
    this[5].faceState[7] = this[4].faceState[3]
    this[5].faceState[6] = this[4].faceState[0]
    this[4].faceState[6] = temp1
    this[4].faceState[3] = temp2
    this[4].faceState[0] = temp3
    return this
}

// ROTATE BACK FACE EDGES
fun List<MutableCubeFace>.handleRotateEdgesBackClockwise(): List<MutableCubeFace> {
    val temp1 = this[1].faceState[6]
    val temp2 = this[1].faceState[7]
    val temp3 = this[1].faceState[8]
    this[1].faceState[6] = this[2].faceState[0]
    this[1].faceState[7] = this[2].faceState[3]
    this[1].faceState[8] = this[2].faceState[6]
    this[2].faceState[0] = this[5].faceState[2]
    this[2].faceState[3] = this[5].faceState[1]
    this[2].faceState[6] = this[5].faceState[0]
    this[5].faceState[2] = this[4].faceState[8]
    this[5].faceState[1] = this[4].faceState[5]
    this[5].faceState[0] = this[4].faceState[2]
    this[4].faceState[8] = temp1
    this[4].faceState[5] = temp2
    this[4].faceState[2] = temp3
    return this
}

fun List<MutableCubeFace>.handleRotateEdgesBackCounterClockwise(): List<MutableCubeFace> {
    val temp1 = this[2].faceState[0]
    val temp2 = this[2].faceState[3]
    val temp3 = this[2].faceState[6]
    this[2].faceState[0] = this[1].faceState[6]
    this[2].faceState[3] = this[1].faceState[7]
    this[2].faceState[6] = this[1].faceState[8]
    this[1].faceState[6] = this[4].faceState[8]
    this[1].faceState[7] = this[4].faceState[5]
    this[1].faceState[8] = this[4].faceState[2]
    this[4].faceState[8] = this[5].faceState[2]
    this[4].faceState[5] = this[5].faceState[1]
    this[4].faceState[2] = this[5].faceState[0]
    this[5].faceState[2] = temp1
    this[5].faceState[1] = temp2
    this[5].faceState[0] = temp3
    return this
}

// ROTATE LEFT FACE EDGES
fun List<MutableCubeFace>.handleRotateEdgesLeftClockwise(): List<MutableCubeFace> {
    val temp1 = this[1].faceState[2]
    val temp2 = this[1].faceState[5]
    val temp3 = this[1].faceState[8]
    this[1].faceState[2] = this[3].faceState[6]
    this[1].faceState[5] = this[3].faceState[3]
    this[1].faceState[8] = this[3].faceState[0]
    this[3].faceState[6] = this[5].faceState[2]
    this[3].faceState[3] = this[5].faceState[5]
    this[3].faceState[0] = this[5].faceState[8]
    this[5].faceState[2] = this[0].faceState[2]
    this[5].faceState[5] = this[0].faceState[5]
    this[5].faceState[8] = this[0].faceState[8]
    this[0].faceState[2] = temp1
    this[0].faceState[5] = temp2
    this[0].faceState[8] = temp3
    return this
}

fun List<MutableCubeFace>.handleRotateEdgesLeftCounterClockwise(): List<MutableCubeFace> {
    val temp1 = this[1].faceState[2]
    val temp2 = this[1].faceState[5]
    val temp3 = this[1].faceState[8]
    this[1].faceState[2] = this[0].faceState[2]
    this[1].faceState[5] = this[0].faceState[5]
    this[1].faceState[8] = this[0].faceState[8]
    this[0].faceState[2] = this[5].faceState[2]
    this[0].faceState[5] = this[5].faceState[5]
    this[0].faceState[8] = this[5].faceState[8]
    this[5].faceState[2] = this[3].faceState[6]
    this[5].faceState[5] = this[3].faceState[3]
    this[5].faceState[8] = this[3].faceState[0]
    this[3].faceState[6] = temp1
    this[3].faceState[3] = temp2
    this[3].faceState[0] = temp3
    return this
}

// ROTATE RIGHT FACE EDGES
fun List<MutableCubeFace>.handleRotateEdgesRightClockwise(): List<MutableCubeFace> {
    val temp1 = this[1].faceState[0]
    val temp2 = this[1].faceState[3]
    val temp3 = this[1].faceState[6]
    this[1].faceState[0] = this[0].faceState[0]
    this[1].faceState[3] = this[0].faceState[3]
    this[1].faceState[6] = this[0].faceState[6]
    this[0].faceState[0] = this[5].faceState[0]
    this[0].faceState[3] = this[5].faceState[3]
    this[0].faceState[6] = this[5].faceState[6]
    this[5].faceState[0] = this[3].faceState[8]
    this[5].faceState[3] = this[3].faceState[5]
    this[5].faceState[6] = this[3].faceState[2]
    this[3].faceState[8] = temp1
    this[3].faceState[5] = temp2
    this[3].faceState[2] = temp3
    return this
}

fun List<MutableCubeFace>.handleRotateEdgesRightCounterClockwise(): List<MutableCubeFace> {
    val temp1 = this[1].faceState[6]
    val temp2 = this[1].faceState[3]
    val temp3 = this[1].faceState[0]
    this[1].faceState[6] = this[3].faceState[2]
    this[1].faceState[3] = this[3].faceState[5]
    this[1].faceState[0] = this[3].faceState[8]
    this[3].faceState[2] = this[5].faceState[6]
    this[3].faceState[5] = this[5].faceState[3]
    this[3].faceState[8] = this[5].faceState[0]
    this[5].faceState[6] = this[0].faceState[6]
    this[5].faceState[3] = this[0].faceState[3]
    this[5].faceState[0] = this[0].faceState[0]
    this[0].faceState[6] = temp1
    this[0].faceState[3] = temp2
    this[0].faceState[0] = temp3
    return this
}

// ROTATE TOP FACE EDGES
fun List<MutableCubeFace>.handleRotateEdgesUpClockwise(): List<MutableCubeFace> {
    val temp1 = this[0].faceState[8]
    val temp2 = this[0].faceState[7]
    val temp3 = this[0].faceState[6]
    this[0].faceState[8] = this[2].faceState[8]
    this[0].faceState[7] = this[2].faceState[7]
    this[0].faceState[6] = this[2].faceState[6]
    this[2].faceState[8] = this[3].faceState[8]
    this[2].faceState[7] = this[3].faceState[7]
    this[2].faceState[6] = this[3].faceState[6]
    this[3].faceState[8] = this[4].faceState[8]
    this[3].faceState[7] = this[4].faceState[7]
    this[3].faceState[6] = this[4].faceState[6]
    this[4].faceState[8] = temp1
    this[4].faceState[7] = temp2
    this[4].faceState[6] = temp3
    return this
}

fun List<MutableCubeFace>.handleRotateEdgesUpCounterClockwise(): List<MutableCubeFace> {
    val temp1 = this[0].faceState[8]
    val temp2 = this[0].faceState[7]
    val temp3 = this[0].faceState[6]
    this[0].faceState[8] = this[4].faceState[8]
    this[0].faceState[7] = this[4].faceState[7]
    this[0].faceState[6] = this[4].faceState[6]
    this[4].faceState[8] = this[3].faceState[8]
    this[4].faceState[7] = this[3].faceState[7]
    this[4].faceState[6] = this[3].faceState[6]
    this[3].faceState[8] = this[2].faceState[8]
    this[3].faceState[7] = this[2].faceState[7]
    this[3].faceState[6] = this[2].faceState[6]
    this[2].faceState[8] = temp1
    this[2].faceState[7] = temp2
    this[2].faceState[6] = temp3
    return this
}

// ROTATE BOTTOM FACE EDGES

fun List<MutableCubeFace>.handleRotateEdgesDownClockwise(): List<MutableCubeFace> {
    val temp1 = this[0].faceState[2]
    val temp2 = this[0].faceState[1]
    val temp3 = this[0].faceState[0]
    this[0].faceState[2] = this[4].faceState[2]
    this[0].faceState[1] = this[4].faceState[1]
    this[0].faceState[0] = this[4].faceState[0]
    this[4].faceState[2] = this[3].faceState[2]
    this[4].faceState[1] = this[3].faceState[1]
    this[4].faceState[0] = this[3].faceState[0]
    this[3].faceState[2] = this[2].faceState[2]
    this[3].faceState[1] = this[2].faceState[1]
    this[3].faceState[0] = this[2].faceState[0]
    this[2].faceState[2] = temp1
    this[2].faceState[1] = temp2
    this[2].faceState[0] = temp3
    return this
}


fun List<MutableCubeFace>.handleRotateEdgesDownCounterClockwise(): List<MutableCubeFace> {
    val temp1 = this[0].faceState[2]
    val temp2 = this[0].faceState[1]
    val temp3 = this[0].faceState[0]
    this[0].faceState[2] = this[2].faceState[2]
    this[0].faceState[1] = this[2].faceState[1]
    this[0].faceState[0] = this[2].faceState[0]
    this[2].faceState[2] = this[3].faceState[2]
    this[2].faceState[1] = this[3].faceState[1]
    this[2].faceState[0] = this[3].faceState[0]
    this[3].faceState[2] = this[4].faceState[2]
    this[3].faceState[1] = this[4].faceState[1]
    this[3].faceState[0] = this[4].faceState[0]
    this[4].faceState[2] = temp1
    this[4].faceState[1] = temp2
    this[4].faceState[0] = temp3
    return this
}

// ROTATE ALL OF THE FRONT FACE OF ANY CUBE
fun List<MutableCubeFace>.handleRotateCubeFaceClockwise(faceNum: Int): List<MutableCubeFace> {
    // ROTATE CROSS
    val tempCross = this[faceNum].faceState[1]
    this[faceNum].faceState[1] = this[faceNum].faceState[3]
    this[faceNum].faceState[3] = this[faceNum].faceState[7]
    this[faceNum].faceState[7] = this[faceNum].faceState[5]
    this[faceNum].faceState[5] = tempCross
    // ROTATE DIAGONALS
    val tempDiagonal = this[faceNum].faceState[0]
    this[faceNum].faceState[0] = this[faceNum].faceState[6]
    this[faceNum].faceState[6] = this[faceNum].faceState[8]
    this[faceNum].faceState[8] = this[faceNum].faceState[2]
    this[faceNum].faceState[2] = tempDiagonal
    return this
}

fun List<MutableCubeFace>.handleRotateCubeFaceCounterClockwise(faceNum: Int): List<MutableCubeFace> {
    // ROTATE CROSS
    val tempCross = this[faceNum].faceState[3]
    this[faceNum].faceState[3] = this[faceNum].faceState[1]
    this[faceNum].faceState[1] = this[faceNum].faceState[5]
    this[faceNum].faceState[5] = this[faceNum].faceState[7]
    this[faceNum].faceState[7] = tempCross
    // ROTATE DIAGONALS
    val tempDiagonal = this[faceNum].faceState[8]
    this[faceNum].faceState[8] = this[faceNum].faceState[6]
    this[faceNum].faceState[6] = this[faceNum].faceState[0]
    this[faceNum].faceState[0] = this[faceNum].faceState[2]
    this[faceNum].faceState[2] = tempDiagonal
    return this
}
