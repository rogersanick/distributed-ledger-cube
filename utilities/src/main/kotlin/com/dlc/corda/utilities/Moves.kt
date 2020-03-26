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