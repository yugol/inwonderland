package ess.mg;

import org.junit.Test;

public class ProfitTest {

    @Test
    public void testFightDouble() {
        final ActivityResult result = Profit.fight(39, 10);
        System.out.println(result);
    }

}
