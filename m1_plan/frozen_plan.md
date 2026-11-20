# Milestone M1: Preregistered Study Plan (Group G02 – Project P02)

## 1. Reference Study & Artefact
- **Paper:** Ampatzoglou et al., *"AI-assisted code refactoring: Where can it be helpful and where do humans outperform it?"*, Journal of Systems and Software (2026). DOI: `10.1016/j.jss.2026.112862`
- **Subject Repository:** `https://github.com/CodelyTV/refactoring-code_smells-design_patterns/tree/main/examples/java/` (Java refactoring/code-smell exercises)
- **Upstream Artefact Snapshot:** Commit `900d69b`, accessed `03/10/2026`.

## 2. Research Questions
- **RQ1 (Bounded Reproduction):** Across four preregistered CodelyTV Java refactoring cases, what is the quantitative difference in GenAI’s mean correctness scores (using the study's 0–10 scale) between (a) problem/quality-attribute diagnosis and (b) GoF pattern implementation?
- **RQ2 (Transfer & Design Decision):** For a selected Java refactoring case from the CodelyTV repository, how does adding explicit design constraints to an AI prompt affect verified implementation correctness, one predefined structural quality metric, and regression-test results under a specified future-change requirement, compared with a baseline prompt and leaving the original structure unchanged?

## 3. Bounded Sample & Case Identifiers
- **Selection Rule:** Four eligible Java code-smell examples selected from the CodelyTV repository and frozen prior to M2 execution:
  - `case_01`: `https://github.com/CodelyTV/refactoring-code_smells-design_patterns/tree/main/examples/java/java-booking-01_base`
  - `case_02`: `https://github.com/CodelyTV/refactoring-code_smells-design_patterns/tree/main/examples/java/java-booking-02_introduce_parameter_object`
  - `case_03`: `https://github.com/CodelyTV/refactoring-code_smells-design_patterns/tree/main/examples/java/java-chat_rollout-02_parameter`
  - `case_04`: `https://github.com/CodelyTV/refactoring-code_smells-design_patterns/tree/main/examples/java/java-student_grades-01_base`
- **RQ2 Target Case:** `https://github.com/CodelyTV/refactoring-code_smells-design_patterns/tree/main/examples/java/java-booking-01_base`

## 4. Method, Scoring & Decision Rules
- **Frozen Model:** `Gemini 3.8 Flash Low`
- **RQ1 Scoring:** Grade each case on the supplied 0–10 rubric across two dimensions: (A) Problem / quality-attribute diagnosis and (B) GoF pattern / SOLID implementation correctness.
- **RQ1 Reproduction Rule:** **REPRODUCED** if GenAI's mean score for implementation (B) is strictly higher than its mean score for diagnosis (A); **NOT REPRODUCED** if Mean(B) <= Mean(A).
- **RQ2 Evaluation Rule:** Compare Condition 0 (No structural change), Condition 1 (Baseline unguided prompt), and Condition 2 (Explicit design-constraints prompt) using regression-test pass rate and Cyclomatic Complexity. Adopted if Condition 2 passes regression tests and improves structural quality over alternatives.

## 5. Preregistered Expected Results (Pre-Execution)
- **RQ1 Expected:** GenAI will achieve higher mean correctness scores for GoF pattern implementation (8–10) than for problem/quality-attribute diagnosis (5–6), aligning with the original study's finding that LLMs struggle with diagnostic reasoning compared to code generation.
- **RQ2 Expected:** Adding explicit design constraints to the prompt will produce a verified refactoring that passes all regression tests and improves structural quality compared to unguided or unchanged baselines.