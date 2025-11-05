package rts

enum class Domain { GROUND, AIR }

interface Movable {
    fun moveTo(target: Point)
}

interface Attacker {
    fun attack(target: UnitBase)
}

open class UnitBase(
    val name: String,
    var position: Point,
    open val domain: Domain
)
