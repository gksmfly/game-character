package rts

fun main() {
    System.out.println("=== RTS 게임 캐릭터 시뮬레이션 시작 ===")

    val start = Point(0, 0)
    val target = Point(10, 10) // 슬라이드 예시 좌표

    // [1] 유닛 생성
    val knights  = (1..16).map { Knight("Knight$it",  start) }
    val archers  = (1..16).map { Archer("Archer$it",  start) }
    val shuttles = (1..4).map  { Shuttle("Shuttle$it", start) }
    val griffins = (1..5).map  { Griffin("Griffin$it", start) }

    System.out.println("[1] Knight 16기, Archer 16기 생성. Shuttle 4대, Griffin 5기 생성.")

    // [2] 셔틀 탑승 (라운드로빈, 최대 8명/대)
    System.out.println("[2] 셔틀 탑승 단계")
    val groundTroops = knights + archers
    groundTroops.forEachIndexed { idx, unit ->
        val shuttle = shuttles[idx % shuttles.size]
        shuttle.board(unit)
    }

    // [3] Griffin 5기와 함께 목표 지점으로 이동
    System.out.println("[3] Griffin 5기와 함께 목표 지점으로 이동")
    shuttles.forEach { it.moveTo(target) }
    griffins.forEach { it.moveTo(target) }

    // [4] 이동 지역에서 모든 탑승객 하차
    System.out.println("[4] 셔틀 하차 단계")
    shuttles.forEach { it.disembarkAll() }

    // [5] 전투 시나리오 (슬라이드 2페이지 지시 그대로)
    System.out.println("[5] 전투 시나리오 시작")

    val k1 = knights[0]
    val k2 = knights[1]
    val a1 = archers[0]
    val g1 = griffins[0]
    val s1 = shuttles[0]

    System.out.println("[5-1] Knight의 공격")
    k1.attack(k2)   // Knight1 -> Knight2
    k1.attack(a1)   // Knight1 -> Archer1
    k1.attack(g1)   // 공중 불가
    k1.attack(s1)   // 공중 불가

    System.out.println("[5-2] Archer의 공격")
    a1.attack(archers[1]) // Archer1 -> Archer2
    a1.attack(k2)         // Archer1 -> Knight2
    a1.attack(g1)         // Archer1 -> Griffin1
    a1.attack(s1)         // Archer1 -> Shuttle1

    System.out.println("[5-3] Griffin의 공격")
    g1.attack(griffins[1]) // 공중 불가
    g1.attack(a1)          // 지상
    g1.attack(k2)          // 지상
    g1.attack(s1)          // 공중 불가

    System.out.println("=== 시뮬레이션 종료 ===")
}
