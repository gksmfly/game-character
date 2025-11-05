package rts

// Knight: 말을 타고, 지상만 공격(창)
class Knight(
    name: String,
    pos: Point,
    var moveStrategy: MoveStrategy = HorseMove,
    var attackStrategy: AttackStrategy = LanceAttack
) : UnitBase(name, pos, Domain.GROUND), Movable, Attacker {

    override fun moveTo(target: Point) = moveStrategy.move(this, target)
    override fun attack(target: UnitBase) = attackStrategy.attack(this, target)
}

// Archer: 걸어서, 지상/공중 모두 공격(화살)
class Archer(
    name: String,
    pos: Point,
    var moveStrategy: MoveStrategy = WalkMove,
    var attackStrategy: AttackStrategy = ArrowAttack
) : UnitBase(name, pos, Domain.GROUND), Movable, Attacker {

    override fun moveTo(target: Point) = moveStrategy.move(this, target)
    override fun attack(target: UnitBase) = attackStrategy.attack(this, target)
}

// Griffin: 날아서, 지상만 공격(번개)
class Griffin(
    name: String,
    pos: Point,
    var moveStrategy: MoveStrategy = FlyMove,
    var attackStrategy: AttackStrategy = LightningAttack
) : UnitBase(name, pos, Domain.AIR), Movable, Attacker {

    override fun moveTo(target: Point) = moveStrategy.move(this, target)
    override fun attack(target: UnitBase) = attackStrategy.attack(this, target)
}

// Shuttle: 날아서, 공격 불가 + 탑승/하차
class Shuttle(
    name: String,
    pos: Point,
    var moveStrategy: MoveStrategy = FlyMove
) : UnitBase(name, pos, Domain.AIR), Movable {

    private val passengers = mutableListOf<UnitBase>()
    private val capacity = 8

    fun board(unit: UnitBase) {
        if (passengers.size >= capacity) {
            System.out.println("${name}는 더 이상 탑승할 수 없습니다. (정원 $capacity)")
            return
        }
        passengers += unit
        System.out.println("${unit.name}가 ${name}에 탑승합니다.")
    }

    override fun moveTo(target: Point) = moveStrategy.move(this, target)

    fun disembarkAll() {
        System.out.println("$name : 모든 승객을 내립니다.")
        val copy = passengers.toList()
        passengers.clear()
        copy.forEach {
            it.position = this.position
            System.out.println("${it.name}가 내립니다.")
        }
    }
}
