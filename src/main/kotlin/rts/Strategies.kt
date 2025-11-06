package rts

/** 이동 전략들 */
object WalkMove : MoveStrategy {
    override fun move(self: UnitBase, to: Point) {
        val from = self.position
        self.relocate(to)
        println("${self.name}가 걸어서(느리게) ${from.fmt()} → ${to.fmt()} 이동")
    }
}

object RideMove : MoveStrategy {
    override fun move(self: UnitBase, to: Point) {
        val from = self.position
        self.relocate(to)
        println("${self.name}가 말을 타고 ${from.fmt()} → ${to.fmt()} 이동")
    }
}

object FlyMove : MoveStrategy {
    override fun move(self: UnitBase, to: Point) {
        val from = self.position
        self.relocate(to)
        println("${self.name}가 날아서 ${from.fmt()} → ${to.fmt()} 이동")
    }
}

/** 공격 전략들 */
object MeleeAttack : AttackStrategy {
    override fun attack(self: UnitBase, target: UnitBase) {
        if (target.domain == Domain.AIR) {
            println("${self.name}: 공중 유닛(${target.name})은 근접 공격할 수 없습니다.")
            return
        }
        println("${self.name}가 ${target.name}을(를) 근접 공격합니다.")
    }
}

object ArrowAttack : AttackStrategy {
    override fun attack(self: UnitBase, target: UnitBase) {
        println("${self.name}가 ${target.name}을(를) 화살로 공격합니다.")
    }
}

object NoAttack : AttackStrategy {
    override fun attack(self: UnitBase, target: UnitBase) {
        println("${self.name}: 공격 수단이 없습니다.")
    }
}

/** Griffin의 지상 강습(공중 유닛은 불가) */
object GriffinClawAttack : AttackStrategy {
    override fun attack(self: UnitBase, target: UnitBase) {
        if (target.domain == Domain.AIR) {
            println("${self.name}: 공중 유닛(${target.name})은 갈퀴 공격 불가")
            return
        }
        println("${self.name}가 ${target.name}을(를) 갈퀴로 강습합니다.")
    }
}
