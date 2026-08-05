---
applyTo: "**/*Test.java"
---

## Java test instructions

1. Follow existing JUnit 4 style (`@Test`, `@Before`) used in this repository.
2. Keep test naming consistent with the current suite style (for example `subject_will_expectation_when_condition`).
3. Prefer clear behavior-focused tests that exercise game actions, movement, inventory, and artifact behavior.
4. Reuse existing test fixtures/helpers in `GameComponents/src/test/java/au/bartish/game/basic` when they fit, instead of duplicating setup.
5. Use AssertJ assertion style in the touched file and keep assertions explicit.
6. Avoid sleep-based timing or brittle console-output coupling unless output text is the behavior under test.
