# Approval Test Workshop -- TypeScript

## Prerequisites

- Node.js 18+
- npm 9+

## Setup

```bash
npm install
```

## Run Tests

```bash
npm test
```

## Run Tests with Code Coverage

```bash
npx jest --config jest.config.js --coverage
```

Coverage report is printed to the console. HTML report at:

```
coverage/lcov-report/index.html
```

## How Approval Tests Work (TypeScript)

This workshop uses [Jest snapshot testing](https://jestjs.io/docs/snapshot-testing), which is the TypeScript/JavaScript equivalent of approval tests.

### Writing a Snapshot Test

Use `toMatchSnapshot()` to capture output:

```typescript
import { someFunction } from "../src/myModule";

describe("MyModule", () => {
    test("all combinations", () => {
        const names = ["Aged Brie", "Sulfuras, Hand of Ragnaros", "Normal Item"];
        const sellInValues = [-1, 0, 1, 10];
        const qualityValues = [0, 1, 49, 50];

        const results: string[] = [];
        for (const name of names) {
            for (const sellIn of sellInValues) {
                for (const quality of qualityValues) {
                    results.push(`[${name}, ${sellIn}, ${quality}] => ${someFunction(name, sellIn, quality)}`);
                }
            }
        }
        expect(results.join("\n")).toMatchSnapshot();
    });
});
```

### The Snapshot Workflow

1. **Run the test** -- Jest creates a `.snap` file in a `__snapshots__` folder
2. **First run auto-approves** -- the snapshot is written automatically (unlike Java, no manual approval needed on first run)
3. **Future runs compare** against the stored snapshot -- any change fails the test
4. **Review the diff** -- Jest shows exactly what changed in the console output
5. **Update if correct** -- run `npx jest --updateSnapshot` (or `npx jest -u`) to approve the new output
6. **Commit** the `.snap` file to version control

### File Conventions

| File | Purpose | Commit? |
|------|---------|---------|
| `__snapshots__/*.snap` | Stored snapshots (golden master) | Yes |

### Tips

- Always review snapshot changes before running `jest -u` -- don't blindly update
- Keep snapshots small and focused -- large snapshots are hard to review
- When refactoring, a snapshot change means behavior changed -- investigate before updating
- You can use `toMatchInlineSnapshot()` to store the snapshot directly in the test file (useful for small outputs)

## Mutation Testing (Stryker)

```bash
npm run test:mutation
```

Open the report at:

```text
reports/mutation/mutation.html
```
