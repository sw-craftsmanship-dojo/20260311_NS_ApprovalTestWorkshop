# Approval Tests & Mutation Testing Workshop

A hands-on workshop to learn how to use **approval tests** (aka snapshot tests / golden master tests) to safely work with legacy code, and **mutation testing** to verify your test quality.

Available in both **Java** and **TypeScript**.

## Learning Objectives

By the end of this workshop you will be able to:

- Explain what approval tests are and when to use them
- Use combination approvals to generate comprehensive test coverage from input combinations
- Lock down legacy code behavior before refactoring
- Use mutation testing to find gaps in your test suite
- Confidently refactor messy code with a safety net

## Prerequisites

### Java

- Java 17+
- Maven 3.6+
- An IDE (IntelliJ IDEA recommended)
- A diff tool (IntelliJ built-in, WinMerge, or VS Code)

### TypeScript

- Node.js 18+
- npm 9+
- An IDE (VS Code recommended)

## Setup

### Java

```bash
cd java
mvn clean compile
```

### TypeScript

```bash
cd typescript
npm install
npx tsc --noEmit
```

## What Are Approval Tests?

Traditional tests assert specific values:

```
assertEquals("Junior mario, 1 years", mergeNameAndAge("mario", 1));
```

Approval tests take a different approach: they capture the **entire output** of your code and compare it against a previously approved result.

1. **Run the test** -- it generates a snapshot of the actual output
2. **Review the output** -- you inspect whether it looks correct
3. **Approve it** -- the snapshot becomes the expected baseline
4. **Future runs compare** against the approved snapshot -- any change is flagged as a failure

This is powerful for **legacy code** where the behavior is complex, poorly documented, and writing individual assertions would take forever.

## What Is Mutation Testing?

Code coverage tells you which lines your tests execute, but not whether your tests actually **verify** the behavior. Mutation testing answers that question.

It works by introducing small changes (mutations) into your code -- flipping `>` to `>=`, changing `+` to `-`, removing a line -- and then running your tests. If a mutation **survives** (tests still pass), it means your tests have a blind spot.

### Tools

- **Java**: [PIT (Pitest)](https://pitest.org/)
- **TypeScript**: [Stryker](https://stryker-mutator.io/)

## Workshop Flow

| Block | Duration | Content |
|-------|----------|---------|
| Introduction | 30 min | Exercise 1: understand approval tests mechanics |
| Break | 10 min | |
| Tennis Kata | 50 min | Exercise 2: write approval tests + mutation testing |
| Break | 10 min | |
| Gilded Rose Kata | 50 min | Exercise 3: lock behavior, refactor, validate with mutations |
| Wrap-up | 20 min | Discussion and key takeaways |

## Exercises

### Exercise 1: Introduction -- ApprovalsExample

**Goal:** Understand how approval tests work by examining a failing test.

**Location:**
- Java: `java/src/main/java/.../exercise1/IntroductionTests/ApprovalsExample.java`
- TypeScript: `typescript/src/exercise1/IntroductionTests/ApprovalsExample.ts`

The `mergeNameAndAge` function assigns a title based on age: "Junior" for young people, "Mister" for adults, "Senior" for elders. A test already exists that runs all age combinations through the function.

**Steps:**

1. Run the tests (`mvn test` or `npm test`)
2. Observe the test failure
3. Compare the approved output vs the actual output -- spot the difference
4. Find and fix the bug in the production code
5. Run the tests again -- they should pass

**Hint:** Look carefully at the boundary conditions around age 19.

---

### Exercise 2: Tennis Kata

**Goal:** Write approval tests from scratch and validate them with mutation testing.

**Location:**
- Java: `java/src/main/java/.../exercise2/TennisKata/`
- TypeScript: `typescript/src/exercise2/TennisKata/`

The `TennisGame1` class implements tennis scoring: Love, Fifteen, Thirty, Forty, Deuce, Advantage, Win. The code works but is hard to read.

**Steps:**

1. Read the `TennisGame1` code to get a rough idea of what it does
2. Write an approval test that generates all score combinations
   - Vary player 1 points (0 to 4) and player 2 points (0 to 4)
   - Capture the score string for each combination
3. Approve the snapshot
4. Run mutation testing and review the report
   - Java: `mvn org.pitest:pitest-maven:mutationCoverage`
   - TypeScript: `npm run test:mutation`
5. If mutations survive, improve your test to catch them

**Hint:** Use combination/snapshot testing to cover all states systematically rather than writing individual assertions.

---

### Exercise 3: Gilded Rose Kata

**Goal:** Use approval tests to safely refactor legacy code.

**Location:**
- Java: `java/src/main/java/.../exercise3/GildedRoseKata/`
- TypeScript: `typescript/src/exercise3/GildedRoseKata/`

The `GildedRose.updateQuality()` method is a classic example of tangled legacy code: deeply nested conditionals handling different item types (Aged Brie, Sulfuras, Backstage passes, normal items).

**Steps:**

1. Write an approval test that covers many input combinations:
   - Item names: "Normal Item", "Aged Brie", "Sulfuras, Hand of Ragnaros", "Backstage passes to a TAFKAL80ETC concert"
   - SellIn values: -1, 0, 1, 5, 6, 10, 11
   - Quality values: 0, 1, 49, 50
2. Approve the golden master snapshot
3. Run mutation testing -- are all mutations killed?
4. If not, add more test inputs until mutation score is high
5. Now **refactor** the code with confidence -- any behavioral change will break your test
6. After refactoring, verify your approval test still passes

**Hint:** The approval test is your safety net. If it passes after refactoring, you haven't changed any behavior.

## Running Mutation Testing

### Java (PIT)

```bash
cd java
mvn org.pitest:pitest-maven:mutationCoverage
```

Report: `java/target/pit-reports/index.html`

### TypeScript (Stryker)

```bash
cd typescript
npm run test:mutation
```

Report: `typescript/reports/mutation/html/index.html`

## Key Takeaways

- **Approval tests let you lock legacy behavior** without understanding every detail first
- **Combination testing** generates comprehensive coverage from input combinations -- much faster than writing individual test cases
- **Mutation testing reveals blind spots** that code coverage alone cannot detect
- Together, approval tests + mutation testing give you the **confidence to refactor** safely
- The workflow is: **lock** (approval test) -> **validate** (mutation test) -> **refactor** (with safety net)

## Project Structure

```
java/
  pom.xml
  src/main/java/workshop/approvaltests/
    exercise1/IntroductionTests/     ApprovalsExample
    exercise2/TennisKata/            TennisGame, TennisGame1
    exercise3/GildedRoseKata/        GildedRose, Item
  src/test/java/workshop/approvaltests/
    exercise1/                       ExampleTest
    exercise2/                       TennisGameTest (empty)
    exercise3/                       GildedRoseTest (empty)

typescript/
  package.json, tsconfig.json, jest.config.ts, stryker.config.json
  src/
    exercise1/IntroductionTests/     ApprovalsExample
    exercise2/TennisKata/            TennisGame, TennisGame1
    exercise3/GildedRoseKata/        GildedRose, Item
  test/
    exercise1/                       ApprovalsExample.test
    exercise2/                       TennisGame.test (empty)
    exercise3/                       GildedRose.test (empty)
```
