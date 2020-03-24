package com.dlc.corda.utilities

// ROTATE FRONT FACE EDGES
// TODO: Research matrix transformations
fun List<MutableComponentCube>.handleRotateEdgesFrontClockwise(): List<MutableComponentCube> {
    val temp1 = this[1].sides[0]
    val temp2 = this[1].sides[1]
    val temp3 = this[1].sides[2]
    this[1].sides[0] = this[4].sides[6]
    this[1].sides[1] = this[4].sides[3]
    this[1].sides[2] = this[4].sides[0]
    this[4].sides[6] = this[5].sides[8]
    this[4].sides[3] = this[5].sides[7]
    this[4].sides[0] = this[5].sides[6]
    this[5].sides[8] = this[2].sides[2]
    this[5].sides[7] = this[2].sides[5]
    this[5].sides[6] = this[2].sides[8]
    this[2].sides[2] = temp1
    this[2].sides[5] = temp2
    this[2].sides[8] = temp3
    return this
}

fun List<MutableComponentCube>.handleRotateEdgesFrontCounterClockwise(): List<MutableComponentCube> {
    val temp1 = this[1].sides[0]
    val temp2 = this[1].sides[1]
    val temp3 = this[1].sides[2]
    this[1].sides[0] = this[2].sides[2]
    this[1].sides[1] = this[2].sides[5]
    this[1].sides[2] = this[2].sides[8]
    this[2].sides[2] = this[5].sides[8]
    this[2].sides[5] = this[5].sides[7]
    this[2].sides[8] = this[5].sides[6]
    this[5].sides[8] = this[4].sides[6]
    this[5].sides[7] = this[4].sides[3]
    this[5].sides[6] = this[4].sides[0]
    this[4].sides[6] = temp1
    this[4].sides[3] = temp2
    this[4].sides[0] = temp3
    return this
}

// ROTATE BACK FACE EDGES
fun List<MutableComponentCube>.handleRotateEdgesBackClockwise(): List<MutableComponentCube> {
    val temp1 = this[1].sides[6]
    val temp2 = this[1].sides[7]
    val temp3 = this[1].sides[8]
    this[1].sides[6] = this[2].sides[0]
    this[1].sides[7] = this[2].sides[3]
    this[1].sides[8] = this[2].sides[6]
    this[2].sides[0] = this[5].sides[2]
    this[2].sides[3] = this[5].sides[1]
    this[2].sides[6] = this[5].sides[0]
    this[5].sides[2] = this[4].sides[8]
    this[5].sides[1] = this[4].sides[5]
    this[5].sides[0] = this[4].sides[2]
    this[4].sides[8] = temp1
    this[4].sides[5] = temp2
    this[4].sides[2] = temp3
    return this
}

fun List<MutableComponentCube>.handleRotateEdgesBackCounterClockwise(): List<MutableComponentCube> {
    val temp1 = this[2].sides[0]
    val temp2 = this[2].sides[3]
    val temp3 = this[2].sides[6]
    this[2].sides[0] = this[1].sides[6]
    this[2].sides[3] = this[1].sides[7]
    this[2].sides[6] = this[1].sides[8]
    this[1].sides[6] = this[4].sides[8]
    this[1].sides[7] = this[4].sides[5]
    this[1].sides[8] = this[4].sides[2]
    this[4].sides[8] = this[5].sides[2]
    this[4].sides[5] = this[5].sides[1]
    this[4].sides[2] = this[5].sides[0]
    this[5].sides[2] = temp1
    this[5].sides[1] = temp2
    this[5].sides[0] = temp3
    return this
}

// ROTATE LEFT FACE EDGES
fun List<MutableComponentCube>.handleRotateEdgesLeftClockwise(): List<MutableComponentCube> {
    val temp1 = this[1].sides[2]
    val temp2 = this[1].sides[5]
    val temp3 = this[1].sides[8]
    this[1].sides[2] = this[3].sides[6]
    this[1].sides[5] = this[3].sides[3]
    this[1].sides[8] = this[3].sides[0]
    this[3].sides[6] = this[5].sides[2]
    this[3].sides[3] = this[5].sides[5]
    this[3].sides[0] = this[5].sides[8]
    this[5].sides[2] = this[0].sides[2]
    this[5].sides[5] = this[0].sides[5]
    this[5].sides[8] = this[0].sides[8]
    this[0].sides[2] = temp1
    this[0].sides[5] = temp2
    this[0].sides[8] = temp3
    return this
}

