package workshop.approvaltests.exercise1;

import workshop.approvaltests.exercise1.IntroductionTests.ApprovalsExample;
import org.approvaltests.Approvals;
import org.approvaltests.combinations.CombinationApprovals;
import org.junit.jupiter.api.Test;

public class ExampleTest {
    @Test
    void testWithApprovalTests()
    {
        Integer[] ages = new Integer[]{1};
        CombinationApprovals.verifyAllCombinations((s)->ApprovalsExample.mergeNameAndAge("mario", s),ages);
    }

    @Test
    void testWithoutCombinations()
    {
        String result = ApprovalsExample.mergeNameAndAge("mario", 1);
        Approvals.verify(result);
    }
}
