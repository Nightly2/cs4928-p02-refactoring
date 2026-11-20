# RQ1 Bounded Reproduction Scoring Sheet (Milestone M2)

- **Reference Study:** Ampatzoglou et al., *Journal of Systems and Software* (2026), DOI: `10.1016/j.jss.2026.112862`
- **Frozen Model:** `Gemini 3.8 Flash Low`
- **Upstream Artefact Commit / Access Date:** `900d69b` (`03/10/2026`)
- **Scoring Scale (0–10):**
  - **(a) Problem / Quality-Attribute Diagnosis:** Accuracy in identifying the underlying code smell, design force, and violated SOLID/OO principles.
  - **(b) GoF Pattern / SOLID Implementation Correctness:** Structural fidelity to the appropriate pattern/refactoring, preservation of behavior/API contracts, and clean compilation/test execution.

## Case-by-Case Scores (Aligned to Actual Output & Test Files)

| Case ID | Upstream Case Path | (a) Diagnosis (0–10) | (b) Implementation (0–10) | Regression Tests | Evidence Links |
| :--- | :--- | :---: | :---: | :---: | :--- |
| `case_01` | `examples/java/java-booking-01_base` | 10 | 10 | PASS | `cases/case_01/`, `prompts_and_outputs/case_01_*`, `test_logs/case_01/` |
| `case_02` | `examples/java/java-chat_rollout-02_parameter` | 10 | 10 | PASS | `cases/case_02/`, `prompts_and_outputs/case_02_*`, `test_logs/case_02/` |
| `case_03` | `examples/java/java-student_grades-01_base` | 8 | 7 | PASS | `cases/case_03/`, `prompts_and_outputs/case_03_*`, `test_logs/case_03/` |
| `case_04` | `examples/java/java-booking-02_introduce_parameter_object` | 10 | 10 | PASS | `cases/case_04/`, `prompts_and_outputs/case_04_*`, `test_logs/case_04/` |
| **Mean** | **All 4 Preregistered Cases** | **9.50** | **9.25** | **4/4 PASS** | $\Delta = \text{Mean}(b) - \text{Mean}(a) = -0.25$ |

## Per-Case Scoring Justifications

- **`case_01` (`java-booking-01_base`) — Diagnosis: 10/10 | Implementation: 10/10:**
  - *(a)* Accurately diagnoses Data Clumps (`Customer`, `Discount`, `Tax`, date interval), Long Parameter List (11 constructor arguments), Low Cohesion, weak wrapper encapsulation, and the `statusFor` boundary edge case (`prompts_and_outputs/case_01_diagnosis_output.txt`).
  - *(b)* Implements a backward-compatible fluent `Booking.Builder` with cohesive domain grouping helpers (`withDates`, `withCustomer`, `withDiscount`, `withTax`) while preserving the 11-argument constructor and passing regression tests (`prompts_and_outputs/case_01_implementation_output.txt`, `test_logs/case_01/`).
- **`case_02` (`java-chat_rollout-02_parameter`) — Diagnosis: 10/10 | Implementation: 10/10:**
  - *(a)* Accurately identifies hardcoded `new Random()` instantiation (DIP/SRP/testability violation), OCP inflexibility, and the `<=` rollout percentage boundary bug (`prompts_and_outputs/case_02_diagnosis_output.txt`).
  - *(b)* Cleanly decouples randomness via `@FunctionalInterface RolloutNumberGenerator` and `RandomRolloutNumberGenerator` (Strategy Pattern) with an overloaded `ChatController(int)` constructor preserving backward compatibility and passing tests (`prompts_and_outputs/case_02_implementation_output.txt`, `test_logs/case_02/`). *(Note: also tightens `<=` to `<` as flagged in diagnosis.)*
- **`case_03` (`java-student_grades-01_base`) — Diagnosis: 8/10 | Implementation: 7/10:**
  - *(a)* Correctly identifies Primitive Obsession / generic `Pair` abuse, but misses the core SRP/OCP violation of hardcoding `allYearsTeachers` and extra-point policy inside `StudentGradeCalculator`, overlooks deep 4-level conditional nesting and convoluted `!(yearToCalculate != ...)` loop logic, and downplays magic error codes (`-1f`, `-2f`) (`prompts_and_outputs/case_03_diagnosis_output.txt`).
  - *(b)* Extracts `ExtraPointRule` / `YearBasedExtraPointRule` via Strategy with a backward-compatible constructor, but leaves `Pair` untouched, copies the convoluted `!(yearToCalculate != ...)` loop verbatim, and leaves `calculateGrades` deeply nested (`prompts_and_outputs/case_03_implementation_output.txt`, `test_logs/case_03/`).
- **`case_04` (`java-booking-02_introduce_parameter_object`) — Diagnosis: 10/10 | Implementation: 10/10:**
  - *(a)* Accurately diagnoses Object Dissolution (unpacking `DateRange`, `Customer`, `Discount`, and `Tax` into 10 flat fields), Feature Envy / Data Class on `DateRange`, and the `startDate` boundary bug (`prompts_and_outputs/case_04_diagnosis_output.txt`).
  - *(b)* Preserves whole value objects inside `Booking`, delegates `statusFor(LocalDateTime)` to `DateRange`, maintains full public API compatibility, and passes regression tests (`prompts_and_outputs/case_04_implementation_output.txt`, `test_logs/case_04/`).

## Decision Rule Verdict & Traceable Notes
- **Preregistered Rule:** **REPRODUCED** if $\text{Mean}(b) > \text{Mean}(a)$; **NOT REPRODUCED** if $\text{Mean}(b) \le \text{Mean}(a)$.
- **Outcome:** **NOT REPRODUCED** ($\text{Mean}(a) = 9.50$ vs. $\text{Mean}(b) = 9.25$; $\Delta = -0.25$).
- **Traceable Sample Note:** All four cases match the preregistered `M1_PLAN` sample set, executed in repository folder order `case_01` (`java-booking-01_base`), `case_02` (`java-chat_rollout-02_parameter`), `case_03` (`java-student_grades-01_base`), and `case_04` (`java-booking-02_introduce_parameter_object`).
- **Deviation & Threat Note:** Canonical single-class CodelyTV smells enabled near-ceiling AI diagnosis ($9.50$), whereas unresolved structural friction in `case_03` lowered implementation ($9.25$).