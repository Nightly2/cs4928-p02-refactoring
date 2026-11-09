# Event B Evaluation: Runtime Extensibility Pressure

## 1. Pressure Selected
* **Evolution Dimension**: Runtime Extensibility.
* **Concrete Requirement**: Allow rollout percentages and feature flags to change dynamically during application runtime without redeploying services, recreating controllers, or introducing downtime.

## 2. Alternatives Considered
1. **Alternative 1: No Structural Change to Controller + External Dynamic Strategy (Adopted)**
    * **Mechanism**: Retain `ChatController` and `@FunctionalInterface RolloutPolicy` completely untouched. Supply runtime changes by injecting `DynamicRolloutPolicy` (backed by thread-safe `AtomicInteger`) or an external lambda supplier.
    * **Trade-off**: Minimal change spread (0 lines altered in `ChatController`, 0 regressions in existing tests). Configuration state resides outside the orchestration class.
2. **Alternative 2: Internal Mutable State / Observer Pattern in Controller (Rejected)**
    * **Mechanism**: Add setters or an EventBus listener inside `ChatController` to update percentage fields directly.
    * **Trade-off**: Re-introduces mutable state and multiple responsibilities into `ChatController`, violating Single Responsibility and Open/Closed principles.

## 3. Comparison of Pre-Event B vs Post-Event B States
* **Lines of code changed in `ChatController`**: 0
* **Cyclomatic Complexity (CC)**: 2 (unchanged)
* **Coupling (CBO)**: 1 (depends solely on `RolloutPolicy`)
* **Test Results**: All baseline tests in `ChatControllerShould` pass (2/2), all Week 9 unit tests pass (13/13), and Event B runtime tests pass (2/2).

## 4. Conclusion
The Week 9 design decision (GoF Strategy Pattern via `RolloutPolicy`) is **FULLY SUPPORTED**. The strategy abstraction completely insulated `ChatController` from runtime configuration dynamics.