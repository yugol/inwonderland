package ess.mg.markets.financial;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class FinancialMarketTest {

    @Test
    public void testBuyGoldWithRon() {
        final ExchangeResult result = FinancialMarket.buyGoldWithRon(1, 10.68457087);
        assertEquals(0, result.getEuro().getAmount(), 0.00001);
        assertEquals(0.97, result.getGold().getAmount(), 0.00001);
        assertEquals(10.68457087, result.getRon().getAmount(), 0.00001);
    }

}
