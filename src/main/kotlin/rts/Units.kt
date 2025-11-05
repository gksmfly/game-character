package rts
// Knight: 말을 타고 이동, 공중 유닛 공격 불가
class Knight(name: String, pos: Point) :
    UnitBase(name, pos, Domain.GROUND), Movable, Attacker {

    override fun moveTo(target: Point) {
        position = target
        System.out.println("${name}가 말을 타고 (${target.x}, ${target.y})로 이동합니다.")
    }

    override fun attack(target: UnitBase) {
        if (target.domain == Domain.AIR) {
            System.out.println("${name}가 ${target.name}을 공격할 수 없습니다. (공중 유닛)")
            return
        }
        System.out.println("${name}가 ${target.name}를 창으로 찌릅니다.")
    }
}

// Archer: 걸어서 이동(느림), 지상/공중 모두 공격 가능
class Archer(name: String, pos: Point) :
    UnitBase(name, pos, Domain.GROUND), Movable, Attacker {

    override fun moveTo(target: Point) {
        position = target
        System.out.println("${name}가 걸어서 (${target.x}, ${target.y})로 이동합니다.")
    }

    override fun attack(target: UnitBase) {
        System.out.println("${name}가 ${target.name}을 화살로 공격합니다.")
    }
}

// Griffin: 날아다님, 지상만 공격(번개 내리침)
class Griffin(name: String, pos: Point) :
    UnitBase(name, pos, Domain.AIR), Movable, Attacker {

    override fun moveTo(target: Point) {
        position = target
        System.out.println("${name}가 날아서 (${target.x}, ${target.y})로 이동합니다.")
    }

    override fun attack(target: UnitBase) {
        if (target.domain == Domain.AIR) {
            System.out.println("${name}가 ${target.name}을 공격할 수 없습니다. (공중 유닛)")
            return
        }
        System.out.println("${name}가 ${target.name}에게 번개를 내리칩니다.")
    }
}

// Shuttle: 비행, 공격 불가, Knight/Archer 탑승 최대 8
class Shuttle(name: String, pos: Point) :
    UnitBase(name, pos, Domain.AIR), Movable {

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

    override fun moveTo(target: Point) {
        position = target
        System.out.println("${name}가 날아서 (${target.x}, ${target.y})로 이동합니다.")
    }

    fun disembarkAll() {
        System.out.println("${name} : 모든 승객을 내립니다.")
        val copy = passengers.toList()
        passengers.clear()
        copy.forEach {
            it.position = this.position
            System.out.println("${it.name}가 내립니다.")
        }
    }
}
