package rts.rts

// Knight, Archer, Shuttle, Griffin 규칙은 과제 명세를 그대로 반영
// - Knight/Archer/Griffin: 공격 가능 (단, 제약 다름)
// - Shuttle: 탑승/이동만, 공격 불가
// - 이동 메시지는 한글, System.out.println 사용 (println 사용)

class Knight(id: String, position: Point) :
    UnitBase(id, position, isFlying = false), Attacker {

    override val typeName: String = "Knight"

    override fun moveTo(point: Point) {
        println("${describe()}가 말을 타고 ${position}에서 ${point}로 이동합니다.")
        position = point
    }

    override fun attack(target: UnitBase) {
        if (target.isFlying) {
            println("${describe()} → ${target.describe()} : 공격할 수 없습니다 (날아다니는 캐릭터는 Knight가 공격 불가).")
            return
        }
        println("${describe()}가 말 위에서 창으로 ${target.describe()}를 찌릅니다!")
    }
}

class Archer(id: String, position: Point) :
    UnitBase(id, position, isFlying = false), Attacker {

    override val typeName: String = "Archer"

    override fun moveTo(point: Point) {
        println("${describe()}가 걸어서(느림) ${position}에서 ${point}로 이동합니다.")
        position = point
    }

    override fun attack(target: UnitBase) {
        val domain = if (target.isFlying) "하늘의" else "땅의"
        println("${describe()}가 화살을 쏩니다! (${domain} ${target.describe()} 명중)")
    }
}

class Griffin(id: String, position: Point) :
    UnitBase(id, position, isFlying = true), Attacker {

    override val typeName: String = "Griffin"

    override fun moveTo(point: Point) {
        println("${describe()}이(가) 날아서 ${position}에서 ${point}로 이동합니다.")
        position = point
    }

    override fun attack(target: UnitBase) {
        if (target.isFlying) {
            println("${describe()} → ${target.describe()} : 공격할 수 없습니다 (Griffin은 하늘의 적을 공격 불가).")
            return
        }
        println("${describe()}이(가) 하늘에서 번개를 내려쳐 ${target.describe()}를 타격합니다!")
    }
}

class Shuttle(id: String, position: Point) :
    UnitBase(id, position, isFlying = true) {

    override val typeName: String = "Shuttle"

    private val capacity = 8
    private val passengers = mutableListOf<UnitBase>()

    override fun moveTo(point: Point) {
        println("${describe()}이(가) 날아서 ${position}에서 ${point}로 이동합니다. (탑승 인원: ${passengers.size}명)")
        position = point
        // 탑승객 위치도 함께 이동(공중 수송)
        passengers.forEach { it.position = point }
    }

    fun board(unit: UnitBase): Boolean {
        // 탑승 가능: Knight, Archer (과제 명세)
        if (unit is Knight || unit is Archer) {
            if (passengers.size >= capacity) {
                println("${describe()} : 탑승 실패 - 정원 초과(최대 $capacity 명).")
                return false
            }
            passengers += unit
            println("${unit.describe()}가 ${describe()}에 탑승합니다. (현재 ${passengers.size}/$capacity)")
            return true
        } else {
            println("${describe()} : ${unit.describe()}는 탑승할 수 없습니다.")
            return false
        }
    }

    fun disembarkAll(): List<UnitBase> {
        if (passengers.isEmpty()) {
            println("${describe()} : 내릴 탑승객이 없습니다.")
            return emptyList()
        }
        println("${describe()} : 모든 탑승객 하차를 시작합니다.")
        val list = passengers.toList()
        passengers.clear()
        list.forEach {
            // 하차 위치는 셔틀의 현재 위치
            println("${it.describe()}가 ${describe()}에서 하차합니다. 위치=${position}")
            it.position = position
        }
        return list
    }

    fun passengerCount(): Int = passengers.size
}