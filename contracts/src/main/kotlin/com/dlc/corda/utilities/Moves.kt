package com.dlc.corda.utilities

import net.corda.core.serialization.CordaSerializable

@CordaSerializable
enum class Moves {
    F, Fi,
    B, Bi,
    L, Li,
    R, Ri,
    D, Di,
    U, Ui
}

fun Moves.getOpposite() = when(this) {
    Moves.F -> Moves.Fi
    Moves.Fi -> Moves.F
    Moves.B -> Moves.Bi
    Moves.Bi -> Moves.B
    Moves.L -> Moves.Li
    Moves.Li -> Moves.L
    Moves.R -> Moves.Ri
    Moves.Ri -> Moves.R
    Moves.D -> Moves.Di
    Moves.Di -> Moves.D
    Moves.U -> Moves.Ui
    Moves.Ui -> Moves.U
}