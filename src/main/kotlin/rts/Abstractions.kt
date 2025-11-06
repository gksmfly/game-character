package rts

/** 지상/공중 도메인 */
enum class Domain { GROUND, AIR }

/** (선택) 종족 – 과제 확장 대비 */
enum class Race { HUMAN, FANTASY, AIRBORNE }

/** 이동 역할 */
interface Movable {
    fun moveTo(target: Point)
}

/** 공격 역할 */
interface Attacker {
    fun attack(target: UnitBase)
}

/** 이동 전략 */
interface MoveStrategy {
    fun move(self: UnitBase, to: Point)
}

/** 공격 전략 */
interface AttackStrategy {
    fun attack(self: UnitBase, target: UnitBase)
}
