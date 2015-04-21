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
import ess.mg.agents.dto.FightResult;
import ess.mg.agents.dto.WorkResult;
import ess.mg.driver.dto.Transactions;
import ess.mg.goods.Quality;
import ess.mg.goods.food.Cuisine;
import ess.mg.goods.food.Wine;

public class MgOperator extends MgAgent {

    public static void main(final String... args) {
        final Moment nowTime = Moment.fromIso(Moment.getNow().toIsoTime());
        if (MEAL_TIME_START.compareTo(nowTime) <= 0 && nowTime.compareTo(WORK_TIME_STOP) < 0) {
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

    private static final int    LIFE_TIME        = 12 * 60 * 1000;

    private static final Moment LABOUR_TIME      = Moment.fromIso("15:00:00");
    private static final Moment MEAL_TIME_START  = LABOUR_TIME.newAdd(Calendar.MINUTE, 1);
    private static final Moment MEAL_TIME_STOP   = MEAL_TIME_START.newAdd(Calendar.MINUTE, 13);
    private static final Moment WINE_TIME_START  = LABOUR_TIME.newAdd(Calendar.MINUTE, 16);
    private static final Moment WINE_TIME_STOP   = WINE_TIME_START.newAdd(Calendar.MINUTE, 20);
    private static final Moment PAPER_TIME_START = LABOUR_TIME.newAdd(Calendar.MINUTE, 16);
    private static final Moment PAPER_TIME_STOP  = PAPER_TIME_START.newAdd(Calendar.MINUTE, 40);
    private static final Moment WORK_TIME_START  = LABOUR_TIME.newAdd(Calendar.MINUTE, 30);
    private static final Moment WORK_TIME_STOP   = LABOUR_TIME.newAdd(Calendar.MINUTE, 240);

    @Override
    public void run() {

        final ALogin login = new ALogin(this);
        login.setEpoch(Moment.fromIso(Moment.getNow().toIsoDate(), LABOUR_TIME.toIsoTime()));
        login.setReadTransactions(true);
        login.perform();

        final Moment serverTime = getContext().getServerTime();
        final Transactions transactions = getContext().getTransactions();

        if (MEAL_TIME_START.compareTo(serverTime) <= 0 && serverTime.compareTo(MEAL_TIME_STOP) < 0) {
            if (transactions.getFoodCount() <= 0) {
                final ABuyGoods buyCuisine = new ABuyGoods(this);
                buyCuisine.setGoods(new Cuisine(Quality.HIGH));
                buyCuisine.perform();
            }
        }

        if (WINE_TIME_START.compareTo(serverTime) <= 0 && serverTime.compareTo(WINE_TIME_STOP) < 0) {
            if (transactions.getWineCount() <= 0) {
                final ABuyGoods buyWine = new ABuyGoods(this);
                buyWine.setGoods(new Wine());
                buyWine.perform();
            }
        }

        if (PAPER_TIME_START.compareTo(serverTime) <= 0 && serverTime.compareTo(PAPER_TIME_STOP) < 0) {
            if (transactions.getNewspaperCount() < MgContext.MAX_NEWSPAPRES_PER_DAY) {
                final ABuyNewspapers buyNewspapers = new ABuyNewspapers(this);
                buyNewspapers.setPapersLeftToBuy(MgContext.MAX_NEWSPAPRES_PER_DAY - transactions.getNewspaperCount());
                buyNewspapers.perform();
            }
        }

        if (WORK_TIME_START.compareTo(serverTime) <= 0 && serverTime.compareTo(WORK_TIME_STOP) < 0) {
            if (transactions.getFightCount() < MgContext.MAX_FIGHTS_PER_DAY) {
                final AFight fight = new AFight(this);
                final FightResult result = fight.perform();
                if (!result.isMaxFightCountReached()) { return; }
            }
            if (transactions.getWorkCount() < MgContext.MAX_WORK_PER_DAY) {
                final AWork work = new AWork(this);
                for (int count = 0; count < 10; ++count) {
                    final WorkResult workResult = work.perform();
                    if (workResult.isSuccessful()) {
                        break;
                    }
                    try {
                        Thread.sleep(50 * 1000);
                    } catch (final InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

}
