# Mentee Guide: Enterprise & Distributed Architecture (Middle → Senior Java Backend)

> **Who this is for:** a middle backend Java developer working towards senior
> **Assumes you already know:** SOLID and the GoF design patterns
> **Duration:** ~16 weeks at 5–7 hours/week, plus a 1-hour session with your mentor each week
> **How it works:** read → build → reflect → discuss with your mentor. Every phase ends with a deliverable, and those deliverables are the evidence for your senior assessment.
> **Source plan:** `java-ood-study-plan-condensed.md`. Your mentor works from a companion mentor guide.

---

## 1. How to Use This Guide

### Your week
| Activity | Time |
|----------|------|
| Reading | 2–3 h |
| Hands-on (ShopFlow, katas, labs) | 2–3 h |
| Written reflection | 30 min |
| Mentor 1:1 | 1 h |

### Before every mentor session
- Push the week's code or document and send the link to your mentor a day ahead.
- Write a short reflection note (template below).
- Prepare answers to the week's **"Prepare for the session"** questions. You don't need to be right: be ready to explain your reasoning.
- Bring at least one thing you **disagree with** from the reading, or found unclear.

### What a session looks like
1. **10 min:** what you learned and what was unclear
2. **25 min:** you walk through your code or design; your mentor challenges it
3. **20 min:** discussion questions or a whiteboard design problem
4. **5 min:** agree on next week's focus

### Reflection note template (`notes/week-NN.md`)
```markdown
# Week NN: <topic>
## Three key ideas
## What I'd do differently in my current project
## Questions for my mentor
## Where I disagree with the reading, and why
```

### Suggested repository layout
```
shopflow/            # the running project (v1 → v2 → v3)
katas/               # refactoring katas
docs/
  adr/               # Architecture Decision Records (0001-*.md, ...)
  diagrams/          # C4 diagrams
  essays/            # trade-off essay, written comparison
notes/               # weekly reflection notes
```

---

## 2. What You Are Working Towards

| Requirement | How you will prove it |
|-------------|-----------------------|
| **R1.** Excellent use of OOD principles and design patterns for classes, modules and subsystems | Design critique, ShopFlow domain kickoff, refactoring katas |
| **R2.** Know the common enterprise architecture patterns (N-Tier, MVC/MVP flavors, Domain Model, Mapper, Unit of Work) and their pros and cons | Trade-off essay, ADRs, mentor Q&A |
| **R3.** Two experience points in higher-level architecture design | #1: ShopFlow modular monolith design (Phase 4). #2: Capstone proposal (Phase 6) |
| **R4.** Refactor an existing design into a proper combination of patterns | Refactoring katas, legacy-module refactoring, capstone refactoring plan |
| **R5.** Take on functional changes without breaking the architecture | Change requests from your mentor in Phases 2–5, live "what if" questions at the capstone defence |
| **Beyond** | Hexagonal + DDD (Phase 4), distributed systems and microservices (Phase 5) |

---

## 3. Roadmap and Progress Tracker

