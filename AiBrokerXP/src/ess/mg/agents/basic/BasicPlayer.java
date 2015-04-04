package ess.mg.agents.basic;

import aibroker.util.Moment;
import ess.mg.MG;
import ess.mg.actions.ActionResult;
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

    public static void main(final String... args) throws InterruptedException {
        final BasicPlayer player = new BasicPlayer();
        do {
            Thread.sleep(getWaitTime());
            setWaitTime(0);
            player.run();
        } while (getWaitTime() > 0);
        player.getDriver().close();
    }

    // private static final Moment REFERENCE_TIME  = Moment.fromIso("21:00:00");
    private static final Moment CUISINE_START   = Moment.fromIso("21:04:00");
    private static final Moment CUISINE_STOP    = Moment.fromIso("21:12:00");
    private static final Moment BEVERAGES_START = Moment.fromIso("21:16:00");
    private static final Moment BEVERAGES_STOP  = Moment.fromIso("21:29:00");
    private static final Moment FIGHT_TIME      = Moment.fromIso("21:30:00");
    private static final double ENERGY_TRESHOLD = 38;

    @Override
    public void run() {
        new Initialize(this, 1000 * 30).perform();
        if (CUISINE_START.compareTo(getGlobal().getServerTime()) <= 0
                && getGlobal().getServerTime().compareTo(CUISINE_STOP) <= 0) {
            if (getGlobal().getCuisine(Quality.HIGH) <= 0) {
                new BuyGoods(new Cuisine(Quality.HIGH), this, 1000 * 20).perform();
                setWaitTime(10);
                return;
            }
        }
        if (BEVERAGES_START.compareTo(getGlobal().getServerTime()) <= 0
                && getGlobal().getServerTime().compareTo(BEVERAGES_STOP) <= 0) {
            final PurchaseResult milkResult = new BuyGoods(new Dairy(Quality.HIGH), this, 1000 * 20).perform();
            final PurchaseResult wineResult = new BuyGoods(new Wine(), this, 1000 * 20).perform();
            if (wineResult.isSuccessful() || milkResult.isSuccessful()) {
                setWaitTime(10);
                return;
            }
        }
        if (getGlobal().getEnergy() >= ENERGY_TRESHOLD || FIGHT_TIME.compareTo(getGlobal().getServerTime()) <= 0) {
            if (getGlobal().getFightCount() < MG.MAX_FIGHTS_PER_DAY) {
                final ActionResult result = new Fight(this, 1000 * 30).perform();
                if (result.isSuccessful()) {
                    setWaitTime(1000 * 60 * 10 + 10);
                    return;
                } else {
                    setWaitTime(10);
                    return;
                }
            }
        }
    }

}
