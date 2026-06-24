# Game Character — RTS Unit Behavior Simulation in Kotlin

> **TL;DR**: Implements movement, combat, and transport mechanics for a medieval RTS game using Strategy and Factory patterns, demonstrating OCP-compliant object-oriented design in Kotlin.

---

## Problem Statement

Modeling diverse unit behaviors (ground movement, aerial flight, melee vs. ranged attack, troop transport) without conditional branching leads to brittle code that breaks whenever new unit types are added.

- Hardcoding behavior per unit class violates the Open-Closed Principle
- Attack validity rules (e.g., ground units cannot melee-attack airborne targets) need a clean abstraction boundary
- Transport systems (boarding, capacity management, disembarkation) require encapsulated state tracking

---

## Approach

- **Strategy Pattern for movement and attack**: `MoveStrategy` and `AttackStrategy` are injected at construction time via `UnitFactory`, eliminating per-unit conditionals and making new unit types a matter of composing existing strategies
- **Factory Pattern for consistent creation**: `UnitFactory` centralizes strategy assignment, preventing inconsistent initialization across call sites
- **Interface Segregation**: `Movable` and `Attacker` are separate interfaces — `Shuttle` implements `Movable` only, since transport units don't engage in combat
- **Encapsulated position mutation**: `UnitBase.position` is read-only externally; only the internal `relocate()` method modifies it, preventing external state corruption

---

## Key Results

| Scenario | Detail |
|----------|--------|
| Units | Knight ×16, Archer ×16, Shuttle ×4 (capacity 8), Griffin ×5 |
| Boarding | Round-robin assignment of 32 ground units across 4 shuttles |
| Cross-domain attack | Ground melee units correctly refuse to attack airborne targets |
| Transport cycle | Board → fly to (10,10) → disembark → attack sequence (12 interactions) |

---

## Tech Stack

| Layer | Technology |
|-------|-----------|
| Language | Kotlin 1.9.0 |
| Runtime | JVM |
| Build | Gradle (Wrapper included) |

---

## Project Structure

```
src/main/kotlin/rts/
├── Point.kt          # 2D coordinate
├── Abstractions.kt   # Movable, Attacker interfaces
├── Strategies.kt     # MoveStrategy, AttackStrategy implementations
├── Units.kt          # UnitBase, Knight, Archer, Griffin, Shuttle, UnitFactory
└── Main.kt           # Scenario execution
```

---

## Getting Started

```bash
./gradlew run
```

---

## Limitations & Future Work

- No combat stats (HP, damage, range) — attack interactions are behavioral demonstrations only
- Pathfinding is direct point-to-point; terrain, obstacles, and movement speed are not modeled
- Potential extensions: A\* pathfinding, area-of-effect attacks, multi-target boarding priority

---

## License

MIT License — see [LICENSE](LICENSE) for details.

---

## Author

**Seoyeon Kim** | Undergraduate Student  
[GitHub](https://github.com/gksmfly) · [Email](mailto:gimhaneul24@gmail.com)
