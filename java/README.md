# Approval Test Workshop -- Java

## Prerequisites

- Java 17+
- Maven 3.6+
- A diff tool (IntelliJ built-in, WinMerge, or VS Code)

## Setup

```bash
mvn clean compile
```

## Run Tests

```bash
mvn test
```

## Run Tests with Code Coverage (JaCoCo)

```bash
mvn clean test
```

JaCoCo runs automatically during the test phase. Open the report at:

```
target/site/jacoco/index.html
```

## How Approval Tests Work (Java)

This workshop uses the [ApprovalTests.Java](https://github.com/approvals/approvaltests.java) library (v9.5.0).

### Writing an Approval Test

Use `CombinationApprovals.verifyAllCombinations` to test all input combinations:

```java
import org.approvaltests.combinations.CombinationApprovals;
import org.junit.jupiter.api.Test;

public class MyTest {
    @Test
    void testAllCombinations() {
        String[] names = {"Aged Brie", "Sulfuras, Hand of Ragnaros", "Normal Item"};
        Integer[] sellInValues = {-1, 0, 1, 10};
        Integer[] qualityValues = {0, 1, 49, 50};

        CombinationApprovals.verifyAllCombinations(
            (name, sellIn, quality) -> {
                // call your production code and return a string result
                return someMethod(name, sellIn, quality);
            },
            names, sellInValues, qualityValues
        );
    }
}
```

### The Approval Workflow

1. **Run the test** -- it generates a `.received.txt` file next to the test class
2. **Test fails** (no `.approved.txt` exists yet) and your diff tool opens
3. **Review the output** in the diff tool -- is it correct?
4. **Approve it** -- copy the received content into the `.approved.txt` file (or rename the file)
5. **Run the test again** -- it passes
6. **Commit** the `.approved.txt` file to version control

### File Conventions

| File | Purpose | Commit? |
|------|---------|---------|
| `*.approved.txt` | Approved baseline (golden master) | Yes |
| `*.received.txt` | Actual output from last run | No (gitignored) |

### Tips

- The `.approved.txt` filename is based on your test class and method name: `ClassName.methodName.approved.txt`
- If you rename a test method, you need to re-approve (the old file becomes orphaned)
- When refactoring, any change to the `.received.txt` means behavior changed -- investigate before approving

## Mutation Testing (PIT)

```bash
mvn org.pitest:pitest-maven:mutationCoverage
```

Open the report at:

```
target/pit-reports/index.html
```
