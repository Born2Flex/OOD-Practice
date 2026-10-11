# Week 01: Design principles beyond SOLID + ShopFlow kickoff

## Where I disagree with the reading, and why
- **Instability metrics (Clean Architecture).** I = Ce / (Ca + Ce) counts only compile-time dependencies. In a Spring codebase, much of the real coupling is runtime wiring (injected lists, `@Order`), so a package can look perfectly stable and still be fragile.
- **Anemic Domain Model (Fowler).** The diagnosis is right, but in a large JPA codebase moving behaviour into an entity used everywhere is costly and risky. Sometimes the pragmatic choice is to keep the services and move logic in gradually.
- **Tell-Don't-Ask.** A pure "tell" isn't always better. `canBePaid()` is a query, but it keeps the rule inside the order and is also useful for a UI. Fowler himself says query methods are fine when used carefully.

## ShopFlow design notes

### Packaging: traditional layers with sub-packages
- **First version:** by feature inside the model layer (`model.order`, `model.pricing`, `model.payment`, ...).
- **Now:** regrouped into classic layers to make the structure easier to follow: `model` (with `order`, `customer`, `money`, `payment`, `state` and `discount` sub-packages), `service`, `exception`, `integration` (payment gateway + adapters + factory), and `external` (pretend SDKs).
- **Trade-off:** a feature like payments is now spread across `model`, `exception`, `integration` and `service`, which goes against the Common Closure Principle. The `PaymentGateway` interface moved out of `model`, so `service` depends on `integration` (classic N-Tier) instead of on an interface the domain owns (Separated Interface).

### Accepted package cycle (ADP trade-off)

```mermaid
graph LR
    model["model.order<br/>Order, OrderStatus"]
    state["model.state<br/>OrderState + 5 states"]
    exception["exception<br/>IllegalOrderTransitionException"]

    model -->|"Order creates NewState"| state
    state -->|"states return OrderStatus"| model
    state -->|"default transitions throw"| exception
```

- **Cycle broken across layers:** `IllegalOrderTransitionException` used to take an `OrderStatus`, which made a cycle `model.order → model.state → exception → model.order` across layers. It now takes a plain `String` status, so `exception` depends on nothing in `model`.
- **Remaining cycle:** `model.order ⇄ model.state`. Accepted, because both packages belong to the order lifecycle inside the model layer.
- **Cost:** the state classes have to be `public`, so code outside the package can create states. `model.order` and `model.state` can't be understood or changed independently.
- **If it starts to hurt:** move `OrderStatus` into `model.state`, so all dependencies point one way.

---

## Prepare for the session
- **Which GRASP principle explains why a `Controller` shouldn't contain business logic?**
  - **High Cohesion**, together with **Information Expert**. A controller's job is to receive a use case and coordinate it, like `PaymentService.pay()`. If it also holds business rules, it does two unrelated jobs, and the rules end up away from the objects that own the data.
- **Which kinds of connascence are hardest to spot, and how do they show up across module or service boundaries?**
  - **Dynamic connascence:** execution order, timing, values and identity. The compiler and imports don't show it. In `cm_sf_report`, the status rules depend on `@Order` numbers in separate files. Across services it shows up as message ordering, retry and timeout timing, and a shared meaning of status values in events.
- **Which package in my module breaks the Stable Dependencies or Stable Abstractions principle, and what would fixing it cost?**
  - The root `status` package (I = 0.64) depends on `caller` (I = 0.86), which breaks SDP. It happens only because the notification service lives in root. Fixing it means moving the notification service and `caller` into their own package: a few class moves and import changes, low risk.
- **In the ShopFlow kickoff, where did I choose *not* to use a pattern, and why?**
  - `PaymentGatewayFactory` uses a plain `switch` instead of a registry. With two providers, the `switch` is simpler and shows the whole routing rule in one place (YAGNI). I'll switch to a registry when a third provider arrives.

## Reading log
- [x] *Clean Architecture*: Part IV (Component Principles)
- [x] *Applying UML and Patterns*: Ch. 17, 18, 25 (GRASP)
- [x] connascence.io: full taxonomy
- [x] M. Fowler: "TellDontAsk"
- [x] M. Fowler: "AnemicDomainModel"

## Deliverables
- [x] Design critique: [docs/essays/01-design-critique.md](../docs/essays/01-design-critique.md)
- [x] Package dependency diagram: [docs/diagrams/01-package-dependencies.md](../docs/diagrams/01-package-dependencies.md)
- [x] ShopFlow domain kickoff: [shopflow/README.md](../shopflow/README.md)
