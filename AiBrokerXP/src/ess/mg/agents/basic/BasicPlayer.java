package ess.mg.agents.basic;

import java.util.Calendar;
import aibroker.util.Moment;
import ess.mg.MG;
import ess.mg.actions.ActionResult;
import ess.mg.actions.WorkResult;
import ess.mg.agents.Agent;
import ess.mg.agents.basic.actions.BuyGoods;
import ess.mg.agents.basic.actions.BuyNewspapers;
import ess.mg.agents.basic.actions.Fight;
import ess.mg.agents.basic.actions.Initialize;
import ess.mg.agents.basic.actions.Work;
import ess.mg.driver.MgWebFighter;
import ess.mg.driver.model.Transactions;
import ess.mg.goods.Quality;
import ess.mg.goods.food.Cuisine;
import ess.mg.goods.food.Dairy;
import ess.mg.goods.food.Wine;

public class BasicPlayer extends Agent {

    public static void main(final String... args) throws InterruptedException {
        final BasicPlayer player = new BasicPlayer();
        do {
            Thread.sleep(getWaitTime());
            setRepeatAfter(-1);
            try {
                player.start();
            } catch (final RuntimeException ex) {
                ex.printStackTrace();
            }
        } while (getWaitTime() >= 0);
        player.close();
    }

    private static final Moment REFERENCE_TIME       = Moment.fromIso("21:00:00");
    private static final Moment CUISINE_START        = REFERENCE_TIME.newAdd(Calendar.MINUTE, 1);
    private static final Moment CUISINE_STOP         = CUISINE_START.newAdd(Calendar.MINUTE, 12);
    private static final Moment BEVERAGES_START      = REFERENCE_TIME.newAdd(Calendar.MINUTE, 17);
    private static final Moment BEVERAGES_STOP       = BEVERAGES_START.newAdd(Calendar.MINUTE, 20);
    private static final Moment EARN_TIME_TRESHOLD   = REFERENCE_TIME.newAdd(Calendar.MINUTE, 30);
    private static final double EARN_ENERGY_TRESHOLD = 40;

    @Override
    public void run() {
        setRepeatAfter(3 * 60 * 1000);

        new Initialize(Moment.fromIso(Moment.getNow().toIsoDate(), REFERENCE_TIME.toIsoTime()), this, 150 * 1000).perform();

        final Moment serverTime = getGlobal().getServerTime();
        final double serverEnergy = getGlobal().getEnergy();
        final Transactions transactions = getGlobal().getTransactions();

        if (CUISINE_START.compareTo(serverTime) <= 0 && serverTime.compareTo(CUISINE_STOP) <= 0) {
            if (transactions.getFoodCount() <= 0) {
                new BuyGoods(new Cuisine(Quality.HIGH), this, 30 * 1000).perform();
                setRepeatAfter(0);
                return;
            } else {
                final int nowMin = serverTime.get(Calendar.MINUTE);
                final int nextMin = BEVERAGES_START.get(Calendar.MINUTE);
                int wait = (nextMin - nowMin + 1) * 60 * 1000;
                if (wait < 0) {
                    wait = 0;
                }
                setRepeatAfter(wait);
            }
        }

        if (BEVERAGES_START.compareTo(serverTime) <= 0 && serverTime.compareTo(BEVERAGES_STOP) <= 0) {
            if (transactions.getMilkCount() <= 0) {
                final ActionResult buyMilk = new BuyGoods(new Dairy(Quality.HIGH), this, 30 * 1000).perform();
                setRepeatAfter(0);
                if (!buyMilk.isSuccessful()) { return; }
            }
            if (transactions.getWineCount() <= 0) {
                new BuyGoods(new Wine(), this, 30 * 1000).perform();
                setRepeatAfter(0);
            }
            if (transactions.getNewspaperCount() < MG.MAX_NEWSPAPRES_PER_DAY) {
                new BuyNewspapers(MG.MAX_NEWSPAPRES_PER_DAY - transactions.getNewspaperCount(), this, 300 * 1000).perform();
                setRepeatAfter(0);
                return;
            }
        }

        if (serverEnergy >= EARN_ENERGY_TRESHOLD || serverTime.compareTo(EARN_TIME_TRESHOLD) >= 0) {
            if (transactions.getFightCount() < MG.MAX_FIGHTS_PER_DAY) {
                final ActionResult result = new Fight(this, 60 * 1000).perform();
                if (result.isSuccessful()) {
                    setRepeatAfter(10 * 60 * 1000);
                    return;
                } else if (MgWebFighter.FIGHTING.equals(result.getMessage())) {
                    setRepeatAfter(3 * 60 * 1000);
                } else {
                    setRepeatAfter(0);
                    return;
                }
            } else if (transactions.getWorkCount() < MG.MAX_WORK_PER_DAY) {
                final WorkResult result = new Work(this, 30 * 1000).perform();
                if (!result.isSuccessful()) {
                    setRepeatAfter(60 * 1000);
                    return;
                }
            } else {
                setRepeatAfter(-1);
            }
        }
    }

}
