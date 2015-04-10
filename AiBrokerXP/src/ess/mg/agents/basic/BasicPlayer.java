package ess.mg.agents.basic;

import java.util.Calendar;
import aibroker.util.Moment;
import ess.mg.MG;
import ess.mg.actions.ActionResult;
import ess.mg.agents.Agent;
import ess.mg.driver.model.Transactions;
import ess.mg.goods.Quality;
import ess.mg.goods.food.Cuisine;
import ess.mg.goods.food.Wine;

public class BasicPlayer extends Agent {

    public static void main(final String... args) throws InterruptedException {
        new BasicPlayer().start();
    }

    private static final Moment REFERENCE_TIME       = Moment.fromIso("21:00:00");
    private static final Moment PRE_ACTIVATE_START   = REFERENCE_TIME.newAdd(Calendar.MINUTE, 1);
    private static final Moment PRE_ACTIVATE_STOP    = PRE_ACTIVATE_START.newAdd(Calendar.MINUTE, 13);
    private static final Moment ACTIVATION_START     = REFERENCE_TIME.newAdd(Calendar.MINUTE, 17);
    private static final Moment ACTIVATION_STOP      = ACTIVATION_START.newAdd(Calendar.MINUTE, 20);
    private static final double EARN_ENERGY_TRESHOLD = 40;
    private static final Moment ACTIVITY_START       = REFERENCE_TIME.newAdd(Calendar.MINUTE, 30);
    private static final Moment ACTIVITY_STOP        = REFERENCE_TIME.newAdd(Calendar.MINUTE, 180);

    @Override
    public void run() {
        setRepeatAfter(1 * 60 * 1000);

        new AInitialize(Moment.fromIso(Moment.getNow().toIsoDate(), REFERENCE_TIME.toIsoTime()), this, 180 * 1000).perform();

        final Moment serverTime = getGlobal().getServerTime();
        final int fightRemainingMinutes = getGlobal().getFightRemainingMinutes();
        final double serverEnergy = getGlobal().getEnergy();
        final Transactions transactions = getGlobal().getTransactions();

        if (serverTime.compareTo(PRE_ACTIVATE_START) < 0) {
            final long nowMin = serverTime.getTimeInMillis();
            final long nextMin = PRE_ACTIVATE_START.getTimeInMillis();
            long wait = (nextMin - nowMin) / 1000 / 60 - 1;
            if (wait < 0) {
                wait = 0;
            }
            setRepeatAfter(wait * 60 * 1000);
        }

        if (PRE_ACTIVATE_START.compareTo(serverTime) <= 0 && serverTime.compareTo(PRE_ACTIVATE_STOP) <= 0) {
            if (transactions.getFoodCount() <= 0) {
                new ABuyGoods(new Cuisine(Quality.HIGH), this, 60 * 1000).perform();
                setRepeatAfter(0);
                return;
            } else {
                final int nowMin = serverTime.get(Calendar.MINUTE);
                final int nextMin = ACTIVATION_START.get(Calendar.MINUTE);
                int wait = nextMin - nowMin - 1;
                if (wait < 0) {
                    wait = 0;
                }
                setRepeatAfter(wait * 60 * 1000);
            }
        }

        if (ACTIVATION_START.compareTo(serverTime) <= 0 && serverTime.compareTo(ACTIVATION_STOP) <= 0) {
//            if (transactions.getMilkCount() <= 0) {
//                final ActionResult buyMilk = new ABuyGoods(new Dairy(Quality.HIGH), this, 60 * 1000).perform();
//                setRepeatAfter(30 * 1000);
//                if (!buyMilk.isSuccessful()) { return; }
//            }
            if (transactions.getWineCount() <= 0) {
                new ABuyGoods(new Wine(), this, 60 * 1000).perform();
                setRepeatAfter(0);
            }
            if (transactions.getNewspaperCount() < MG.MAX_NEWSPAPRES_PER_DAY) {
                new ABuyNewspapers(MG.MAX_NEWSPAPRES_PER_DAY - transactions.getNewspaperCount(), this, 300 * 1000).perform();
                setRepeatAfter(0);
                return;
            }
        }

        if (serverEnergy >= EARN_ENERGY_TRESHOLD ||
                ACTIVITY_START.compareTo(serverTime) <= 0 && serverTime.compareTo(ACTIVITY_STOP) <= 0) {
            if (transactions.getFightCount() < MG.MAX_FIGHTS_PER_DAY) {
                final ActionResult result = new AFight(this, 60 * 1000).perform();
                if (result.isSuccessful()) {
                    setRepeatAfter(10 * 60 * 1000 - 10);
                    return;
                } else {
                    int wait = fightRemainingMinutes;
                    if (wait < 0) {
                        wait = 0;
                    }
                    setRepeatAfter(wait * 60 * 1000);
                }
//            } else if (transactions.getWorkCount() < MG.MAX_WORK_PER_DAY) {
//                final WorkResult result = new AWork(this, 60 * 1000).perform();
//                if (!result.isSuccessful()) {
//                    setRepeatAfter(30 * 1000);
//                    return;
//                } else {
//                    setRepeatAfter(-1);
//                }
            } else {
                setRepeatAfter(-1);
            }
        }
    }

}
