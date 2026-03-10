# Characterization & Mutation Tests

## Legacy Code

> "Code without tests"
> -- *Michael Feathers*

> "Profitable code that we feel afraid to change"
> -- *J. B. Rainsberger*

## How They Relate

```
  ┌─────────────────────────────────────────────┐
  │         Characterization Tests               │
  │         (the concept)                        │
  │                                              │
  │    ┌───────────────────────────────────┐     │
  │    │       Golden Master               │     │
  │    │       (the technique)             │     │
  │    │                                   │     │
  │    │    ┌─────────────────────────┐    │     │
  │    │    │    Approval Tests       │    │     │
  │    │    │    (the tool)           │    │     │
  │    │    └─────────────────────────┘    │     │
  │    │                                   │     │
  │    └───────────────────────────────────┘     │
  │                                              │
  └─────────────────────────────────────────────┘
```

- **Characterization Tests** -- the broad concept of capturing existing behavior
- **Golden Master** -- a specific technique: save output, compare against it
- **Approval Tests** -- a library/tool that implements the Golden Master technique

## Characterization Tests

When a system goes into production, it becomes its own specification. We need to know when we are changing existing behaviour *regardless of whether we think it's right or not*.

The first thing we have to do is **locking down current behaviour**.

- Tests to enable refactoring
- Ensure the system works the same
- Most often used with legacy code
- Quickly results in high test coverage

## Golden Master

```
INPUT DATA  -->  EXISTING CODE  -->  SAVE "GOLDEN MASTER"

INPUT DATA  -->  MODIFIED CODE  -->  SAME OUTPUT?  --YES-->  PASS
                                                   --NO--->  FAIL
```

## Approval Tests

Golden Master's implementation by Llewellyn Falco.

- Flexible
- Open Source
- Powerful
- Convenient
- Quick

Available for most modern languages.

**Makes it easy...**

- ...for humans to evaluate results
- ...to set tests up
- ...to format test output for readability
- ...to maintain tests
- ...in multiple languages
- ...with visual results
- ...to make characterization tests

## Mutation Tests

Faults (or **mutations**) are automatically seeded into your code, then your tests are run.

If your tests fail then the mutation is **killed**, if your tests pass then the mutation **lived**.

The quality of your tests can be gauged from the percentage of mutations killed.

```
TESTS  -->  EXISTING CODE  -->  PASS

TESTS  -->  MUTATED CODE   -->  PASS  (mutation lived -- bad)
                           -->  FAIL  (mutation killed -- good)
```

Traditional test coverage measures only which code is **executed** by your tests. It is therefore *only able to identify code that is definitely not tested*.

As it is actually able to detect whether each statement is **meaningfully** tested, *mutation testing is the gold standard* against which all other types of coverage are measured.

## References

### Videos

- [ApprovalTests -- 5 Minute Introduction (Java)](https://approvaltests.com/videos/) -- 5 min
- [ApprovalTests -- Prime Factors Kata](https://approvaltests.com/videos/) -- 4 min
- [ApprovalTests -- Chopping Kata](https://approvaltests.com/videos/) -- 9 min

### Reading

- [approvaltests.com](https://approvaltests.com/) -- official Approval Tests site by Llewellyn Falco
- [Approval Testing (Emily Bache)](https://medium.com/97-things/approval-testing-33946cde4aa8) -- concise overview on Medium
- [What's the difference between Regression Tests, Characterization Tests, and Approval Tests?](https://understandlegacycode.com/blog/characterization-tests-or-approval-tests/) -- Understand Legacy Code
- [Approval Testing Intro (Samman Coaching)](https://sammancoaching.org/learning_hours/legacy/approval_testing_intro.html) -- learning hour format
- [Gilded Rose Refactoring Kata (Emily Bache)](https://github.com/emilybache/GildedRose-Refactoring-Kata) -- the kata used in this workshop
