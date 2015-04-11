package ess.mg.optimizers;

import ess.Price;
import ess.mg.MgMarket;
import ess.mg.driver.MgWebDriver;
import ess.mg.driver.MgWebReader;
import ess.mg.goods.Quality;
import ess.mg.goods.food.Dairy;

public class MilkOptimizer {

    public static void main(final String... args) {
        final MgWebReader reader = new MgWebDriver();
        reader.login();
        final Price globalPrice = reader.fetchPrice(new Dairy(Quality.HIGH), MgMarket.GLOBAL);
        final Price localPrice = reader.fetchPrice(new Dairy(Quality.HIGH), MgMarket.LOCAL);
        final Double xChangeRate = reader.fetchGoldRonExchangeRate();
        reader.close();
        final MilkOptimizer opt = new MilkOptimizer(localPrice, globalPrice, xChangeRate);
        opt.optimiseNow();
    }

    private final Price  ron;
    private final Price  gold;
    private final double goldRonExchangeRate;

    public MilkOptimizer(final Price local, final Price global, final double goldRonExchangeRate) {
        ron = local;
        gold = global;
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
