# CS4928 Group G02 — Project P02: AI-Assisted Refactoring to GoF Patterns and SOLID Principles

## 1. Project Overview & Reference Artefacts
- **Group:** G02 (Project Pack P02 — OO Design, Patterns & Refactoring)
- **Assigned Study:** Ampatzoglou et al., *"AI-assisted code refactoring: Where can it be helpful and where do humans outperform it?"*, *Journal of Systems and Software* (2026). DOI: `10.1016/j.jss.2026.112862`
- **Upstream Subject Repository:** `https://github.com/CodelyTV/refactoring-code_smells-design_patterns/tree/main/examples/java/`
- **Upstream Snapshot:** Commit `900d69b`, accessed `03/10/2026`
- **Frozen GenAI Model Used Across Milestones:** `Gemini 3.8 Flash Low`

---

## 2. Frozen Milestone Tags (Audit Trail)
All stage freezes are preserved in chronological order without rewriting earlier commits:

| Stage | Git Tag | Commit | Scope Frozen |
| :--- | :--- | :--- | :--- |
| **Week 5 (M1)** | `M1_PLAN` | `1416845` | Preregistered RQs, bounded sample, method, and decision rules (`m1_plan/frozen_plan.md`) |
| **Week 7 (M2)** | `M2_REPRO` | `7f57cae` | Bounded RQ1 reproduction & RQ2 prompt-constraint experiment (`m2_reproduction/`) |
| **Pre-Event A** | `PRE_EVENT_A` | `7f57cae` | Pre-Event-A frozen state prior to held-out prompt evaluation |
| **Week 9 (M4)** | `PRE_EVENT_B` | `3e3ff5a` | Chosen Week 9 GoF Strategy refactoring (`RolloutPolicy`) prior to Event B |
| **Week 10 (Event B)** | `POST_EVENT_B` | `d0368dd` | Event B runtime-extensibility evaluation (`DynamicRolloutPolicy`) |
| **Week 11 (Final)** | `FINAL` | *Tagged at FINAL* | Complete evidence trail, scoring/analysis records, and reproducibility notes |

---

## 3. Repository Structure & Evidence Map

```text
.
├── README.md                                  # Project map, reproducibility guide, and AI-use notes
├── m1_plan/
│   └── frozen_plan.md                         # Preregistered M1 plan + post-freeze metadata addendum
├── m2_reproduction/
│   ├── RQ1/
│   │   ├── rq1_scoring_sheet.md               # Case-by-case 0–10 diagnosis vs. implementation scores
│   │   ├── cases/                             # case_01..case_04 Java source snapshots
│   │   ├── prompts_and_outputs/               # Frozen diagnosis & implementation prompts/outputs
│   │   └── test_logs/                         # HTML test reports for case_01..case_04
│   └── RQ2/
│       ├── rq2_results.md                     # Condition 0/1/2 comparison & decision-rule verdict
│       ├── prompts_and_outputs/               # Condition 1 (unguided) & Condition 2 (constrained) prompts/outputs
│       ├── condition_{0,1,2}_metric.txt       # Cyclomatic Complexity logs
│       └── condition_{0,1,2}_test_log.txt     # Regression test logs
├── POST_EVENT_A/
│   ├── event_a_analysis.md                    # Held-out comparison, transfer verdict, and design implication
│   ├── case_A1_chat_rollout_*                 # Frozen unguided & constrained prompts/outputs (Case A1)
│   └── case_A2_student_grades_*               # Frozen unguided & constrained prompts/outputs (Case A2)
└── m4_design/
    ├── m4_design_decision.md                  # Week 9 design pressure, 3 alternatives, and verification
    ├── event_b_analysis.md                    # Event B runtime extensibility evaluation & trade-offs
    ├── java-chat_rollout-01_base/             # Baseline (Status Quo) Gradle project
    ├── java-chat_rollout-02_unguided/         # Unguided AI proposal (IntSupplier) Gradle project
    ├── java-chat_rollout-03_chosen_strategy/  # Adopted Strategy (RolloutPolicy) + Event B DynamicRolloutPolicy
    └── test_logs/event_b/index.html           # Gradle 8.14 HTML test report (10 total: 8 active PASS, 2 ignored)
```

---

