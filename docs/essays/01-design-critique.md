# Design Critique: `service.contract.status`

## Context
- **System:** `cm_sf_report`, contract management backend (Spring Boot, JPA).
- **Module:** `service.contract.status`, 35 classes, about 1,100 lines. Decides a contract's status (Draft / Open), verifies status transitions, and sends status-change reminders.
- **Structure:** a facade, two managers (project / non-project contracts), and small rule and verifier classes wired by Spring.

## 1. Anemic `Contract` (GRASP Information Expert, Tell-Don't-Ask)
`Contract` holds the status data, but the logic lives in nine other classes that read and set it:

```java
contract.getSmsStatus() == ContractStatus.Open && !contract.hasStatusOverrideComment() ...
contract.setStatusChangeToDraftDate(null);
contract.setSmsStatus(ContractStatus.Draft);
```

Expressions like `contract.getSapOrderNumber().isExpired()` also break the Law of Demeter.

**Impact:** "when does a contract become Draft?" has no single answer in the code. Every rule re-checks `status == Open`, and any class can put a contract into any status.

## 2. Rule order hidden in `@Order` (connascence of execution)
The SAP rules run in the order of their `@Order(1)`, `@Order(2)`, `@Order(4)` annotations, spread across three files. `FinInfoRule` (1) sets the draft date that `StatusTransitionRule` (2) reads.

**Impact:** dynamic connascence is the strongest kind, and here it also has the worst locality (separate files). Renumbering one annotation silently changes behaviour, and no compiler or import shows the dependency.

## 3. `service` ⇄ `status.manager` package cycle (ADP)
`ContractService` calls `ContractStatusManagerFacade`, and the managers call `ContractServicePackageChecker` and `CurrentUserPermissionChecker` from `service`. See the [package dependency diagram](../diagrams/01-package-dependencies.md).

**Impact:** neither package can be understood, tested or extracted without the other, which blocks a modular split of the codebase.

## What works well
The rule hierarchy: one stable `StatusManagementRule` interface with small, unstable rule classes. A new rule is a new class (OCP), and the package metrics match the Stable Abstractions Principle.

## Recommended fixes, ranked by value
| # | Fix | Solves | Effort | Risk |
|---|-----|--------|--------|------|
| 1 | Declare the SAP rule order in one place: one ordered list built in a config class, or one `SapStatusPolicy` that calls the rules in sequence | Finding 2 | S | Low |
| 2 | Move status behaviour into `Contract`: `isOpen()`, `revertToDraft()`, `clearDraftDeadline()`. Migrate the rules one by one | Finding 1 | M | Medium (JPA entity, many callers) |
| 3 | Let the managers depend on small interfaces defined in `status.manager` and implemented in `service` (DIP) | Finding 3 | S | Low |

## What I would leave alone
- **The rule / verifier structure.** It is the part that already follows OCP and the Stable Abstractions Principle.
- **A full State pattern for `Contract` status.** Tempting after ShopFlow, but it means a big-bang rewrite of a JPA entity used across the codebase. Fix 2 gets most of the benefit incrementally.
