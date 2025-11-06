
## RTS(Real‑Time Strategy) 게임 캐릭터 시뮬레이션

> **과제명:** *게임 캐릭터와 행동 만들기 (homework02_GameCharacter.pdf)*  
> **제출 형태:** *Gradle 프로젝트 (gradle build / gradle run 가능 상태)*  
> **언어/런타임:** **Kotlin** (JVM)

---

### 1) 프로젝트 개요

중세 RTS를 가정하여 캐릭터(**Knight, Archer, Shuttle, Griffin**)의 **이동 · 공격 · 탑승**을 객체지향적으로 설계/구현합니다.  
PDF 명세의 필수 규칙을 만족하고, 설계 평가지표(중복 최소화/재사용/확장성)를 위해 **전략 패턴**과 **팩토리**를 사용했습니다.

---

### 2) 실행 방법

#### A) Gradle (권장)
`build.gradle.kts` 에 다음이 설정되어 있어야 합니다.
```kotlin
plugins {
    application
    kotlin("jvm") version "1.9.0" // 프로젝트 버전에 맞추세요
}
application {
    mainClass.set("hw2.game.MainKt")
}
```

실행:
```bash
./gradlew run
```

#### B) 배포 스크립트
```bash
./gradlew installDist
./build/install/game-character/bin/game-character   # (Windows: ...\bin\game-character.bat)
```
---

### 3) 패키지/파일 구조

```
src/
└─ main/
   └─ kotlin/rts/
      ├─ Point.kt
      ├─ Abstractions.kt
      ├─ Strategies.kt
      ├─ Units.kt
      └─ Main.kt
```

### 4) 설계(아키텍처) 개요

#### 4.1 전략 패턴
- **이동 / 공격**을 각각 `MoveStrategy` / `AttackStrategy`로 분리  
- 각 유닛은 생성 시 전략을 **주입**받아 동작 (조건문 최소화, 확장 용이)

#### 4.2 팩토리
- `UnitFactory`에서 유닛 생성/전략 조합을 일원화 (일관성, 테스트 용이)

#### 4.3 인터페이스 분리 & 캡슐화
- 역할별 인터페이스: `Movable`, `Attacker`  
- `UnitBase.position`은 읽기 전용(`val` getter), 내부 `relocate(...)`로만 변경  
- `Shuttle.board(...)`에서 타입/정원/중복을 체크, `carrier` 상태 추적

---

## 5) 클래스 다이어그램
```mermaid
classDiagram
direction LR

class Domain {
  <<enumeration>>
  GROUND
  AIR
}

class Point {
  +x: Int
  +y: Int
}

class Movable
<<interface>> Movable
Movable : +moveTo(target: Point)

class Attacker
<<interface>> Attacker
Attacker : +attack(target: UnitBase)

class MoveStrategy
<<interface>> MoveStrategy
MoveStrategy : +move(self: UnitBase, to: Point)

class AttackStrategy
<<interface>> AttackStrategy
AttackStrategy : +attack(self: UnitBase, target: UnitBase)

class UnitBase {
  +name: String
  +position: Point
  +domain: Domain
  +carrier: Shuttle?
  +setMoveStrategy(s: MoveStrategy?)
  +setAttackStrategy(s: AttackStrategy?)
  +moveTo(target: Point)
  +attack(target: UnitBase)
  -relocate(to: Point)
  -setCarrier(s: Shuttle?)
}

UnitBase ..|> Movable
UnitBase ..|> Attacker
UnitBase --> MoveStrategy : uses
UnitBase --> AttackStrategy : uses

class Knight
class Archer
class Griffin
class Shuttle {
  +capacity: Int
  +board(u: UnitBase): Boolean
  +disembarkAll(): List~UnitBase~
  +passengerCount(): Int
}

Knight --|> UnitBase
Archer --|> UnitBase
Griffin --|> UnitBase
Shuttle --|> UnitBase

class WalkMove
class RideMove
class FlyMove
class MeleeAttack
class ArrowAttack
class NoAttack
class GriffinClawAttack

WalkMove ..|> MoveStrategy
RideMove ..|> MoveStrategy
FlyMove ..|> MoveStrategy
MeleeAttack ..|> AttackStrategy
ArrowAttack ..|> AttackStrategy
NoAttack ..|> AttackStrategy
GriffinClawAttack ..|> AttackStrategy

class UnitFactory {
  +createKnight(i: Int, p: Point): Knight
  +createArcher(i: Int, p: Point): Archer
  +createGriffin(i: Int, p: Point): Griffin
  +createShuttle(i: Int, p: Point, cap: Int): Shuttle
}
```

---

## 6) 메인 시나리오 (요약)

1. **생성:** Knight 16, Archer 16, Shuttle 4(정원 8), Griffin 5 (모두 `Point(0,0)`)
2. **탑승:** Knight/Archer 32명을 셔틀 4대에 **라운드로빈**으로 배치
3. **이동:** Shuttle 4대 + Griffin 5기가 목표 좌표(`Point(10,10)`)로 이동
4. **하차:** 탑승객 전원 하차 → 하차 위치 = 셔틀 현재 위치
5. **공격:** Knight/Archer/Griffin 대표 1기씩 **총 12건**(Shuttle 포함) 호출

> *참고:* 과제 PDF의 “출력은 한글로” 조건에 맞춰 모든 과정이 한글 로그로 출력됩니다.

---

## 7) 테스트 & 예시 로그 (발췌)

```
=== 이동 시작 ===
그리핀1가 날아서 (0, 0) → (10, 10) 이동
셔틀1가 날아서 (0, 0) → (10, 10) 이동
...
=== 하차 ===
궁수1가 셔틀1 에서 내립니다. 위치: (10, 10)
...
=== 교차 공격 ===
기사1가 궁수2을(를) 근접 공격합니다.
기사1: 공중 유닛(그리핀1)은 근접 공격할 수 없습니다.
기사1: 공중 유닛(셔틀1)은 근접 공격할 수 없습니다.
궁수1가 셔틀1을(를) 화살로 공격합니다.
그리핀1: 공중 유닛(그리핀2)은 갈퀴 공격 불가
...
```
---

## 8) 설계 포인트(요약)

- **전략 패턴**: 이동/공격을 전략으로 분리해 **OCP/재사용성** 확보
- **팩토리**: 생성/전략 조합을 일원화 → **일관성/테스트 용이**
- **인터페이스 분리(ISP)**: 필요한 역할만 구현 (Shuttle은 공격 미구현)
- **캡슐화**: 위치 변경은 내부 메서드로만, 탑승 상태 추적(`carrier`)

---

## 9) 한계/확장 아이디어

- 체력/피해량/사거리 등 전투 시스템 확장
- 경로 탐색(A*), 장애물, 속도/지형 보정
- 복수 타겟/에어본 전용 무기 전략 추가(예: BallistaAttack)
