package rts

/** 공통 유닛 베이스: 캡슐화 + 전략 주입 */
open class UnitBase(
    val name: String,
    start: Point,
    val domain: Domain,
    private var moveStrategy: MoveStrategy? = null,
    private var attackStrategy: AttackStrategy? = null,
) : Movable, Attacker {

    private var _position: Point = start
    val position: Point get() = _position

    /** 수송기 탑승 상태 추적 */
    var carrier: Shuttle? = null
        private set

    /** 전략 교체(확장 대비) */
    fun setMoveStrategy(s: MoveStrategy?) { moveStrategy = s }
    fun setAttackStrategy(s: AttackStrategy?) { attackStrategy = s }

    /** 전략에서만 위치를 수정하도록 내부 메서드 제공 */
    internal fun relocate(to: Point) { _position = to }

    internal fun setCarrier(s: Shuttle?) { carrier = s }

    override fun moveTo(target: Point) {
        (moveStrategy ?: error("$name: 이동 전략이 없습니다.")).move(this, target)
    }

    override fun attack(target: UnitBase) {
        (attackStrategy ?: NoAttack).attack(this, target)
    }
}

/** 구체 유닛들 */
class Knight(name: String, position: Point) :
    UnitBase(name, position, Domain.GROUND, RideMove, MeleeAttack)

class Archer(name: String, position: Point) :
    UnitBase(name, position, Domain.GROUND, WalkMove, ArrowAttack)

class Griffin(name: String, position: Point) :
    UnitBase(name, position, Domain.AIR, FlyMove, GriffinClawAttack)

/** 수송선: 비행 + 탑승/하차 */
class Shuttle(
    name: String,
    position: Point,
    val capacity: Int = 8
) : UnitBase(name, position, Domain.AIR, FlyMove, NoAttack) {

    private val passengers = mutableListOf<UnitBase>()

    /**
     * Knight/Archer만 탑승 허용 + 정원/중복 체크
     */
    fun board(u: UnitBase): Boolean {
        if (u !is Knight && u !is Archer) {
            println("$name: ${u.name}는 탑승할 수 없습니다.")
            return false
        }
        if (passengers.size >= capacity) {
            println("$name: 정원($capacity) 초과로 탑승 실패")
            return false
        }
        if (u in passengers) {
            println("$name: ${u.name}는 이미 탑승 중입니다.")
            return false
        }
        passengers += u
        u.setCarrier(this)
        println("${u.name}가 $name 에 탑승합니다.")
        return true
    }

    /** 다 내리고 현재 셔틀 위치로 배치 */
    fun disembarkAll(): List<UnitBase> {
        val dropped = passengers.toList()
        passengers.clear()
        dropped.forEach { u ->
            u.setCarrier(null)
            u.relocate(this.position)
            println("${u.name}가 $name 에서 내립니다. 위치: ${position.fmt()}")
        }
        return dropped
    }

    /** 현재 승객 수 조회(디버깅용) */
    fun passengerCount(): Int = passengers.size
}

/** 팩토리: 생성 & 전략 조합 일원화 */
object UnitFactory {
    fun createKnight(index: Int, p: Point) = Knight("기사$index", p)
    fun createArcher(index: Int, p: Point) = Archer("궁수$index", p)
    fun createGriffin(index: Int, p: Point) = Griffin("그리핀$index", p)
    fun createShuttle(index: Int, p: Point, capacity: Int = 8) = Shuttle("셔틀$index", p, capacity)

    /** (선택) 문자열 타입 생성 – UML 호환 */
    fun createUnit(type: String, p: Point): UnitBase = when (type.lowercase()) {
        "knight", "기사" -> createKnight(0, p)
        "archer", "궁수" -> createArcher(0, p)
        "griffin", "그리핀" -> createGriffin(0, p)
        "shuttle", "셔틀" -> createShuttle(0, p)
        else -> error("알 수 없는 타입: $type")
    }
}
