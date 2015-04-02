package ess.mg.optimizers;

import ess.Currency;
import ess.Price;
import ess.mg.driver.MgWebReader;
import ess.mg.markets.financial.FinancialMarket;

public class MilkOptimizer {

    public static void main(final String... args) {
        final MgWebReader reader = new MgWebReader();
        reader.login();
        final Double xChangeRate = reader.fetchGoldRonExchangeRate();
        reader.close();
        final MilkOptimizer opt = new MilkOptimizer(0.92, 0.09, xChangeRate);
        System.out.println(opt.optimiseNow());
    }

    private final Price  ron;
    private final Price  gold;
    private final double goldRonExchangeRate;

    public MilkOptimizer(final double ron, final double gold, final double goldRonExchangeRate) {
        this.ron = Price.ron(ron);
        this.gold = Price.gold(gold);
        this.goldRonExchangeRate = goldRonExchangeRate;
    }

    public Currency optimiseNow() {
        final Price goldRon = FinancialMarket.estimateGoldToRon(gold.getAmount(), goldRonExchangeRate).getRon();
        System.out.println("    X-RATE = " + goldRonExchangeRate);
        System.out.println("GOLD Price = " + gold);
        System.out.println(" RON Price = " + ron);
        System.out.println("GOLD Price = " + goldRon);
        return ron.getAmount() <= goldRon.getAmount() ? Currency.RON : Currency.EURO;
    }

}
