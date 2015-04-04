package ess.mg.optimizers;

import ess.Price;
import ess.mg.driver.MgWebDriver;
import ess.mg.driver.MgWebReader;
import ess.mg.goods.food.Dairy;
import ess.mg.markets.MgMarket;
import ess.mg.markets.financial.FinancialMarket;

public class MilkOptimizer {

    public static void main(final String... args) {
        final MgWebReader reader = new MgWebDriver();
        reader.login();
        final Double globalPrice = reader.fetchPrice(new Dairy(3), MgMarket.GLOBAL);
        final Double localPrice = reader.fetchPrice(new Dairy(3), MgMarket.LOCAL);
        final Double xChangeRate = reader.fetchGoldRonExchangeRate();
        reader.close();
        final MilkOptimizer opt = new MilkOptimizer(localPrice, globalPrice, xChangeRate);
        opt.optimiseNow();
    }

    private final Price  ron;
    private final Price  gold;
    private final double goldRonExchangeRate;

    public MilkOptimizer(final double ron, final double gold, final double goldRonExchangeRate) {
        this.ron = Price.ron(ron);
        this.gold = Price.gold(gold);
        this.goldRonExchangeRate = goldRonExchangeRate;
    }

    public MgMarket optimiseNow() {
        final Price goldRon = FinancialMarket.estimateGoldToRon(gold.getAmount(), goldRonExchangeRate).getRon();
        final MgMarket market = ron.getAmount() <= goldRon.getAmount() ? MgMarket.LOCAL : MgMarket.GLOBAL;
        System.out.println("    X-RATE = " + goldRonExchangeRate);
        System.out.println("GOLD Price = " + gold);
        System.out.println(" RON Price = " + ron);
        System.out.println("GOLD Price = " + goldRon);
        System.out.println("ADVICE: Buy " + market);
        return market;
    }

}