| Week | Phase | Topic | Done |
|------|-------|-------|------|
| 1 | 1 | Design principles beyond SOLID + ShopFlow kickoff | [ ] |
| 2 | 2 | Code smells, safe refactoring, refactoring to patterns | [ ] |
| 3 | 2 | Legacy code, changing code without breaking the design | [ ] |
| 4 | 3 | PoEAA: layering, domain logic, O-R mapping | [ ] |
| 5 | 3 | PoEAA: concurrency, distribution, base patterns | [ ] |
| 6 | 4 | Hexagonal / Clean / Onion + Modular Monolith | [ ] |
| 7 | 4 | Domain-Driven Design | [ ] |
| 8 | 4 | CQRS, Event Sourcing, Event-Driven Architecture (**Experience point #1**) | [ ] |
| 9 | 5 | Why (not) microservices, and decomposition | [ ] |
| 10 | 5 | Distributed systems fundamentals: scalability, availability, consistency, caching | [ ] |
| 11 | 5 | Communication & integration | [ ] |
| 12 | 5 | Data management | [ ] |
| 13 | 5 | Resilience, observability, security | [ ] |
| 14 | 5 | Testing & deployment | [ ] |
| 15–16 | 6 | Capstone: architecture design and defence (**Experience point #2**) | [ ] |

### The running project: ShopFlow

One domain carries through the whole plan: **"ShopFlow", an order and fulfillment system** with Orders, Catalog, Inventory, Payments, Shipping and Notifications. Each phase builds on the previous one:

| Phase | ShopFlow version |
|-------|------------------|
| 1–2 | Core domain classes: pricing, order lifecycle, notifications, payment providers |
| 3 | **v1:** classic layered Spring Boot app (Service Layer + Domain Model + JPA repositories) |
| 4 | **v2:** modular monolith, hexagonal inside each module, DDD aggregates, ArchUnit tests |
| 5 | **v3:** Orders, Payments and Inventory as separate services with a saga, outbox, resilience and caching |

From Phase 2 onwards your mentor will give you **change requests** (e.g., "add partial refunds", "support multi-warehouse stock", "add loyalty points"). The goal is always the same: make the change without breaking the architecture, and ideally by *adding* code rather than editing existing code.

**Tech stack:** Java 21+, Spring Boot 3.x, Spring Data JPA, PostgreSQL, Testcontainers, ArchUnit, Spring Modulith, Kafka or RabbitMQ, Resilience4j.

---

## 4. Phase 1: Design Principles Beyond SOLID + ShopFlow Kickoff (Week 1)

You already know SOLID and the GoF patterns. This week covers what SOLID doesn't: how to decide which class owns a responsibility, how to measure coupling, and how to design packages and modules. You'll need these for the modular monolith in Phase 4.

### Topics
- **GRASP** as a vocabulary for assigning responsibilities: Information Expert, Creator, Controller, Low Coupling, High Cohesion, Pure Fabrication, Indirection, Protected Variations
- **Coupling and cohesion**: kinds of coupling, **connascence** (static vs. dynamic, strength, locality)
- Encapsulation: Tell-Don't-Ask, Law of Demeter, avoiding the anemic model (as a preview)
- Immutability and value objects in Java (records, defensive copies)
- **Package and component principles**: REP, CCP, CRP, ADP, SDP, SAP, plus stability/abstractness metrics

### Reading
- *Clean Architecture* (R. Martin): Part IV (Component Principles)
- *Applying UML and Patterns* (C. Larman): GRASP chapters (17, 18, 25). Skim them if GRASP is familiar.
- connascence.io: the full taxonomy (short)
- M. Fowler, "TellDontAsk" and "AnemicDomainModel" (martinfowler.com/bliki)

### Deliverables
- [ ] **Design critique (1–2 pages)** of a real service or module from a company project, built around GRASP, coupling/connascence and component principles rather than a SOLID checklist. Cover the problems, their impact, and fixes ranked by value.
- [ ] **Package dependency diagram** of the same module: find cycles and compute instability (I = Ce / (Ca + Ce)) for the main packages.
- [ ] **ShopFlow domain kickoff:** implement
  - pricing and discounts (Strategy + Composite + Specification)
  - the order lifecycle (State, or a sealed type + `switch`)
  - notifications (Observer via Spring events)
  - payment provider integration (Adapter + Factory)

  This isn't pattern study. The point is to **choose and justify** each pattern, including the places where a plain `switch` on a sealed type is enough. Write one or two sentences per choice in the README.

### Prepare for the session
- Which GRASP principle explains why a `Controller` shouldn't contain business logic?
- Which kinds of connascence are hardest to spot, and how do they show up across module or service boundaries?
- Which package in your module breaks the Stable Dependencies or Stable Abstractions principle, and what would fixing it cost?
- In the ShopFlow kickoff, where did you choose *not* to use a pattern, and why?

---

## 5. Phase 2: Refactoring & Refactoring to Patterns (Weeks 2–3)

This phase is about changing existing code safely: requirements **R4** and **R5**.

### Week 2: Code smells, safe refactoring, refactoring to patterns

**Topics**
- Code smells: Long Method, Large Class, Feature Envy, Shotgun Surgery, Divergent Change, Primitive Obsession, Data Clumps, Switch Statements, Speculative Generality
- Safe refactoring workflow: characterization tests, small steps, IDE automated refactorings, keeping the build green
- Refactoring **toward** patterns and **away** from them

**Reading**
- *Refactoring*, 2nd ed. (M. Fowler): Ch. 1–3 in full, then use the catalogue as a reference
- *Refactoring to Patterns* (J. Kerievsky): Ch. 1–4, then these refactorings: *Replace Conditional Logic with Strategy*, *Replace State-Altering Conditionals with State*, *Move Embellishment to Decorator*, *Replace Constructors with Creation Methods*, *Extract Composite*, *Replace Implicit Tree with Composite*, *Introduce Null Object*

**Deliverables**
- [ ] **Katas** (github.com/emilybache): *Gilded Rose* (→ Strategy/State/Polymorphism), and *Tennis Refactoring* or *Trip Service* (→ dependency breaking and testability). Commit in small steps so your mentor can follow the sequence.

### Week 3: Legacy code, changing code without breaking the design

**Topics**
- Working with legacy code: seams, sprout/wrap method/class, breaking dependencies
- Keeping architecture intact under change: finding the right extension point, OCP in practice, "make the change easy, then make the easy change"

**Reading**
- *Working Effectively with Legacy Code* (M. Feathers): Ch. 1–4, 6, 8; skim Ch. 25 (dependency-breaking techniques)

**Deliverables**
- [ ] **Legacy refactoring:** pick a messy real module from a company project (or an open-source one) and deliver:
  - characterization tests
  - a sequence of small commits
  - a **before/after design note**: smells found, patterns applied, why, and what you deliberately did *not* do
- [ ] **Change request on ShopFlow** (your mentor gives it, e.g., "add a new payment provider and a buy-2-get-1 promotion"). Show that the change mostly **adds** code rather than modifying existing code.

### Prepare for the session
- How do you decide that a refactoring is worth doing in a sprint?
- How do you refactor safely with no tests?
- When is the right answer "remove the pattern"?

---

## 6. Phase 3: Enterprise Application Architecture (Weeks 4–5)

This phase covers **R2**. You'll read *Patterns of Enterprise Application Architecture* (M. Fowler, 2002) **selectively**. It was written in the J2EE/.NET 1.0 era. The architectural ideas still hold, but JPA/Hibernate and Spring now implement many of the patterns, so for those, knowing the idea is enough.

### PoEAA reading map

Legend: ✅ **Read carefully** · 🔎 **Skim** (understand the idea, know it exists) · ⏭ **Skip**

**Rule for every pattern chapter:** read the opening summary, *How It Works* and *When to Use It*. **Skip the *Example* sections.** They use Java 1.4 / C# 1.0 with hand-written JDBC, and the text explains the ideas better than the code does.

#### Part 1: The Narratives
| Chapter | Status | Why / modern equivalent |
|---------|--------|-------------------------|
| Introduction | ✅ | Vocabulary and "kinds of enterprise applications". Skim the performance section |
| Ch. 1 Layering | ✅ | N-Tier / layered architecture |
| Ch. 2 Organizing Domain Logic | ✅ | Transaction Script vs. Domain Model vs. Table Module: **the key trade-off chapter** |
| Ch. 3 Mapping to Relational Databases | ✅ | Read *Architectural Patterns*, *The Behavioral Problem*, *Reading in Data*, *Structural Mapping Patterns*. Skim *Building the Mapping*, *Using Metadata*, *Database Connections* |
| Ch. 4 Web Presentation | ⏭ | Frontend: out of scope |
| Ch. 5 Concurrency | ✅ | Optimistic vs. pessimistic locking, isolation, transactions (`@Version`, `SELECT FOR UPDATE`). Skim *Application Server Concurrency* |
| Ch. 6 Session State | ⏭ | Modern default: stateless services + tokens (Phase 5) |
| Ch. 7 Distribution Strategies | ✅ | "The First Law of Distributed Object Design"; coarse- vs. fine-grained interfaces: **essential for microservices** |
| Ch. 8 Putting It All Together | 🔎 | Read the domain-layer and data-source guidance; skip the platform sections |

#### Part 2: The Patterns
| Chapter / Pattern | Status | Modern Java equivalent |
|-------------------|--------|------------------------|
| **Ch. 9 Domain Logic** | | |
| Transaction Script | ✅ | "Spring service with procedural logic" |
| Domain Model | ✅ | Rich JPA entities / DDD aggregates |
| Table Module | ⏭ | Ch. 2 covers the contrast |
| Service Layer | ✅ | `@Service` application layer, transaction boundaries |
| **Ch. 10 Data Source Architectural** | | |
| Table Data Gateway, Row Data Gateway | 🔎 | `JdbcTemplate` DAOs, jOOQ |
| Active Record | ✅ | Rails/Panache style; compare with Data Mapper |
| Data Mapper | ✅ | JPA/Hibernate, MapStruct at boundaries |
| **Ch. 11 O-R Behavioral** | | |
| Unit of Work | ✅ | Hibernate `Session` / JPA `EntityManager`, dirty checking, flush |
| Identity Map | 🔎 | Persistence context / first-level cache |
| Lazy Load | ✅ | `FetchType.LAZY`, proxies, N+1, `LazyInitializationException`, Open-Session-in-View |
| **Ch. 12 O-R Structural** | | |
| Identity Field | 🔎 | `@Id`; sequence vs. UUID (v7) |
| Foreign Key Mapping, Association Table Mapping | ⏭ | `@ManyToOne` / `@ManyToMany` handle it |
| Dependent Mapping | 🔎 | `@OneToMany(cascade, orphanRemoval)`, related to aggregates |
| Embedded Value | 🔎 | `@Embeddable` |
| Serialized LOB | ⏭ | JSON columns |
| Single / Class / Concrete Table Inheritance, Inheritance Mappers | 🔎 | `@Inheritance` strategies: know the trade-offs |
| **Ch. 13 O-R Metadata Mapping** | | |
| Metadata Mapping | ⏭ | JPA annotations are this pattern |
| Query Object | 🔎 | Criteria API, Spring Data `Specification`, Querydsl |
| Repository | ✅ | Spring Data repositories vs. DDD repositories (the difference matters) |
| **Ch. 14 Web Presentation** | | |
| Model View Controller | 🔎 *(optional)* | Spring MVC. Read together with the optional "GUI Architectures" article below |
| Page/Front Controller, Template/Transform/Two Step View, Application Controller | ⏭ | Frontend: out of scope |
| **Ch. 15 Distribution** | | |
| Remote Facade | ✅ | REST controllers / API facades, coarse-grained APIs |
| Data Transfer Object | ✅ | DTOs, records, why entities shouldn't leak into APIs |
| **Ch. 16 Offline Concurrency** | | |
| Optimistic Offline Lock | ✅ | `@Version`, ETag / `If-Match` in REST |
| Pessimistic Offline Lock | 🔎 | `@Lock(PESSIMISTIC_WRITE)`, distributed locks; the concept of a lock that spans requests |
| Coarse-Grained Lock | ✅ | Locking the aggregate root |
| Implicit Lock | 🔎 | Framework-managed locking |
| **Ch. 17 Session State** | ⏭ | Stateless JWT / Redis-backed session (Phase 5) |
| **Ch. 18 Base Patterns** | | |
| Gateway | ✅ | Clients for external systems |
| Mapper | 🔎 | MapStruct |
| Layer Supertype | ⏭ | `BaseEntity` / `@MappedSuperclass` |
| Separated Interface | ✅ | Interface in the domain, implementation in infrastructure: **the basis of DIP and Hexagonal** |
| Registry | ⏭ | Replaced by DI containers |
| Value Object | 🔎 | Records, `@Embeddable` (more in Phase 4) |
| Money | ✅ | `BigDecimal` pitfalls, JSR 354 (Moneta) |
| Special Case | 🔎 | Null Object, `Optional` |
| Plugin | ⏭ | Spring profiles / conditional beans |
| Service Stub | 🔎 | Test doubles, WireMock |
| Record Set | ⏭ | Outdated |

#### Supplementary reading
- M. Fowler, "Presentation Domain Data Layering" and "AnemicDomainModel" (bliki)
- *(Optional)* M. Fowler, "GUI Architectures" (martinfowler.com/eaaDev/uiArchs.html): MVC, MVP (Supervising Controller, Passive View) and Presentation Model/MVVM, in about an hour. Read it if your assessment covers the "MVC/MVP flavors" part of R2.
- *(Optional)* V. Mihalcea, *High-Performance Java Persistence*: chapters on the persistence context, fetching and concurrency control

### Week 4: Layering, domain logic, O-R mapping
**Read:** Introduction, Ch. 1, 2, 3, then the Ch. 9, 10 and 11 patterns and Repository. As you read, compare each idea with what JPA/Hibernate does.

### Week 5: Concurrency, distribution, base patterns
**Read:** Ch. 5, 7, 8 (skim), the selected Ch. 12 patterns, Ch. 15, 16, 18.

### Deliverables
- [ ] **ShopFlow v1:** a layered Spring Boot app, Controller → Service Layer → Domain Model → Repository (Data Mapper via JPA), with:
  - rich domain objects (behaviour inside `Order`, not in `OrderService`)
  - value objects (`Money`, `Address`) as `@Embeddable`/records
  - optimistic locking on `Order`
  - DTOs + a mapper at the API boundary
- [ ] **Unit of Work lab:** tests that show dirty checking, flush timing, Identity Map behaviour, lazy loading, the N+1 problem, and how to fix it (fetch join, entity graph)
- [ ] **Trade-off essay (2–3 pages):** *"Which domain logic and data source patterns would you choose for (a) a CRUD back-office admin app, (b) a complex insurance/pricing engine, (c) a high-throughput reporting service? Why?"*
- [ ] *(Optional)* **Experiment:** implement "place order" three ways (Transaction Script, Active Record-like, Domain Model) and compare complexity and testability
- [ ] Any **change request** your mentor gives on ShopFlow v1

### Prepare for the session
- When is Transaction Script the *better* choice than Domain Model?
- What does Hibernate do on `commit`? Explain it in terms of Unit of Work and Identity Map.
- Why is Open-Session-in-View considered an anti-pattern?
- Why do N-Tier layers turn into "lasagna", and how do you stop that happening?

---

## 7. Phase 4: Deeper Architecture Patterns (Weeks 6–8)

You move from "layers" to **architectures that protect the domain**. This phase produces your **Experience point #1** (R3).

### Week 6: Hexagonal / Clean / Onion + Modular Monolith

**Topics**
- Ports & Adapters (Hexagonal), Onion, Clean Architecture: what they share (dependency rule, DIP at the architecture level) and how they differ
- Driving vs. driven adapters; use cases / application services
- Mapping strategies between layers (no mapping, two-way, full, one-way) and their costs
- **Modular monolith**: module boundaries, package-by-feature vs. package-by-layer, enforcing boundaries
- Architecture enforcement: **ArchUnit**, **Spring Modulith**, jMolecules

**Reading**
- *Get Your Hands Dirty on Clean Architecture*, 2nd ed. (T. Hombergs): the whole book (short, Java/Spring-based)
- *Clean Architecture* (R. Martin): Part V (Architecture), Ch. 15–28
- A. Cockburn, "Hexagonal Architecture" (original article)
- Spring Modulith reference documentation

### Week 7: Domain-Driven Design

**Topics**
- **Strategic DDD:** ubiquitous language, subdomains (core/supporting/generic), bounded contexts, context mapping (Shared Kernel, Customer/Supplier, Conformist, **Anti-Corruption Layer**, Open Host Service, Published Language)
- **Tactical DDD:** Entities, Value Objects, **Aggregates** (consistency boundaries, reference by ID, small aggregates), Domain Services, Domain Events, Repositories, Factories
- Event Storming as a discovery technique

**Reading**
- *Learning Domain-Driven Design* (V. Khononov): Parts I–II
- *Implementing Domain-Driven Design* (V. Vernon): Ch. 2, 3, 10
- V. Vernon, "Effective Aggregate Design" (3-part essay, free PDF)
- *Domain-Driven Design* (E. Evans): reference only, Part II and Part IV

### Week 8: CQRS, Event Sourcing, Event-Driven Architecture

**Topics**
- CQRS: from simple (separate read models in the same DB) to full (separate stores)
- Event Sourcing: event store, projections, snapshots, schema evolution, **when NOT to use it**
- Domain events vs. integration events
- Architecture styles and their trade-offs: layered, modular monolith, microkernel, event-driven, space-based, microservices
- Architecture characteristics ("-ilities") and fitness functions
- Documenting architecture: **C4 model**, **ADRs**

**Reading**
- *Fundamentals of Software Architecture*, 2nd ed. (M. Richards, N. Ford): Part I and the architecture-style chapters
- M. Fowler, "CQRS" and "Event Sourcing"
- G. Young, "CQRS Documents" (free PDF): CQRS and Event Sourcing chapters
- c4model.com; adr.github.io
- *System Design Interview Vol. 2* (A. Xu, S. Lam): Ch. 6 "Ad Click Event Aggregation" and Ch. 10 "Real-time Gaming Leaderboard" (read-model case studies)

### Deliverables (Experience point #1)
- [ ] **Event Storming** session with your mentor for ShopFlow → bounded contexts and a context map
- [ ] **ShopFlow v2:** refactor v1 into a **modular monolith** with a hexagonal structure inside each module:
  - modules = bounded contexts (Orders, Inventory, Payments, Catalog)
  - modules talk only through public APIs or domain events (Spring Modulith events)
  - aggregates enforce their own invariants
  - **ArchUnit tests** fail the build when a dependency rule is broken
  - one read model with a simple CQRS approach (e.g., "order history")
- [ ] **Change request:** "support multiple warehouses with stock reservation". Show that only the Inventory module changes.
- [ ] **Design document:** C4 context + container + component diagrams, the context map, and at least **3 ADRs** (e.g., "modular monolith vs. microservices", "Domain Model vs. Transaction Script for Catalog", "mapping strategy between layers")
- [ ] **Architecture review:** defend your decisions to your mentor (and ideally another senior or architect)

### Prepare for the session
- Is Hexagonal worth it for a CRUD service? What is the cost?
- How do you choose aggregate boundaries? What happens if an aggregate is too big or too small?
- Where does a transaction boundary belong in a modular monolith?
- Event Sourcing: what are 3 good and 3 bad reasons to use it?

---

## 8. Phase 5: Distributed Systems & Microservices Patterns (Weeks 9–14)

The main references are **microservices.io** and *Microservices Patterns* (C. Richardson). Throughout this phase, keep asking **"do we actually need microservices here?"** The trade-offs and the cost of distribution come first.

### Week 9: Why (not) microservices, and decomposition

**Topics**
- Benefits vs. costs; the fallacies of distributed computing; the distributed monolith anti-pattern
- Revisit PoEAA Ch. 7 (First Law of Distributed Object Design)
- **Decomposition patterns:** by business capability, by subdomain (DDD), self-contained service, team topologies / Conway's Law
- Service granularity: disintegrators vs. integrators
- **Migration:** Strangler Fig, Branch by Abstraction, Anti-Corruption Layer, Change Data Capture

**Reading**
- *Building Microservices*, 2nd ed. (S. Newman): Ch. 1–3
- *Monolith to Microservices* (S. Newman): Ch. 1–3
- *Software Architecture: The Hard Parts* (N. Ford et al.): Ch. 7 (Service Granularity)
- microservices.io: "Decomposition" pattern group

### Week 10: Distributed systems fundamentals

Every microservices problem is a distributed-systems problem first. This week gives you the vocabulary for the rest of the phase.

**Topics**
- **Scalability:** vertical vs. horizontal scaling, stateless services, load balancing (L4 vs. L7), autoscaling, back-of-the-envelope estimates
- **Data scaling:** read replicas; partitioning/sharding (range, hash, consistent hashing), rebalancing, hot spots
- **Replication:** single-leader, multi-leader, leaderless (quorums); replication lag
- **High availability:** redundancy, single points of failure, failover (active-passive vs. active-active), multi-AZ vs. multi-region, "nines", SLI/SLO/SLA, RTO/RPO
- **CAP theorem and PACELC:** what the "C" in CAP really means, why "CP vs. AP" labels mislead, latency vs. consistency when there is no partition
- **Consistency models:** linearizable, sequential, causal, read-your-writes, monotonic reads, eventual; consensus (Raft) at the concept level
- **Caching:** cache levels (HTTP/client, CDN, gateway, in-process, Redis); cache-aside, read-through, write-through, write-behind; TTL and invalidation; stampede, hot keys, penetration; cache–DB consistency; HTTP caching (`Cache-Control`, `ETag`); Spring Cache

**Reading** (short on time? Start with the *System Design Interview Vol. 1* chapters and the Kleppmann post)
- *System Design Interview Vol. 1* (A. Xu): Ch. 1, 2, 5, 6
- *Designing Data-Intensive Applications* (M. Kleppmann): Ch. 5 (Replication), Ch. 6 (Partitioning), Ch. 9 (linearizability, ordering and consensus sections)
- M. Kleppmann, "Please stop calling databases CP or AP" (blog post)
- D. Abadi, "Consistency Tradeoffs in Modern Distributed Database System Design" (the PACELC paper, short)
- Google SRE book (free at sre.google): Ch. 3 "Embracing Risk" and Ch. 4 "Service Level Objectives"
- Spring Framework Cache Abstraction and Spring Data Redis documentation

### Week 11: Communication & integration

**Topics**
- Sync vs. async; request/response vs. events vs. commands
- **API Gateway**, **Backend for Frontend (BFF)**, API composition
- API design and versioning (REST, gRPC), consumer-driven contracts
- Messaging: point-to-point vs. pub/sub, message ordering, **idempotent consumer**, at-least-once delivery, dead-letter queues
- Service discovery, client-side vs. server-side load balancing

**Reading**
- *Microservices Patterns* (C. Richardson): Ch. 3, Ch. 8
- *Enterprise Integration Patterns* (G. Hohpe, B. Woolf): intro plus Message Channel, Pub-Sub, Message Router, Content-Based Router, Dead Letter Channel, Idempotent Receiver, Correlation Identifier
- *System Design Interview Vol. 2*: Ch. 4 "Distributed Message Queue"

### Week 12: Data management (the hardest part)

**Topics**
- **Database per service**, and why a shared database is an integration anti-pattern
- **Splitting an existing database:** data ownership, code-first or data-first, shared tables and cross-service foreign keys, keeping data in sync during migration
- **Distributed transactions:** Two-Phase Commit / XA, TC/C (Try-Confirm/Cancel), and why microservices usually choose sagas instead
- **Saga** (choreography vs. orchestration), compensating transactions, semantic locks, countermeasures for lack of isolation
- **Transactional Outbox**, Polling Publisher, Transaction Log Tailing (Debezium CDC)
- **CQRS** across services, API Composition vs. CQRS views
- Event Sourcing in microservices
- Eventual consistency, and how to explain it to the business

**Reading**
- *Microservices Patterns*: Ch. 4, 5, 6, 7
- *Software Architecture: The Hard Parts*: Ch. 6 (Pulling Apart Operational Data), Ch. 12 (Transactional Sagas)
- *Monolith to Microservices* (S. Newman): Ch. 4 (Decomposing the Database)
- *Designing Data-Intensive Applications*: Ch. 7 (Transactions), the "Distributed Transactions and Consensus" section of Ch. 9 (2PC), Ch. 11 (selected)
- *System Design Interview Vol. 2*: Ch. 11 "Payment System" and Ch. 12 "Digital Wallet"

### Week 13: Resilience, observability, security

**Topics**
- **Resilience:** Timeout, Retry with backoff + jitter, **Circuit Breaker**, **Bulkhead**, Rate Limiter, fallback; cascading failures
- **Observability:** distributed tracing (OpenTelemetry), correlation IDs, log aggregation, metrics, health checks
- **Security:** Access Token pattern, edge vs. service-level auth, service-to-service auth (mTLS), OAuth2/OIDC with Spring Security, stateless sessions
- **Zero Trust:** never trust the network, authenticate and authorize every call, least privilege, mTLS everywhere
- **Secrets management:** Vault and cloud secret managers, Kubernetes Secrets and their limits, rotation, keeping secrets out of git, images and plain config
- Externalized configuration

**Reading**
- *Release It!*, 2nd ed. (M. Nygard): Part I (Create Stability)
- *Microservices Patterns*: Ch. 11
- Resilience4j documentation; Micrometer Tracing / OpenTelemetry docs
- *System Design Interview Vol. 2*: Ch. 5 "Metrics Monitoring and Alerting System"
- NIST SP 800-207 "Zero Trust Architecture" (skim sections 2–3)
- Spring Cloud Vault documentation

### Week 14: Testing & deployment

**Topics**
- Testing strategy: test pyramid vs. honeycomb, **consumer-driven contract testing** (Spring Cloud Contract / Pact), component tests with Testcontainers
- **12-Factor App:** the twelve factors and how a Spring Boot service meets each one
- Deployment patterns: service per container, blue-green, canary, feature flags, **Sidecar** and Ambassador
- Service mesh (built on the Sidecar pattern) vs. library-based approaches (overview)
- Kubernetes basics for architects (overview only)

**Reading**
- *Microservices Patterns*: Ch. 9–10, Ch. 12
- *Building Microservices*, 2nd ed.: Ch. 8, Ch. 9
- 12factor.net (read all twelve factors)
- microservices.io: "Sidecar" and "Service Mesh" patterns

### Deliverables (build them across Weeks 9–14)
- [ ] **ShopFlow v3:** extract **Orders**, **Payments** and **Inventory** into separate services (Docker Compose is enough):
  - [ ] each service has its own database
  - [ ] **order placement saga**: orchestration or choreography, your choice, justified in an ADR
  - [ ] **transactional outbox** (hand-written, or Debezium)
  - [ ] **idempotent consumers** and a dead-letter topic
  - [ ] **Resilience4j** circuit breaker + retry + timeout on synchronous calls
  - [ ] an **API Gateway** (Spring Cloud Gateway) with a BFF-style aggregated endpoint
  - [ ] distributed tracing across all services
  - [ ] at least one **consumer-driven contract test**
  - [ ] **12-Factor** compliant, with secrets from a secrets manager (e.g., Vault via Spring Cloud Vault), not config files
- [ ] **Scalability and caching lab:** two instances of a service behind a load balancer; Redis cache-aside for Catalog product reads with invalidation on update; reproduce a stale read and fix it. Half-page note on the CAP/PACELC trade-off of each ShopFlow data store (PostgreSQL, Kafka, Redis).
- [ ] **Failure lab:** kill Payments during a saga, add latency to Inventory, send duplicate messages. Document what happened and how you fixed it.
- [ ] **Change request:** "add partial refunds" or "add a Loyalty service". Assess the impact across services and contracts.
- [ ] **Written comparison:** *"Modular monolith (v2) vs. microservices (v3) for ShopFlow: which would you recommend for a team of 6 and for a team of 40, and why?"*

### Prepare for the sessions
- How do you avoid a distributed monolith?
- Which ShopFlow operations need strong consistency, and which can be eventually consistent? What do CAP and PACELC say about each?
- Cache-aside with a TTL: how stale can a read get, and how do you explain that to the business?
- Why is 2PC rarely used between microservices, and what do you give up by using a saga instead?
- Saga orchestration vs. choreography: what are the trade-offs for observability, coupling and complexity?
- How do you guarantee "exactly once" processing?
- What should a gateway do and not do?
- How do you handle a breaking API change across services?

---

## 9. Phase 6: Capstone, Architecture Design & Defence (Weeks 15–16)

This produces your **Experience point #2** (R3). If possible, use a **real company project**: a new feature, a new service or a re-architecture proposal. Otherwise pick a realistic case such as "ride-hailing platform", "hotel booking" or "B2B invoicing system", or a chapter from the case study bank below.

### Case study bank: *System Design Interview Vol. 2*
**Write your own proposal first, then read the chapter.** Afterwards, write a short "what I missed / what I'd do differently" note.
| Chapter | Why it makes a good capstone |
|---------|------------------------------|
| Ch. 7 Hotel Reservation System | Overbooking prevention, concurrency control, idempotent reservations; closest to ShopFlow |
| Ch. 13 Stock Exchange | Low latency, strict ordering, event sourcing, determinism; a demanding stretch option |
| Ch. 9 S3-like Object Storage | Durability, replication, erasure coding, metadata vs. data separation |
| Ch. 1 Proximity Service / Ch. 3 Google Maps | Geospatial indexing, read-heavy scaling, caching |
| Ch. 2 Nearby Friends / Ch. 8 Distributed Email Service | Optional: WebSocket fan-out, pub/sub, large-scale storage and search |

### Deliverables
- [ ] **Architecture proposal document:**
  - [ ] business context, goals, constraints, key quality attributes (prioritised)
  - [ ] C4 diagrams (context, container, key components)
  - [ ] domain model / bounded contexts / context map
  - [ ] chosen architectural style(s) and enterprise patterns, **with rejected alternatives**
  - [ ] data management and consistency strategy
  - [ ] integration and resilience strategy
  - [ ] 5+ ADRs
  - [ ] risks, a migration/rollout plan, fitness functions
- [ ] **Refactoring plan (R4):** one legacy area in the company codebase, with target design, pattern combination, step-by-step safe migration and tests
- [ ] **Architecture defence:** a 45–60 min session with your mentor and 1–2 senior engineers or architects. Expect live "what if" change requests (R5), such as "traffic grows 10x" or "we must add a new region with data residency rules".

---

## 10. Whiteboard Problems

Every 1–2 weeks, your mentor will give you a design problem in the session: a parking lot, a rate limiter, a notification system, a URL shortener, a library system, an e-wallet, a booking system with overbooking prevention, or a pricing/promotion engine. More may come from the *System Design Interview Vol. 2* chapters.

- **Phases 1–2:** focus on class and module design (responsibilities, patterns, boundaries).
- **Phases 4–6:** focus on system design, using the 4-step framework:
  1. **Understand the problem and scope:** ask questions, agree on functional and non-functional requirements, estimate scale
  2. **High-level design:** main components, data flow, APIs, data stores
  3. **Deep dive:** the one or two hardest parts (consistency, scaling, failure handling)
  4. **Wrap up:** bottlenecks, trade-offs, what you'd do with more time

---

## 11. Self-Assessment Checklist (Senior Readiness)

By the end, you should be able to do each of these **without guidance**. Revisit the list at the end of every phase.

**OOD & Patterns (R1)**
- [ ] Use GRASP and coupling/connascence to critique and improve a design
- [ ] Choose a fitting pattern (or none) for a design problem and explain its consequences
- [ ] Recognise patterns in Spring/JDK and explain how they work (proxies, templates, events)
- [ ] Design package/module structure without cycles and with clear public APIs

**Enterprise Architecture (R2)**
- [ ] Compare Transaction Script / Table Module / Domain Model for a given business context
- [ ] Compare Active Record / Data Mapper; explain Unit of Work, Identity Map and Lazy Load via JPA internals
- [ ] Explain N-Tier/layered architecture, its variants and its failure modes
- [ ] *(Optional)* Explain MVC vs. MVP (Passive View, Supervising Controller) vs. MVVM and where each fits
- [ ] Choose a concurrency strategy (optimistic/pessimistic/coarse-grained lock) and justify it

**Architecture design (R3)**
- [ ] Experience point #1: ShopFlow modular monolith design + ADRs, reviewed
- [ ] Experience point #2: Capstone architecture proposal, defended

**Refactoring & change (R4, R5)**
- [ ] Refactor a legacy module into a clean pattern combination, safely and in small steps
- [ ] Take on change requests without breaking architectural boundaries (proven by ArchUnit/contract tests)

**Beyond the requirements**
- [ ] Apply Hexagonal/Clean architecture and DDD (strategic + tactical) appropriately
- [ ] Design a microservices solution with correct data management (saga, outbox, CQRS) and resilience patterns
- [ ] Explain CAP/PACELC and consistency models, and choose a consistency level for each data store in a design
- [ ] Design for scalability and high availability: replication, partitioning, failover, and a caching strategy with invalidation
- [ ] Explain why 2PC is avoided between microservices and what replaces it
- [ ] Apply 12-Factor, Zero Trust and secrets management to a service design
- [ ] Argue credibly *against* microservices when they're not justified

---

## 12. Reading List

**Core (selected chapters as listed in each week)**
1. M. Fowler: *Patterns of Enterprise Application Architecture* (see the reading map in §6)
2. R. Martin: *Clean Architecture* (Parts IV–V)
3. M. Fowler: *Refactoring*, 2nd ed.
4. J. Kerievsky: *Refactoring to Patterns*
5. T. Hombergs: *Get Your Hands Dirty on Clean Architecture*, 2nd ed.
6. V. Khononov: *Learning Domain-Driven Design*
7. C. Richardson: *Microservices Patterns* (+ microservices.io)
8. S. Newman: *Building Microservices*, 2nd ed.

**Recommended**
- C. Larman: *Applying UML and Patterns* (GRASP chapters)
- J. Bloch: *Effective Java*, 3rd ed. (reference)
- M. Feathers: *Working Effectively with Legacy Code*
- V. Vernon: *Implementing Domain-Driven Design* / "Effective Aggregate Design"
- M. Richards, N. Ford: *Fundamentals of Software Architecture*, 2nd ed.
- N. Ford et al.: *Software Architecture: The Hard Parts*
- M. Nygard: *Release It!*, 2nd ed.
- S. Newman: *Monolith to Microservices*
- G. Hohpe, B. Woolf: *Enterprise Integration Patterns* (reference)
- M. Kleppmann: *Designing Data-Intensive Applications* (Ch. 5, 6, 7, 9, 11, selected)
- A. Xu: *System Design Interview: An Insider's Guide, Vol. 1* (Ch. 1, 2, 5, 6)
- A. Xu, S. Lam: *System Design Interview: An Insider's Guide, Vol. 2*. Use it as a case study library: read each chapter in the week it's listed, after that week's main reading.
- Google: *Site Reliability Engineering* (free online; Ch. 3–4)
- V. Mihalcea: *High-Performance Java Persistence* (selected chapters)

**Online**
- martinfowler.com: "GUI Architectures" (optional), "AnemicDomainModel", "TellDontAsk", "PresentationDomainDataLayering", "CQRS", "Event Sourcing", "StranglerFigApplication", "Microservices" (with J. Lewis), "MonolithFirst"
- connascence.io
- microservices.io: pattern language
- 12factor.net
- M. Kleppmann, "Please stop calling databases CP or AP"; D. Abadi, PACELC paper
- NIST SP 800-207 "Zero Trust Architecture"
- refactoring.guru: refactoring catalogue
- github.com/emilybache: refactoring katas
- c4model.com, adr.github.io
- ArchUnit, Spring Modulith, Resilience4j, Spring Cloud Contract documentation

---

*If you're already strong in an area, agree with your mentor to shorten it and spend the time on Phases 4–5. Keep the deliverables, though: they are your evidence for the senior assessment.*
