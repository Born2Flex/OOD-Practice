# OOD Practice

Working through [ood-guide.md](ood-guide.md) (middle → senior Java backend).

## Current: Week 1, design principles beyond SOLID + ShopFlow kickoff

1. [ ] **Read** *Clean Architecture* Part IV, Larman Ch. 17, 18, 25 (GRASP), connascence.io, and Fowler's "TellDontAsk" and "AnemicDomainModel".
2. [x] **Pick** one real module from a company project for steps 3–4.
3. [x] **Draw** its package dependencies, mark the cycles, and compute I = Ce / (Ca + Ce) for the main packages → [docs/diagrams/01-package-dependencies.md](docs/diagrams/01-package-dependencies.md)
4. [x] **Write** a 1–2 page critique of the module (GRASP, connascence, component principles) with fixes ranked by value → [docs/essays/01-design-critique.md](docs/essays/01-design-critique.md)
5. [x] **Code** in `shopflow/`:
   - pricing and discounts (Strategy + Composite + Specification)
   - order lifecycle (State, or a sealed type + `switch`)
   - notifications (Observer via Spring events)
   - payment providers (Adapter + Factory)
6. [x] **Justify** each pattern choice in 1–2 sentences, including where you chose *not* to use one → [shopflow/README.md](shopflow/README.md)
7. [ ] **Reflect:** fill in the reflection and prep questions → [notes/week-01.md](notes/week-01.md)
8. [ ] **Send** the links to your mentor a day before the session.

## Progress
| Week | Topic | Done |
|------|-------|------|
| 1 | Design principles beyond SOLID + ShopFlow kickoff | [ ] |
| 2 | Code smells, refactoring to patterns | [ ] |
| 3 | Legacy code, changing without breaking the design | [ ] |
| 4 | PoEAA: layering, domain logic, O-R mapping | [ ] |
| 5 | PoEAA: concurrency, distribution, base patterns | [ ] |
| 6 | Hexagonal / Clean / Onion + Modular Monolith | [ ] |
| 7 | Domain-Driven Design | [ ] |
| 8 | CQRS, Event Sourcing, EDA (Experience point #1) | [ ] |
| 9 | Why (not) microservices, decomposition | [ ] |
| 10 | Distributed systems fundamentals | [ ] |
| 11 | Communication & integration | [ ] |
| 12 | Data management | [ ] |
| 13 | Resilience, observability, security | [ ] |
| 14 | Testing & deployment | [ ] |
| 15–16 | Capstone (Experience point #2) | [ ] |
