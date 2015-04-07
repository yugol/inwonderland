package ess.mg;

import org.junit.Test;
import ess.mg.optimizers.ActivityResult;

public class ProfitTest {

    @Test
    public void testFightDouble() {
        final ActivityResult result = Profit.fight(new MG(), 39, 10);
        System.out.println(result);
    }

}
