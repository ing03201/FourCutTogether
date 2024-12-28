package com.foke.together.domain.interactor.entity

enum class CutFrameType {
//    MAKER_FAIRE,
//    FOURCUT_LIGHT,
//    FOURCUT_DARK;
    YELLOW,
    SKY_BLUE,
    MAGENTA;
    companion object {
        fun findBy(name: String): CutFrameType {
            return when (name) {
                YELLOW.name -> YELLOW
                SKY_BLUE.name -> SKY_BLUE
                MAGENTA.name -> MAGENTA
                else -> throw IllegalArgumentException("Unknown value: $name")
            }
        }
        fun findBy(ordinal: Int): CutFrameType {
            return when (ordinal) {
                YELLOW.ordinal -> YELLOW
                SKY_BLUE.ordinal -> SKY_BLUE
                MAGENTA.ordinal -> MAGENTA
                else -> throw IllegalArgumentException("Unknown value: $ordinal")
            }
        }
    }
}