fun List<MutableComponentCube>.handleRotateEdgesLeftCounterClockwise(): List<MutableComponentCube> {
    val temp1 = this[1].sides[2]
    val temp2 = this[1].sides[5]
    val temp3 = this[1].sides[8]
    this[1].sides[2] = this[0].sides[2]
    this[1].sides[5] = this[0].sides[5]
    this[1].sides[8] = this[0].sides[8]
    this[0].sides[2] = this[5].sides[2]
    this[0].sides[5] = this[5].sides[5]
    this[0].sides[8] = this[5].sides[8]
    this[5].sides[2] = this[3].sides[6]
    this[5].sides[5] = this[3].sides[3]
    this[5].sides[8] = this[3].sides[0]
    this[3].sides[6] = temp1
    this[3].sides[3] = temp2
    this[3].sides[0] = temp3
    return this
}

// ROTATE RIGHT FACE EDGES
fun List<MutableComponentCube>.handleRotateEdgesRightClockwise(): List<MutableComponentCube> {
    val temp1 = this[1].sides[0]
    val temp2 = this[1].sides[3]
    val temp3 = this[1].sides[6]
    this[1].sides[0] = this[0].sides[0]
    this[1].sides[3] = this[0].sides[3]
    this[1].sides[6] = this[0].sides[6]
    this[0].sides[0] = this[5].sides[0]
    this[0].sides[3] = this[5].sides[3]
    this[0].sides[6] = this[5].sides[6]
    this[5].sides[0] = this[3].sides[8]
    this[5].sides[3] = this[3].sides[5]
    this[5].sides[6] = this[3].sides[2]
    this[3].sides[8] = temp1
    this[3].sides[5] = temp2
    this[3].sides[2] = temp3
    return this
}

fun List<MutableComponentCube>.handleRotateEdgesRightCounterClockwise(): List<MutableComponentCube> {
    val temp1 = this[1].sides[6]
    val temp2 = this[1].sides[3]
    val temp3 = this[1].sides[0]
    this[1].sides[6] = this[3].sides[2]
    this[1].sides[3] = this[3].sides[5]
    this[1].sides[0] = this[3].sides[8]
    this[3].sides[2] = this[5].sides[6]
    this[3].sides[5] = this[5].sides[3]
    this[3].sides[8] = this[5].sides[0]
    this[5].sides[6] = this[0].sides[6]
    this[5].sides[3] = this[0].sides[3]
    this[5].sides[0] = this[0].sides[0]
    this[0].sides[6] = temp1
    this[0].sides[3] = temp2
    this[0].sides[0] = temp3
    return this
}

// ROTATE TOP FACE EDGES
fun List<MutableComponentCube>.handleRotateEdgesUpClockwise(): List<MutableComponentCube> {
    val temp1 = this[0].sides[8]
    val temp2 = this[0].sides[7]
    val temp3 = this[0].sides[6]
    this[0].sides[8] = this[2].sides[8]
    this[0].sides[7] = this[2].sides[7]
    this[0].sides[6] = this[2].sides[6]
    this[2].sides[8] = this[3].sides[8]
    this[2].sides[7] = this[3].sides[7]
    this[2].sides[6] = this[3].sides[6]
    this[3].sides[8] = this[4].sides[8]
    this[3].sides[7] = this[4].sides[7]
    this[3].sides[6] = this[4].sides[6]
    this[4].sides[8] = temp1
    this[4].sides[7] = temp2
    this[4].sides[6] = temp3
    return this
}

