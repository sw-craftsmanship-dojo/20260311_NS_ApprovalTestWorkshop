import { mergeNameAndAge } from "../../src/exercise1/IntroductionTests/ApprovalsExample";

describe("ApprovalsExample", () => {
    test("mergeNameAndAge combinations", () => {
        const ages = [1];
        const results = ages.map((age) => `[${age}] => ${mergeNameAndAge("mario", age)}`);
        expect(results.join("\n")).toMatchSnapshot();
    });
});
