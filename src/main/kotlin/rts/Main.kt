package rts


fun main() {
    val start = Point(0, 0)
    val target = Point(10, 10)

    // 16/16/4/5 생성
    val knights = (1..16).map { UnitFactory.createKnight(it, start) }
    val archers = (1..16).map { UnitFactory.createArcher(it, start) }
    val griffins = (1..5).map { UnitFactory.createGriffin(it, start) }
    val shuttles = (1..4).map { UnitFactory.createShuttle(it, start, capacity = 8) }

    // 탑승 분배 (셔틀당 정원 8)
    val queue = ArrayDeque<UnitBase>().apply {
        knights.forEach { add(it) }
        archers.forEach { add(it) }
    }
    for (s in shuttles) {
        repeat(s.capacity) {
            if (queue.isNotEmpty()) s.board(queue.removeFirst())
        }
        println("${s.name} 현재 승객: ${s.passengerCount()}명")
    }

    // 이동: Griffin + Shuttle
    println("\n=== 이동 시작 ===")
    griffins.forEach { it.moveTo(target) }
    shuttles.forEach { it.moveTo(target) }

    // 하차
    println("\n=== 하차 ===")
    shuttles.forEach { it.disembarkAll() }

    // 교차 공격 데모
    println("\n=== 교차 공격 ===")
    val k = knights.first()
    val a = archers.first()
    val g = griffins.first()

    k.attack(a)      // 지상 ↔ 지상 (가능)
    a.attack(k)      // 지상 ↔ 지상 (가능)
    k.attack(g)      // 기사 → 공중 (불가)
    a.attack(g)      // 궁수 → 공중 (가능)
    g.attack(k)      // 그리핀 → 지상 (가능)
    g.attack(a)      // 그리핀 → 지상 (가능)
}
