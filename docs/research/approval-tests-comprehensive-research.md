# Approval Tests: Comprehensive Research Document

**Research Date:** 2026-02-20
**Research Depth:** Detailed

---

## Table of Contents

1. [Origins and Definition](#1-origins-and-definition)
2. [How It Works](#2-how-it-works)
3. [Key Libraries and Frameworks](#3-key-libraries-and-frameworks)
4. [When to Use](#4-when-to-use)
5. [When NOT to Use](#5-when-not-to-use)
6. [Best Practices](#6-best-practices)
7. [Relationship to Mutation Testing](#7-relationship-to-mutation-testing)
8. [Relationship to TDD](#8-relationship-to-tdd)
9. [Common Pitfalls](#9-common-pitfalls)
10. [Key References](#10-key-references)

---

## 1. Origins and Definition

### Definition

Approval testing is a software testing technique where the output of a system under test is captured, reviewed by a human, and stored as the "approved" expected result. On subsequent test runs, the current output is compared against this approved baseline. The test passes if the outputs match; it fails if they differ, leaving it to a human reviewer to either approve the new output or fix the code.

### Who Coined the Term

**Llewellyn Falco** is the creator of the ApprovalTests framework and is credited with popularizing the term "approval tests." The technique emerged around 2007 from a weekly coding group.

### Relationship to Characterization Testing (Michael Feathers)

The term "characterization test" was coined by **Michael Feathers** in *Working Effectively with Legacy Code* (2004). Feathers defines a characterization test as "a test that characterizes the actual behavior of a piece of code." The critical distinction is that characterization tests do not verify what code is *supposed* to do, but what the code *actually and currently does*.

Feathers' philosophy: "In nearly every legacy system, what the system does is more important than what it is supposed to do."

The same technique goes by many names:

| Name | Origin / Context |
|---|---|
| **Characterization Tests** | Michael Feathers, *Working Effectively with Legacy Code* |
| **Golden Master Tests** | From the audio record-mastering industry |
| **Snapshot Tests** | Popularized by Jest in the JavaScript ecosystem |
| **Approval Tests** | Llewellyn Falco's framework and recommended term |
| **Pinning Tests** | Used in some refactoring communities |

---

## 2. How It Works

### The Approval Workflow

#### Step 1: Receive

When a test runs, the approval test framework captures the output and writes it to a **received file** (e.g., `TestClass.testMethod.received.txt`). It looks for a corresponding **approved file** (e.g., `TestClass.testMethod.approved.txt`).

#### Step 2: Compare

The framework compares received vs approved:
- **Match:** test passes. Received file is deleted.
- **Differ (or no approved file):** test fails. A diff tool is launched showing both files.

#### Step 3: Review

The developer examines the differences and determines:
- Is the new output correct? (approve it)
- Is the old output correct? (fix the code)

#### Step 4: Approve

If the new output is correct, the developer approves it by copying received content into the approved file (via diff tool or file rename).

### File Management Rules

- `.received.*` files: never commit (add to `.gitignore`), auto-deleted on passing tests
- `.approved.*` files: always commit -- they are the shared golden master

### First-Run Behavior

On first run, no approved file exists, so the test always fails. The diff tool shows received output vs an empty file. The developer reviews and approves to establish the initial baseline.

---

## 3. Key Libraries and Frameworks

### ApprovalTests (Llewellyn Falco)

The original polyglot framework with implementations in Java, .NET, C++, Python, Ruby, PHP, and JavaScript. Falco reports a typical 60:1 ratio -- 60 lines of production code covered for every 1 line of test code.

Website: [approvaltests.com](https://approvaltests.com)

### Jest Snapshot Testing (JavaScript/TypeScript)

Built-in snapshot testing via `toMatchSnapshot()`. Key difference from ApprovalTests: Jest auto-approves on first run and uses `--updateSnapshot` flag for updates rather than diff-tool review.

### Verify (.NET - Simon Cropp)

Modern .NET snapshot testing with extensible architecture. Supports NUnit, xUnit, MSTest. Rich scrubber ecosystem for non-deterministic data. Member of the .NET Foundation.

### Other Ecosystems

- **Python:** `approvaltests` package, `pytest-snapshot`
- **Go:** `go-approval-tests`
- **Rust:** `insta` crate

---

## 4. When to Use

### Legacy Code Without Tests

The primary use case. Before refactoring legacy code, capture existing behavior without requiring deep understanding. You don't need to know what the code is processing -- just capture its output.

### Complex Output Verification

When output is a complex data structure (JSON, XML, HTML, HTTP responses), writing individual assertions is impractical. Approval tests capture everything in one assertion.

### Refactoring Safety Nets

Changes to approval test output signal unintended behavioral changes during refactoring.

### Combinatorial Testing

Test all combinations of input parameters against a single approved output file. Replaces dozens of individual test cases with one comprehensive approval test.

### Short Deadlines / Time Pressure

Capturing behavior with approval tests is the fastest path to a safety net.

---

## 5. When NOT to Use

### Simple Value Assertions

When the expected output is a simple value, a traditional `assertEquals` is clearer.

### Frequently Changing Output

When output changes frequently and legitimately (e.g., UI under active design), approval tests create friction with constant re-approval.

### Non-Deterministic Systems (Without Scrubbers)

Timestamps, GUIDs, random values cause failures every run unless scrubbers are applied.

### As a Permanent Replacement for Unit Tests

Approval tests are a stepping stone. As you refactor and extract smaller units, write proper unit tests for those units.

---

## 6. Best Practices

### Combination Approvals

Use combinatorial testing to systematically test all combinations of input parameters. Prevents missing edge cases and replaces many individual test methods with one comprehensive test.

### Scrubbers for Non-Deterministic Data

Scrubbers transform non-deterministic output into stable values:

| Pattern | Example |
|---|---|
| GUID scrubbing | `3F2504E0-...` becomes `guid_1` |
| Date/time scrubbing | `2026-02-20T14:30:00Z` becomes `[date_time]` |
| Regex-based scrubbing | Digits replaced with `[number]` |

Smart scrubbers preserve referential relationships: the same GUID always maps to the same label.

### Printer Patterns

Transform output into human-readable, approval-friendly format. Remove irrelevant information, normalize formatting, highlight important aspects.

### Start from the Outside

Test from the highest level, then refactor from the inside. Avoids tying tests to implementation details.

### Small, Focused Snapshots

Keep snapshots small and focused. Large monolithic snapshots are hard to review and generate noise.

---

## 7. Relationship to Mutation Testing

| Aspect | Approval Tests | Mutation Testing |
|---|---|---|
| **Question** | "Has the output changed?" | "Would the tests catch a bug?" |
| **Validates** | Correctness of output | Effectiveness of the test suite |
| **Mechanism** | Compare output to approved baseline | Introduce code mutations, check if tests fail |
| **Strength** | Fast, broad coverage | Finds weak/missing assertions |

### How They Complement Each Other

1. **Approval tests provide rich assertions; mutation testing validates those assertions are meaningful.** A snapshot might be too coarse -- mutation testing reveals this.
2. **Mutation testing reveals gaps; approval tests fill them efficiently.** One approval test covers complex scenarios that would require dozens of fine-grained assertions.
3. **Practical workflow:** Approval tests during development for fast feedback. Mutation testing periodically to validate suite effectiveness. Surviving mutants guide where to add more tests.

---

## 8. Relationship to TDD

Emily Bache describes approval testing as "Test-Driven Development with a twist."

**Approval-Test-Driven Development:**
1. Write a test that captures output (fails -- no approved file)
2. Write code, run test, review received output
3. Approve when correct (test turns green)
4. Refactor (any unintended change causes failure)

### Key Differences from Traditional TDD

- **Unit size:** Larger behavioral surface than traditional TDD
- **Design influence:** Less coupling to internal structure, fewer test changes during refactoring
- **Fewer test cases:** Each test covers more behavior
- **New development:** Contrary to perception, approval tests work for new features too (Emily Bache, Lift Kata)

---

## 9. Common Pitfalls

### Approving Without Reviewing

The most dangerous pitfall. Developers may rubber-stamp approvals without examining differences. **Mitigation:** Treat approved files as production artifacts during code review.

### Snapshot Bloat

Large snapshots become impossible to review. **Mitigation:** Keep snapshots focused and small. Use printers to strip irrelevant information.

### Brittle Tests

Tightly coupled to output format -- cosmetic changes cause failures. **Mitigation:** Use printers that normalize output format.

### Locking In Buggy Behavior

Approving legacy output means approving bugs too. **Mitigation:** Treat approval as "this is the current behavior" not "this is correct behavior."

### Missing Non-Determinism

Failing to scrub all volatile content leads to intermittent failures. **Mitigation:** Run tests multiple times during setup. Compare received files across runs.

### Not Committing Approved Files

Other developers and CI cannot verify against the baseline. **Mitigation:** Always commit `.approved.*` files.

---

## 10. Key References

### Books

| Title | Author |
|---|---|
| *Working Effectively with Legacy Code* (2004) | Michael Feathers |
| *Approval Testing by Example* | Emily Bache |

### Key People

| Person | Role |
|---|---|
| **Llewellyn Falco** | Creator of ApprovalTests framework |
| **Emily Bache** | Author, approval-test-driven development advocate |
| **Michael Feathers** | Coined "characterization tests" |
| **Simon Cropp** | Creator of Verify (.NET) |
| **Nicolas Carlo** | Author of understandlegacycode.com |
| **Clare Macrae** | Co-maintainer of ApprovalTests.cpp |

### Talks and Podcasts

- SE Radio 595: Llewellyn Falco on Approval Testing
- Herding Code 117: Llewellyn Falco on Approval Tests
- Hanselminutes 360: Approval Tests with Llewellyn Falco

### Key Blog Posts

- Nicolas Carlo: [Characterization Tests, or Approval Tests?](https://understandlegacycode.com/blog/characterization-tests-or-approval-tests/)
- Emily Bache: [Approval Testing (97 Things)](https://medium.com/97-things/approval-testing-33946cde4aa8)
- Kent C. Dodds: [Effective Snapshot Testing](https://kentcdodds.com/blog/effective-snapshot-testing)
- CCD-Akademie: [Testing legacy code with approval tests](https://ccd-akademie.de/en/testing-legacy-code-with-approval-tests/)

### Websites

- [approvaltests.com](https://approvaltests.com)
- [github.com/VerifyTests/Verify](https://github.com/VerifyTests/Verify)
- [jestjs.io/docs/snapshot-testing](https://jestjs.io/docs/snapshot-testing)
- [pitest.org](https://pitest.org)
- [stryker-mutator.io](https://stryker-mutator.io)
