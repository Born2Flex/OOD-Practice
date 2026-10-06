# ShopFlow

An order and fulfillment system that runs through the whole roadmap.

| Phase | Version | Status |
|-------|---------|--------|
| 1–2 | Core domain classes | in progress |
| 3 | v1: layered Spring Boot app | |
| 4 | v2: modular monolith, hexagonal, DDD | |
| 5 | v3: Orders / Payments / Inventory services | |

## Phase 1: domain kickoff

### Pattern choices
One or two sentences per choice. Include the places where a pattern was **not** used.

| Area | Choice | Why | Rejected alternative |
|------|--------|-----|----------------------|
| Pricing and discounts | Strategy (`Discount`) + Composite (`StackedDiscount`) + Specification (`CustomerTypeSpecification`), coordinated by `PricingService` | **Keep `Order` cohesive (GRASP High Cohesion).** `Order` already has lines, a customer reference and a lifecycle; adding discount rules would push it toward a God Object (Large Class smell). **The calculation needs more than the order.** It also needs the `Customer`, which `Order` only references by ID. Evans recommends a *Domain Service* for an operation that isn't the natural responsibility of one entity; Larman calls this *Pure Fabrication*. **Not an anemic model.** The rules live in domain objects in `model.pricing`, and `Order` still computes its own `subtotal()` (Information Expert). `PricingService` only coordinates. | **`Order.total(pricingPolicy)`** (Information Expert): rejected because it lowers `Order`'s cohesion and forces it to know about customers. **All logic in the service** (Transaction Script): rejected because it leads to the Anemic Domain Model that Fowler warns about. |
| Order lifecycle | State pattern | Avoids `if`/`else` or `switch` on the status inside `Order`: each state owns its allowed transitions, which keeps the behaviour maintainable. Invalid transitions throw a business exception (`IllegalOrderTransitionException`). | Enum-based transition table: one central enum that declares the allowed transitions for each status. Workable, but the State pattern is simple enough for now. |
| Notifications | Observer via Spring events | | |
| Payment providers | Adapter + Factory | | |

### Where I chose not to use a pattern
-

## Change requests log
| # | Request | Phase | Files added | Files modified | Notes |
|---|---------|-------|-------------|----------------|-------|
| | | | | | |
