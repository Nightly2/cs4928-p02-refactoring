# Event B Evaluation: Runtime Extensibility Pressure

- **Pre-Event-B Frozen State:** Tag `PRE_EVENT_B` (`3e3ff5a`)
- **Post-Event-B State:** Tag `POST_EVENT_B` (`d0368dd`)
- **Target Implementation:** `java-chat_rollout-03_chosen_strategy/`
- **Verification Logs:** `test_logs/event_b/`

## 1. Pressure Selected & Pre-Execution Prediction
- **Evolution Dimension:** Runtime Extensibility.
- **Concrete Requirement:** Allow rollout percentages and feature flags to change dynamically during application runtime without redeploying services, recreating controllers, or introducing downtime.
- **Pre-Event-B Prediction:** This pressure will test how rollout decisions are supplied and modified, but it will **not** require modifying `ChatController` or the `RolloutPolicy` interface. Because the Week 9 Strategy Pattern decoupled policy evaluation (`isEnabled()`) from chat routing, the design will absorb dynamic runtime changes entirely through a dedicated dynamic strategy implementation (preserving OCP and DIP).

## 2. Alternatives Considered
1. **Alternative 1: No Structural Change to Controller + External Dynamic Strategy (Adopted)**
   - **Mechanism:** Retain `ChatController` and `@FunctionalInterface RolloutPolicy` completely untouched. Supply runtime changes by injecting `DynamicRolloutPolicy` (backed by thread-safe `AtomicInteger` / `BooleanSupplier`) or an external lambda supplier.
   - **Trade-off:** Minimal change spread (`0` lines altered in `ChatController`, `0` regressions in existing tests). Configuration management is isolated in policy/composition infrastructure rather than embedded inside `ChatController`.
2. **Alternative 2: Internal Mutable State / Observer Pattern in Controller (Rejected)**
   - **Mechanism:** Add setters or an EventBus / configuration registry listener directly inside `ChatController` to update percentage fields.
   - **Trade-off:** Re-introduces mutable state and multiple responsibilities into `ChatController`, violating Single Responsibility (SRP) and Open/Closed (OCP) principles and over-engineering a simple orchestration class.

## 3. Comparison of Pre-Event B vs. Post-Event B States
- **Lines of code changed in `ChatController`:** `0`
- **Cyclomatic Complexity (CC):** `2` (unchanged)
- **Coupling (CBO):** `1` (depends solely on `RolloutPolicy`)
- **Test Results (`test_logs/event_b/index.html`, Gradle 8.14):** **10 total tests — 8 active PASS (100%), 0 failures, 2 ignored (`0.073s`)**:
  - `ChatControllerShould`: 2 tests, 0 failures, 2 ignored (original non-deterministic CodelyTV baseline tests)
  - `ChatControllerTest`: 3/3 PASS (Week 9 Strategy/controller verification)
  - `RandomPercentageRolloutPolicyTest`: 3/3 PASS (Week 9 boundary & policy verification)
  - `EventBDynamicRolloutTest`: 2/2 PASS (Event B runtime extensibility verification)

## 4. Conclusion & Trade-off / Anti-Pattern Risk
- **Verdict:** The Week 9 design decision (GoF Strategy Pattern via `RolloutPolicy`) is **FULLY SUPPORTED**. The strategy abstraction completely insulated `ChatController` from runtime configuration dynamics.
- **Trade-off & Anti-Pattern Risk:**
  - **Avoided:** God-class / SRP erosion from embedding mutable runtime configuration listeners inside `ChatController`.
  - **Remaining Risk / Limitation:** In larger industrial systems using framework-managed dependency injection (e.g., Spring/Guice), manual constructor chaining would become an anti-pattern. Additionally, preserving the legacy `<=` boundary condition maintains 100% regression compatibility but retains the subtle off-by-one probability edge case from the original CodelyTV implementation.