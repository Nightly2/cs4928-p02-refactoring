# RQ2 Results: Prompt Constraints, Correctness & Structural Quality

- **Selected Case:** `examples/java/java-booking-01_base`
- **Future-Change Requirement:** The system must support different status-calculation rules for different booking types while preserving existing behaviour for current bookings.
- **Predefined Structural Metric:** Cyclomatic Complexity (CC)
- **Raw Evidence Files:**
  - Prompts & AI Outputs: `prompts_and_outputs/condition_1_prompt.txt`, `prompts_and_outputs/condition_1_output.txt`, `prompts_and_outputs/condition_2_prompt.txt`, `prompts_and_outputs/condition_2_output.txt`
  - Metric Logs: `condition_0_metric.txt`, `condition_1_metric.txt`, `condition_2_metric.txt`
  - Test Logs: `condition_0_test_log.txt`, `condition_1_test_log.txt`, `condition_2_test_log.txt`

---

## Summary Comparison

| Condition | Prompt Type | Regression Tests | Cyclomatic Complexity | Backward Compatibility |
| :--- | :--- | :---: | :---: | :--- |
| **Condition 0** | Original Code (No structural change) | **PASS** | 3 | Baseline |
| **Condition 1** | Generic / Unguided Prompt | **FAIL** | 3 | **Broken** (public constructor signature altered) |
| **Condition 2** | Explicit Design-Constraints Prompt | **PASS** | 3 | **Preserved** (unchanged constructor + Strategy registry) |

---

## Condition 0 — Original Code
- **Regression Tests:** PASS (`condition_0_test_log.txt`)
- **Cyclomatic Complexity:** 3 (`condition_0_metric.txt`)
- **Observation:** Baseline behavior passes all tests, but status-calculation logic remains hardcoded inside `Booking`.

## Condition 1 — Generic Prompt
- **Regression Tests:** FAIL (`condition_1_test_log.txt`)
- **Cyclomatic Complexity:** 3 (`condition_1_metric.txt`)
- **Failure Analysis (AI Verification):** The unguided AI refactoring modified the public `Booking` constructor by adding a required `BookingStatusCalculator` parameter without keeping a backward-compatible signature (`prompts_and_outputs/condition_1_output.txt`). Existing regression tests called the original constructor, causing compilation failure.

## Condition 2 — Constrained Prompt
- **Regression Tests:** PASS (`condition_2_test_log.txt`)
- **Cyclomatic Complexity:** 3 (`condition_2_metric.txt`)
- **Observation:** Adding explicit design constraints caused the AI to preserve the exact existing public `Booking` constructor while extracting a `BookingStatusCalculator` Strategy interface and `BookingStatusCalculators` registry (`prompts_and_outputs/condition_2_output.txt`)[cite: 8], passing all regression tests without breaking existing callers.

---

## Decision Rule Verdict & Metric Threat
- **Preregistered Rule:** Adopted if Condition 2 passes regression tests and improves the selected structural metric (`CC`) over alternatives.
- **Outcome:** **Partially Supported / Not Adopted on CC Alone.** Condition 2 passed 100% of regression tests and prevented the breaking API change seen in Condition 1, confirming that explicit design constraints improve implementation reliability and backward compatibility. However, Cyclomatic Complexity remained `3` across all three conditions.
- **Deviation / Threat to Validity:** Cyclomatic Complexity measures intra-method control-flow branching rather than class-level coupling or Open/Closed Principle (OCP) extensibility, so it failed to capture the architectural benefit of extracting the Strategy abstraction.