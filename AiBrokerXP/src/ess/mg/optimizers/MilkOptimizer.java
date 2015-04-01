package ess.mg.optimizers;

import ess.Currency;
import ess.Price;
import ess.mg.markets.financial.FinancialMarket;

public class MilkOptimizer {

    public static void main(final String... args) {
        final MilkOptimizer opt = new MilkOptimizer(0.97, 0.1, 10.7180);
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
        System.out.println(" RON Price = " + ron);
        System.out.println("GOLD Price = " + goldRon);
        return ron.getAmount() <= goldRon.getAmount() ? Currency.RON : Currency.EURO;
    }

}
