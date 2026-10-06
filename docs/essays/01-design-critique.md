# Design Critique: <module / service name>

> 1–2 pages. Use GRASP, coupling/connascence and component principles as the lens, not a SOLID checklist.

## Context
- **System / module:**
- **What it does:**
- **Size and age** (classes, packages, LOC, team):
- **Why I picked it:**

## Responsibility assignment (GRASP)
| Finding | GRASP principle violated | Where (class/method) | Impact |
|---------|--------------------------|----------------------|--------|
| | | | |

## Coupling and connascence
| Finding | Connascence kind (name/type/meaning/position/algorithm/execution/timing/values/identity) | Strength · locality · degree | Impact |
|---------|-----|-----|--------|
| | | | |

## Encapsulation
- Tell-Don't-Ask / Law of Demeter violations:
- Anemic model symptoms:
- Mutable state that should be a value object:

## Component principles
- Cycles (ADP): see [package dependency diagram](../diagrams/01-package-dependencies.md)
- SDP / SAP violations:
- REP / CCP / CRP observations:

## Recommended fixes, ranked by value
| # | Fix | Problem it solves | Effort (S/M/L) | Risk | Value |
|---|-----|-------------------|----------------|------|-------|
| 1 | | | | | |
| 2 | | | | | |
| 3 | | | | | |

## What I would deliberately leave alone, and why
