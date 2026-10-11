# Week 02: Code smells, safe refactoring, refactoring to patterns

## Where I disagree with the reading, and why
- **"Only automated refactorings until the code is covered" (Trip Service rule).** I got a safety net first with Mockito's `mockStatic`, without touching the code. The tests had to be rewritten once I injected the dependencies, but they kept me safe while I did it. A temporary safety net that will be thrown away is still better than refactoring blind.
- **Replace Conditional Logic with Strategy isn't free.** In Gilded Rose, strategies chosen by `supports()` introduced a new coupling: `RegularStrategy` must stay last in the list (connascence of position). It's acceptable because the order lives in one place, but a `Map` by item name or a `switch` would avoid it.
- **"A refactoring never changes behaviour" is stricter than practice.** In Trip Service I changed the empty result to an immutable `List.of()` while refactoring. Small deliberate changes are fine if they're pinned by a test and called out, not hidden inside a "refactoring" commit.

---

## Prepare for the session (Phase 2: Weeks 2–3)
- **How do you decide that a refactoring is worth doing in a sprint?**
  - When it's on the path of a change I'm about to make: "make the change easy, then make the easy change". In Gilded Rose, refactoring first turned the Conjured feature into one new class. Code that rarely changes isn't worth refactoring, however ugly it is.
- **How do you refactor safely with no tests?**
  - Build a safety net first, then change in small, green steps. For a whole program: a golden master that compares the full output (the Gilded Rose approval test). For hard-wired dependencies: create seams with automated IDE refactorings, or use static mocking as a temporary net (Trip Service). Then add characterization tests at the boundaries, which pin what the code *does*, not what it should do.
- **When is the right answer "remove the pattern"?**
  - When the variation it was built for doesn't exist. Signs: an unused extension point (my `GildedRose` constructor that takes a list of strategies is Speculative Generality), a single implementation behind an interface, or a plain `switch` / `Map` that says the same thing more clearly.

## Reading log
- [ ] *Refactoring*, 2nd ed.: Ch. 1–3
- [ ] *Refactoring to Patterns*: Ch. 1–4
- [ ] Replace Conditional Logic with Strategy
- [ ] Replace State-Altering Conditionals with State
- [ ] Move Embellishment to Decorator
- [ ] Replace Constructors with Creation Methods
- [ ] Extract Composite
- [ ] Replace Implicit Tree with Composite
- [ ] Introduce Null Object

## Deliverables
- [x] Gilded Rose kata: `katas/gilded-rose/`
- [x] Trip Service kata: `katas/trip-service/`