fun List<MutableComponentCube>.handleRotateEdgesUpCounterClockwise(): List<MutableComponentCube> {
    val temp1 = this[0].sides[8]
    val temp2 = this[0].sides[7]
    val temp3 = this[0].sides[6]
    this[0].sides[8] = this[4].sides[8]
    this[0].sides[7] = this[4].sides[7]
    this[0].sides[6] = this[4].sides[6]
    this[4].sides[8] = this[3].sides[8]
    this[4].sides[7] = this[3].sides[7]
    this[4].sides[6] = this[3].sides[6]
    this[3].sides[8] = this[2].sides[8]
    this[3].sides[7] = this[2].sides[7]
    this[3].sides[6] = this[2].sides[6]
    this[2].sides[8] = temp1
    this[2].sides[7] = temp2
    this[2].sides[6] = temp3
    return this
}

// ROTATE BOTTOM FACE EDGES

fun List<MutableComponentCube>.handleRotateEdgesDownClockwise(): List<MutableComponentCube> {
    val temp1 = this[0].sides[2]
    val temp2 = this[0].sides[1]
    val temp3 = this[0].sides[0]
    this[0].sides[2] = this[4].sides[2]
    this[0].sides[1] = this[4].sides[1]
    this[0].sides[0] = this[4].sides[0]
    this[4].sides[2] = this[3].sides[2]
    this[4].sides[1] = this[3].sides[1]
    this[4].sides[0] = this[3].sides[0]
    this[3].sides[2] = this[2].sides[2]
    this[3].sides[1] = this[2].sides[1]
    this[3].sides[0] = this[2].sides[0]
    this[2].sides[2] = temp1
    this[2].sides[1] = temp2
    this[2].sides[0] = temp3
    return this
}


fun List<MutableComponentCube>.handleRotateEdgesDownCounterClockwise(): List<MutableComponentCube> {
    val temp1 = this[0].sides[2]
    val temp2 = this[0].sides[1]
    val temp3 = this[0].sides[0]
    this[0].sides[2] = this[2].sides[2]
    this[0].sides[1] = this[2].sides[1]
    this[0].sides[0] = this[2].sides[0]
    this[2].sides[2] = this[3].sides[2]
    this[2].sides[1] = this[3].sides[1]
    this[2].sides[0] = this[3].sides[0]
    this[3].sides[2] = this[4].sides[2]
    this[3].sides[1] = this[4].sides[1]
    this[3].sides[0] = this[4].sides[0]
    this[4].sides[2] = temp1
    this[4].sides[1] = temp2
    this[4].sides[0] = temp3
    return this
}

// ROTATE ALL OF THE FRONT FACE OF ANY CUBE
fun List<MutableComponentCube>.handleRotateCubeFaceClockwise(faceNum: Int): List<MutableComponentCube> {
    // ROTATE CROSS
    val tempCross = this[faceNum].sides[1]
    this[faceNum].sides[1] = this[faceNum].sides[3]
    this[faceNum].sides[3] = this[faceNum].sides[7]
    this[faceNum].sides[7] = this[faceNum].sides[5]
    this[faceNum].sides[5] = tempCross
    // ROTATE DIAGONALS
    val tempDiagonal = this[faceNum].sides[0]
    this[faceNum].sides[0] = this[faceNum].sides[6]
    this[faceNum].sides[6] = this[faceNum].sides[8]
    this[faceNum].sides[8] = this[faceNum].sides[2]
    this[faceNum].sides[2] = tempDiagonal
    return this
}

fun List<MutableComponentCube>.handleRotateCubeFaceCounterClockwise(faceNum: Int): List<MutableComponentCube> {
    // ROTATE CROSS
    val tempCross = this[faceNum].sides[3]
    this[faceNum].sides[3] = this[faceNum].sides[1]
    this[faceNum].sides[1] = this[faceNum].sides[5]
    this[faceNum].sides[5] = this[faceNum].sides[7]
    this[faceNum].sides[7] = tempCross
    // ROTATE DIAGONALS
    val tempDiagonal = this[faceNum].sides[8]
    this[faceNum].sides[8] = this[faceNum].sides[6]
    this[faceNum].sides[6] = this[faceNum].sides[0]
    this[faceNum].sides[0] = this[faceNum].sides[2]
    this[faceNum].sides[2] = tempDiagonal
    return this
}
