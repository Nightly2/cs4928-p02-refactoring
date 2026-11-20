# Milestone M4 (Week 9): Software Design & Refactoring Decision

- **Frozen Pre-Event-B Tag:** `PRE_EVENT_B` (`3e3ff5a`)
- **Target Class:** `tv.codely.chat_rollout.ChatController`
- **Artefacts Compared:**
  - Baseline (Status Quo): `java-chat_rollout-01_base/`
  - Alternative 2 (Unguided AI Proposal): `java-chat_rollout-02_unguided/`
  - Alternative 3 (Chosen Strategy Refactoring): `java-chat_rollout-03_chosen_strategy/`

---

## 1. Concrete Design Problem
In `tv.codely.chat_rollout.ChatController`, the `pullMessages()` method directly instantiates `new Random().nextInt(100)`, creating a hardcoded, volatile dependency. This violates the **Dependency Inversion Principle (DIP)** and **Open/Closed Principle (OCP)**:
- The controller is tightly coupled to non-deterministic random generation, making automated unit testing impossible without bytecode manipulation.
- Alternative rollout rules (e.g., user whitelisting or deterministic test stubs) cannot be introduced without modifying `ChatController` directly.

---

## 2. Architectural Alternatives Considered

| Alternative | Folder / Reference | Mechanism | Trade-offs |
| :--- | :--- | :--- | :--- |
| **1. No-Change / Status Quo** | `java-chat_rollout-01_base/` | Leave `new Random()` inline inside `pullMessages()`. | Avoids regression risk, but permanently blocks deterministic unit testing and OCP extensibility. |
| **2. Primitive Functional Seam (`IntSupplier`)** | `java-chat_rollout-02_unguided/` | Inject `IntSupplier` via constructor (unguided LLM proposal). | Resolves basic testability with minimal code, but suffers from **Primitive Obsession**, leaves rollout math inside `ChatController`, fails OCP if rollout rules evolve beyond integer percentages, and silently altered the boundary condition from `<=` to `<`. |
| **3. GoF Strategy Pattern (`RolloutPolicy`)** *(Adopted)* | `java-chat_rollout-03_chosen_strategy/` | Extract a domain-level `@FunctionalInterface RolloutPolicy` with concrete implementation `RandomPercentageRolloutPolicy` (constraint-rich LLM proposal). | Decouples policy evaluation from message orchestration and supports both concise lambda injection and stateful policies with minimal indirection. |

---

## 3. Chosen Intervention & Justification
We adopted **Alternative 3 (GoF Strategy Pattern via `RolloutPolicy`)**:
1. **Proportionate DIP & OCP Compliance:** Satisfies Dependency Inversion and Open/Closed without over-engineering. Marking `RolloutPolicy` as a `@FunctionalInterface` allows unit tests to inject simple lambdas (e.g., `() -> true`), while production code delegates to a dedicated policy class.
2. **Backward Compatibility:** Retains the public no-argument constructor `ChatController()`, delegating to `new RandomPercentageRolloutPolicy(NEW_CHAT_ROLLOUT_PERCENTAGE)` so existing callers remain unaffected.
3. **Behavioral Preservation (AI Verification):** Unlike the unguided LLM output (`java-chat_rollout-02_unguided/`), which silently changed the percentage threshold from `<=` to `<`, the adopted policy preserves the exact `<=` boundary evaluation to maintain 100% regression compatibility.

---

## 4. Verification Evidence
- **Structural Properties (`ChatController`):** Cyclomatic Complexity (`CC`) = `2`; Coupling (`CBO`) = `1` (depends solely on `RolloutPolicy`).
- **Automated Verification (`java-chat_rollout-03_chosen_strategy/`, Gradle 8.14):**
  - `ChatControllerTest`: `3/3` PASS (verifies deterministic routing via injected `RolloutPolicy` lambdas and default constructor compatibility).
  - `RandomPercentageRolloutPolicyTest`: `3/3` PASS (verifies `<=` percentage boundary behavior).
  - `ChatControllerShould`: `2` original non-deterministic CodelyTV baseline tests remain ignored.
  - Full combined Gradle HTML test report (including subsequent Event B verification): `test_logs/event_b/index.html`.