package ess.mg.agents.operator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import javax.swing.Timer;
import aibroker.util.Moment;
import ess.mg.MgContext;
import ess.mg.agents.MgAgent;
import ess.mg.agents.actions.ABuyGoods;
import ess.mg.agents.actions.ALogin;
import ess.mg.agents.dto.PurchaseResult;
import ess.mg.agents.dto.WorkResult;
import ess.mg.driver.dto.Transactions;
import ess.mg.goods.Quality;
import ess.mg.goods.food.Cuisine;
import ess.mg.goods.food.Dairy;
import ess.mg.goods.food.Wine;

public class MgOperator extends MgAgent {

    public static void main(final String... args) {
        final Moment nowTime = Moment.getNow().getTimeMoment();
        final boolean active = WORK_START_TIME.compareTo(nowTime) <= 0 && nowTime.compareTo(STOP_TIME) < 0;
        if (active) {
            final MgOperator operator = new MgOperator();
            final Timer timer = new Timer(LIFE_TIME, new ActionListener() {

                @Override
                public void actionPerformed(final ActionEvent e) {
                    operator.stop();
                    System.exit(1);
                }

            });
            timer.setRepeats(false);
            timer.start();
            operator.start();
        }
    }

    private static final int    LIFE_TIME                = 12 * 60 * 1000;

    private static final Moment WORK_START_TIME          = Moment.fromIso("19:30:00");
    private static final Moment PREPARE_FIGHT_START_TIME = Moment.fromIso("20:15:00");
    private static final Moment FIGHT_START_TIME         = Moment.fromIso("20:30:00");
    private static final Moment STOP_TIME                = Moment.fromIso("23:00:00");

    @Override
    public void run() {

        final ALogin login = new ALogin(this);
        login.setEpoch(Moment.fromIso(Moment.getNow().toIsoDate(), WORK_START_TIME.toIsoTime()));
        login.setReadTransactions(true);
        login.perform();

        final Moment serverTime = getContext().getServerTime();
        final Transactions transactions = getContext().getTransactions();

        if (WORK_START_TIME.compareTo(serverTime) <= 0) {

            if (transactions.getMilkCount() == 0 && getContext().getRonAmount() > 1) {
                final ABuyGoods buyMilk = new ABuyGoods(this);
                buyMilk.setGoods(new Dairy(Quality.HIGH));
                for (int count = 0; count < 10; ++count) {
                    final PurchaseResult purchaseResult = buyMilk.perform();
                    if (purchaseResult.isSuccessful()) {
                        break;
                    } else {
                        final Moment lastBuyTime = purchaseResult.getMessageTime().getTimeMoment();
                        final long delay = serverTime.getDelta(lastBuyTime, Calendar.MILLISECOND);
                        if (0 < delay && delay < LIFE_TIME) {
                            try {
                                Thread.sleep(delay);
                            } catch (final InterruptedException e) {
                                e.printStackTrace();
                            }
                        } else {
                            break;
                        }
                    }
                }
            }

            if (transactions.getWorkCount() < MgContext.MAX_WORK_PER_DAY) {
                final AWork work = new AWork(this);
                for (int count = 0; count < 5; ++count) {
                    final WorkResult workResult = work.perform();
                    if (workResult.isSuccessful()) {
                        break;
                    }
                    try {
                        Thread.sleep(100 * 1000);
                    } catch (final InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        }

        if (PREPARE_FIGHT_START_TIME.compareTo(serverTime) <= 0) {
            if (transactions.getWineCount() <= 0) {
                final ABuyGoods buyWine = new ABuyGoods(this);
                buyWine.setGoods(new Wine());
                buyWine.perform();
                getDriver().fetchPlayerContext(getContext());
            }
            if (transactions.getFoodCount() <= 0) {
                final ABuyGoods buyCuisine = new ABuyGoods(this);
                buyCuisine.setGoods(new Cuisine(Quality.HIGH));
                buyCuisine.perform();
                getDriver().fetchPlayerContext(getContext());
            }
        }

        if (FIGHT_START_TIME.compareTo(serverTime) <= 0 || 25 <= getContext().getEnergy()) {
            final boolean fightsCompleted = transactions.getFightCount() >= MgContext.MAX_FIGHTS_PER_DAY;
            if (!fightsCompleted) {
                final AReferralFight fight = new AReferralFight(this);
                fight.perform();
            }
        }

        if (PREPARE_FIGHT_START_TIME.compareTo(serverTime) <= 0) {
            if (transactions.getNewspaperCount() < MgContext.MAX_NEWSPAPRES_PER_DAY) {
                final ABuyNewspapers buyNewspapers = new ABuyNewspapers(this);
                buyNewspapers.setPapersLeftToBuy(MgContext.MAX_NEWSPAPRES_PER_DAY - transactions.getNewspaperCount());
                buyNewspapers.perform();
            }
        }

    }

}
