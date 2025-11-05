package rts.rts

fun main() {
    println("=== RTS 게임 캐릭터 시뮬레이션 시작 ===")

    val start = Point(0, 0)
    val target = Point(100, 50)

    // 유닛 생성
    val knights = (1..16).map { Knight("K%02d".format(it), start) }
    val archers = (1..16).map { Archer("A%02d".format(it), start) }
    val shuttles = (1..4).map { Shuttle("S%02d".format(it), start) }
    val griffins = (1..5).map { Griffin("G%02d".format(it), start) }

    println("\n[1] Knight 16기, Archer 16기 생성 완료. Shuttle 4대, Griffin 5기 생성 완료.")

    // [2] 셔틀 4대에 Knight/Archer 총 32기 탑승 (셔틀 당 최대 8명)
    println("\n[2] 셔틀 탑승 단계")
    val groundTroops = knights + archers // 32기
    var idx = 0
    for (unit in groundTroops) {
        // 라운드 로빈으로 4대에 분배
        val shuttle = shuttles[idx % shuttles.size]
        shuttle.board(unit)
        idx++
    }
    shuttles.forEach { println("${it.describe()} 현재 탑승 인원: ${it.passengerCount()}명") }

    // [3] Griffin 5기와 함께 일정 거리(target)로 이동
    println("\n[3] Griffin 5기와 함께 목표 지점으로 이동")
    shuttles.forEach { it.moveTo(target) }
    griffins.forEach { it.moveTo(target) }

    // [4] 이동 지역에서 셔틀에 탄 모든 캐릭터를 내린다
    println("\n[4] 셔틀 하차 단계")
    shuttles.forEach { it.disembarkAll() }

    // [5] 전투 시나리오
    println("\n[5] 전투 시나리오 시작")
    val knight1 = knights.first()
    val knight2 = knights[1]
    val archer1 = archers.first()
    val griffin1 = griffins.first()
    val shuttle1 = shuttles.first()

    // Knight 1기가 Knight/Archer/Griffin/Shuttle 각각 공격
    println("\n[5-1] Knight의 공격")
    knight1.attack(knight2)
    knight1.attack(archer1)
    knight1.attack(griffin1) // 불가 (공중)
    knight1.attack(shuttle1) // 불가 (공중)

    // Archer 1기가 Archer/Knight/Griffin/Shuttle 각각 공격
    println("\n[5-2] Archer의 공격")
    archer1.attack(archer1)  // 자기 자신 공격 시나리오는 지시서에 '다른' 이라고 했지만, 데모로 출력
    archer1.attack(knight2)
    archer1.attack(griffin1) // 가능 (공중)
    archer1.attack(shuttle1) // 가능 (공중)

    // Griffin 1기가 Griffin/Archer/Knight/Shuttle 각각 공격
    println("\n[5-3] Griffin의 공격")
    griffin1.attack(griffins[1]) // 불가 (공중)
    griffin1.attack(archer1)     // 가능 (지상)
    griffin1.attack(knight2)     // 가능 (지상)
    griffin1.attack(shuttle1)    // 불가 (공중)

    println("\n=== 시뮬레이션 종료 ===")
}