## 4. Environment & Reproducibility Instructions
- **Java:** JDK 17 (`17.0.20.1`)
- **Build & Test Tool:** Gradle Wrapper (`Gradle 8.14`, included in each `m4_design/java-chat_rollout-*` project)
- **Metric Measurement:** Cyclomatic Complexity (`CC`) and Coupling Between Objects (`CBO`) were computed manually from the source methods/classes (recorded in `m2_reproduction/RQ2/condition_*_metric.txt`, `m4_design/m4_design_decision.md`, and `m4_design/event_b_analysis.md`).

### Re-Running Automated Tests (M4 & Event B)
From the repository root on Windows PowerShell:

```powershell
cd m4_design\java-chat_rollout-03_chosen_strategy
.\gradlew.bat test
```

*(On macOS/Linux, run `./gradlew test`.)*

Expected result (matching `m4_design/test_logs/event_b/index.html`): **10 total tests — 8 active PASS (`ChatControllerTest` 3/3, `RandomPercentageRolloutPolicyTest` 3/3, `EventBDynamicRolloutTest` 2/2), 2 ignored (`ChatControllerShould`), 0 failures.**

To inspect the baseline or unguided Week 9 alternatives, run the same command inside `m4_design\java-chat_rollout-01_base` or `m4_design\java-chat_rollout-02_unguided`.

---

## 5. Summary of Results & Traceable Deviations
- **RQ1 (Bounded Reproduction):** **NOT REPRODUCED** — Mean Diagnosis = `9.50/10` vs. Mean Implementation = `9.25/10` (`Δ = -0.25`).
  - *Traceable Numbering Note:* The four preregistered CodelyTV cases from `M1_PLAN` were executed in `m2_reproduction/RQ1/cases/` as: `case_01` (`java-booking-01_base`), `case_02` (`java-chat_rollout-02_parameter`), `case_03` (`java-student_grades-01_base`), and `case_04` (`java-booking-02_introduce_parameter_object`).
- **RQ2 (Prompt Constraints on `java-booking-01_base`):** Explicit design constraints preserved the public `Booking` constructor and passed regression tests (via Strategy + `BookingStatusCalculators` registry), whereas the unguided prompt broke backward compatibility. Cyclomatic Complexity remained `3` across all conditions, illustrating that CC does not capture OCP/DIP decoupling.
- **Change Event A (Held-Out Tasks):** On `case_A1` (`java-chat_rollout-01_base`), constraint-rich prompting produced a clean Strategy (`RolloutPolicy`), whereas unguided prompting produced primitive obsession (`IntSupplier`) and conflicting proposals. On `case_A2` (`java-student_grades-02_*`), both conditions converged (13/13 tests PASS, `CC = 6`), showing that GoF patterns are only justified when concrete design forces demand them.
- **M4 & Change Event B (Runtime Extensibility):** The Week 9 Strategy refactoring (`RolloutPolicy`) absorbed dynamic runtime rollout changes via `DynamicRolloutPolicy` with **0 lines changed** in `ChatController`, `CC = 2`, `CBO = 1`, and **8/8 active tests passing**.

---

## 6. Material AI-Use & Verification Notes
AI (`Gemini 3.8 Flash Low`) was used as an engineering assistant to generate candidate refactorings and diagnostic outputs under frozen prompts. All outputs were independently verified against compilation, Gradle test suites, and SOLID/GoF principles:
1. **Rejected Breaking API Change (M2 RQ2 — Condition 1):** Verification via regression tests caught that the unguided AI altered `Booking`'s public constructor by adding a required `BookingStatusCalculator` parameter, breaking existing callers (`m2_reproduction/RQ2/condition_1_test_log.txt`).
2. **Rejected Primitive Obsession & Silent Boundary Bug (Event A / M4 — `ChatController`):** Code inspection of the unguided AI output (`m4_design/java-chat_rollout-02_unguided/`) revealed primitive obsession (`IntSupplier`), conflicting architectural proposals, and a silent behavioral change from `<=` to `<` on the rollout percentage check. We rejected it in favor of the constraint-rich `@FunctionalInterface RolloutPolicy` preserving exact `<=` semantics.
3. **Rejected Unnecessary Pattern Overhead (Event A — `case_A2` & Event B):** Verified that `StudentGradeCalculator` did not warrant a GoF pattern and that `ChatController` did not require an internal Observer/EventBus in Event B, choosing the smallest justified design response.