package rts.rts

import rts.rts.Point

interface Movable {
    fun moveTo(point: Point)
}

abstract class UnitBase(
    val id: String,
    var position: Point,
    val isFlying: Boolean
) : Movable {
    abstract val typeName: String
    open fun describe(): String = "$typeName#$id"
}

interface Attacker {
    fun attack(target: UnitBase)
}