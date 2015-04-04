package ess.mg.agents.basic;

import aibroker.util.Moment;
import ess.mg.MG;
import ess.mg.actions.PurchaseResult;
import ess.mg.agents.Agent;
import ess.mg.agents.basic.actions.BuyGoods;
import ess.mg.agents.basic.actions.Fight;
import ess.mg.agents.basic.actions.Initialize;
import ess.mg.goods.Quality;
import ess.mg.goods.food.Cuisine;
import ess.mg.goods.food.Dairy;
import ess.mg.goods.food.Wine;

public class BasicPlayer extends Agent {

    public static void main(final String... args) {
        final BasicPlayer player = new BasicPlayer();
        player.run();
        player.getDriver().close();
    }

    private static final Moment CUISINE_START   = Moment.fromIso("21:01:00");
    private static final Moment CUISINE_STOP    = Moment.fromIso("21:12:00");
    private static final Moment BEVERAGES_START = Moment.fromIso("21:16:00");
    private static final Moment BEVERAGES_STOP  = Moment.fromIso("21:29:00");
    private static final Moment FIGHT_TIME      = Moment.fromIso("21:30:00");
    private static final double ENERGY_TRESHOLD = 33;

    @Override
    public void run() {
        new Initialize(this, 1000 * 30).perform();
        if (CUISINE_START.compareTo(getGlobal().getServerTime()) <= 0 && getGlobal().getServerTime().compareTo(CUISINE_STOP) <= 0) {
            if (getGlobal().getCuisine(Quality.HIGH) <= 0) {
                new BuyGoods(new Cuisine(Quality.HIGH), this, 1000 * 20).perform();
                reset();
            }
        }
        if (BEVERAGES_START.compareTo(getGlobal().getServerTime()) <= 0 && getGlobal().getServerTime().compareTo(BEVERAGES_STOP) <= 0) {
            final PurchaseResult wineResult = new BuyGoods(new Wine(), this, 1000 * 20).perform();
            final PurchaseResult milkResult = new BuyGoods(new Dairy(Quality.HIGH), this, 1000 * 20).perform();
            if (wineResult.isSuccessful() || milkResult.isSuccessful()) {
                reset();
            }
        }
        if (getGlobal().getEnergy() >= ENERGY_TRESHOLD || FIGHT_TIME.compareTo(getGlobal().getServerTime()) <= 0) {
            if (getGlobal().getFightCount() < MG.MAX_FIGHTS_PER_DAY) {
                new Fight(this).perform();
            } else {
                // work
            }
        }
    }

}
