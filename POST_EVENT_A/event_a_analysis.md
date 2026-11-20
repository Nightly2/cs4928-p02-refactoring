# Change Event A: Held-Out Prompt Comparison & Transfer Analysis

## 1. Held-Out Cases & Preregistered Prediction (Frozen Before Execution)
- **Held-Out Task 1 (`case_A1`):** `examples/java/java-chat_rollout-01_base` (not used in M2)
- **Held-Out Task 2 (`case_A2`):** `examples/java/java-student_grades-02_*` (the `.java` file was attached directly to the prompt session rather than pasted inline in `case_A2_student_grades_02_prompt.txt`).
- **Frozen Prediction:** Constraint-rich prompts (stating design forces + GoF/SOLID rules) will achieve higher implementation correctness and better structural quality (lower Cyclomatic Complexity / coupling) while passing 100% of unit tests, whereas unguided prompts will produce ad-hoc fixes (e.g., inline parameter tweaks or stream cleanup) that fail to decouple volatile dependencies.
- **Outcome That Would Challenge M2:** If unguided prompts consistently preserved public API contracts and produced clean, cohesive abstractions equal to constraint-rich prompts across held-out cases, or if explicit design constraints introduced over-engineered abstractions that broke regression tests.
- **Frozen Method Applied:** Same M2 comparison protocol (unguided prompt vs. constraint-rich prompt evaluated against API preservation, regression tests, and Cyclomatic Complexity without post-hoc retuning).

## 2. Raw Evidence Files
- **Case A1 (`chat_rollout`):**
  - `case_A1_chat_rollout_unguided_prompt.txt` & `case_A1_chat_rollout_unguided_output.txt`
  - `case_A1_chat_rollout_constrained_prompt.txt` & `case_A1_chat_rollout_constrained_output.txt`
- **Case A2 (`student_grades`):**
  - `case_A2_student_grades_unguided_prompt.txt` & `case_A2_student_grades_unguided_output.txt`
  - `case_A2_student_grades_constrained_prompt.txt` & `case_A2_student_grades_constrained_output.txt`

## 3. Event A Results
- **Case 1 (`java-chat_rollout-01_base`):** Clear architectural advantage for the constraint-rich prompt. While both preserved backward compatibility via default constructors, the unguided prompt relied on primitive obsession (`IntSupplier`) and split its output into conflicting architectural alternatives. The constraint-rich prompt delivered a clean, cohesive GoF Strategy Pattern (`RolloutPolicy` and `RandomPercentageRolloutPolicy`), fully decoupling randomness without breaking public API contracts.
- **Case 2 (`java-student_grades-03_if_guard_clause`):** No clear advantage for the constraint-rich prompt. Both unguided and constraint-rich refactorings passed all 13 tests, preserved the public API, and had Cyclomatic Complexity = 6.

## 4. Comparison with M2: What Transfers vs. What Does Not
- **What Transfers:** Explicit design constraints still prevent primitive obsession, conflicting architectural outputs, and contract drift when a task contains genuine architectural tension and multiple plausible design directions (`case_A1`), matching the behavioral reliability advantage observed in M2 RQ2.
- **What Does Not Transfer:** Design constraints do not automatically outperform unguided prompts on every task (`case_A2`), nor do they reduce Cyclomatic Complexity (`CC = 6` in both conditions for `case_A2`, mirroring M2 RQ2 where `CC = 3` remained unchanged). When a class lacks volatile external dependencies or multiple competing extension axes, unguided and constrained prompts converge on equivalent local refactorings.

## 5. Software-Design & Maintainability Implication
- **Proportionate Pattern Application (DIP/OCP vs. Over-Engineering):** A GoF pattern should only be introduced when concrete design forces justify the indirection. In `ChatController` (`case_A1`), hardcoded `new Random()` instantiation violated Dependency Inversion (DIP) and Open/Closed (OCP), making Strategy (`RolloutPolicy`) necessary for deterministic testability and alternative rollout rules. In `student_grades` (`case_A2`), introducing heavy pattern abstractions where local cohesion and guard clauses already satisfy requirements would add unnecessary structural complexity without improving maintainability.