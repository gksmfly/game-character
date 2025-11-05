package rts

/** 이동 전략 */
interface MoveStrategy {
    fun move(unit: UnitBase, target: Point)
}

/** 공격 전략 */
interface AttackStrategy {
    fun attack(attacker: UnitBase, target: UnitBase)
}

/* ===== 이동 전략 구현들 ===== */

object HorseMove : MoveStrategy {
    override fun move(unit: UnitBase, target: Point) {
        unit.position = target
        System.out.println("${unit.name}가 말을 타고 $target 로 이동합니다.")
    }
}

object WalkMove : MoveStrategy {
    override fun move(unit: UnitBase, target: Point) {
        unit.position = target
        System.out.println("${unit.name}가 걸어서 $target 로 이동합니다.")
    }
}

object FlyMove : MoveStrategy {
    override fun move(unit: UnitBase, target: Point) {
        unit.position = target
        System.out.println("${unit.name}가 날아서 $target 로 이동합니다.")
    }
}

/* ===== 공격 전략 구현들 ===== */

object LanceAttack : AttackStrategy {
    override fun attack(attacker: UnitBase, target: UnitBase) {
        if (target.domain == Domain.AIR) {
            System.out.println("${attacker.name}가 ${target.name}을 공격할 수 없습니다. (공중 유닛)")
            return
        }
        System.out.println("${attacker.name}가 ${target.name}를 창으로 찌릅니다.")
    }
}

object ArrowAttack : AttackStrategy {
    override fun attack(attacker: UnitBase, target: UnitBase) {
        System.out.println("${attacker.name}가 ${target.name}을 화살로 공격합니다.")
    }
}

object LightningAttack : AttackStrategy {
    override fun attack(attacker: UnitBase, target: UnitBase) {
        if (target.domain == Domain.AIR) {
            System.out.println("${attacker.name}가 ${target.name}을 공격할 수 없습니다. (공중 유닛)")
            return
        }
        System.out.println("${attacker.name}가 ${target.name}에게 번개를 내리칩니다.")
    }
}
