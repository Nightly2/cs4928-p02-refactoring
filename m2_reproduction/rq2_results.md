# RQ2 Results

Selected case: java-booking-01_base

Future-change requirement:
The system must support different status-calculation rules for different booking types while preserving existing behaviour for current bookings.

Structural metric:
Cyclomatic Complexity

## Condition 0 — Original Code

Regression tests: PASS

Cyclomatic Complexity: 3

## Condition 1 — Generic Prompt

Regression tests: FAIL

Reason:
The AI-generated refactoring changed the Booking constructor by adding a required BookingStatusCalculator parameter. The original regression tests still used the existing constructor, so test compilation failed.

Cyclomatic Complexity: 3

## Condition 2 — Constrained Prompt

Regression tests: PASS

Cyclomatic Complexity: 3

The constrained prompt preserved the existing public constructor and used a Strategy-based design for status calculation.

## Conclusion

The constrained prompt produced a working refactoring that preserved existing regression-test behaviour.

The generic prompt produced a plausible refactoring, but it broke backward compatibility by changing the public Booking constructor.

Cyclomatic Complexity remained 3 in all three conditions.

Therefore, explicit design constraints improved implementation reliability and compatibility, but did not improve the selected structural metric.