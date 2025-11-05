**RTS(Real-Time Strategy) 게임 캐릭터 시뮬레이션 과제**

> **과제명:** *게임 캐릭터와 행동 만들기 (homework02_GameCharacter.pdf)*  
> **제출 형태:** *Gradle 프로젝트 (gradle build / gradle run 가능 상태)*  
> **작성 언어:** **Kotlin**

---

### 1. 프로젝트 개요

중세시대를 배경으로 한 RTS 게임을 가정하여  
캐릭터(**Knight, Archer, Shuttle, Griffin**)의 **이동 · 공격 · 탑승** 동작을  
객체지향적으로 설계하고 구현한 프로그램입니다.

---

### 2. 구현 요구사항 요약

- **캐릭터 종류 및 규칙**
    - **Knight**: 말을 타고 이동, **지상만 공격 가능** (공중 유닛 공격 불가)
    - **Archer**: 걸어서 이동(느림), **지상/공중 모두 공격 가능**
    - **Griffin**: 날아서 이동, **하늘에서 지상만 공격**
    - **Shuttle**: 비행 유닛, **공격 불가**, Knight/Archer **최대 8기 탑승 가능**
- 모든 행동은 `println`으로 **한글 메시지** 출력
- 각 캐릭터의 동작 규칙은 **다형성**으로 처리 (오버라이드)

---

### 3. 개발 환경

| 항목 | 버전 |
|---|---|
| **JDK** | 24 (OpenJDK 24.0.2) |
| **Kotlin** | 2.2.0 |
| **Gradle DSL** | Kotlin |
| **IDE** | IntelliJ IDEA 2024.2 이상 |

---

### 4. 프로젝트 구조

```text
game-character/
├─ build.gradle.kts
├─ settings.gradle.kts
├─ src/
│  └─ main/
│     └─ kotlin/
│        └─ rts/
│           ├─ Point.kt
│           ├─ Abstractions.kt
│           ├─ Units.kt
│           └─ Main.kt
└─ README.md
```

---

### 5. 실행 방법

#### 프로젝트 빌드
```bash
# 프로젝트 최상위 폴더에서
./gradlew clean build
```

#### 실행
```bash
java -jar build/libs/convenience-store-system-1.0.0.jar
```

---

### 6. 객체지향 설계 포인트

| 원칙                           | 적용 내용                                                                                      |
|--------------------------------|-------------------------------------------------------------------------------------------------|
| Abstraction (추상화)           | 공통 행위를 `Movable`(이동), `Attacker`(공격) 인터페이스로 분리                                 |
| Encapsulation (캡슐화)         | `Shuttle`의 탑승자 목록을 `private`로 은닉, `board()`/`disembark()`로만 상태 변경               |
| Inheritance (상속)             | `UnitBase` 상속으로 공통 속성(`name`, `position`)과 기본 행위(`moveTo`) 재사용                  |
| Polymorphism (다형성)          | 각 유닛이 `moveTo`/`attack`를 오버라이드하여 다른 로직을 같은 방식으로 호출                     |
| SRP / OCP (단일 책임/개방-폐쇄)| 규칙은 각 클래스에 단일 책임으로 배치, 새 유닛 추가 시 기존 코드 수정 없이 확장 가능            |
