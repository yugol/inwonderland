package ess.mg;

import org.junit.Test;
import ess.mg.optimizers.ActivityResult;
import ess.mg.optimizers.Profit;

public class ProfitTest {

    @Test
    public void testFightDouble() {
        final ActivityResult result = Profit.fight(new MgContext(), 39, 10);
        System.out.println(result);
    }

}
