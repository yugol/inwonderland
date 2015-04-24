package ess.mg.agents.operator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import aibroker.util.Moment;
import ess.mg.MgContext;
import ess.mg.agents.MgAgent;
import ess.mg.agents.actions.ABuyGoods;
import ess.mg.agents.actions.ALogin;
import ess.mg.agents.dto.ReferralFightResult;
import ess.mg.agents.dto.WorkResult;
import ess.mg.driver.dto.Transactions;
import ess.mg.goods.Quality;
import ess.mg.goods.food.Cuisine;
import ess.mg.goods.food.Wine;

public class MgOperator extends MgAgent {

    public static void main(final String... args) {
        final Moment nowTime = Moment.getNow().getTimeMoment();
        final boolean active = START_TIME.compareTo(nowTime) <= 0 && nowTime.compareTo(STOP_TIME) < 0;
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

    private static final int    LIFE_TIME       = 12 * 60 * 1000;

    private static final Moment START_TIME      = Moment.fromIso("20:15:00");
    private static final Moment WORK_START_TIME = Moment.fromIso("20:30:00");
    private static final Moment STOP_TIME       = Moment.fromIso("23:30:00");

    @Override
    public void run() {

        final ALogin login = new ALogin(this);
        login.setEpoch(Moment.fromIso(Moment.getNow().toIsoDate(), START_TIME.toIsoTime()));
        login.setReadTransactions(true);
        login.perform();

        final Moment serverTime = getContext().getServerTime();
        final Transactions transactions = getContext().getTransactions();

        if (transactions.getWineCount() <= 0) {
            final ABuyGoods buyWine = new ABuyGoods(this);
            buyWine.setGoods(new Wine());
            buyWine.perform();
        }

        if (transactions.getFoodCount() <= 0) {
            final ABuyGoods buyCuisine = new ABuyGoods(this);
            buyCuisine.setGoods(new Cuisine(Quality.HIGH));
            buyCuisine.perform();
        }

        getDriver().fetchPlayerContext(getContext());

        if (WORK_START_TIME.compareTo(serverTime) <= 0 || 25 <= getContext().getEnergy()) {
            if (transactions.getFightCount() < MgContext.MAX_FIGHTS_PER_DAY) {
                final AReferralFight fight = new AReferralFight(this);
                final ReferralFightResult result = fight.perform();
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

        if (transactions.getNewspaperCount() < MgContext.MAX_NEWSPAPRES_PER_DAY) {
            final ABuyNewspapers buyNewspapers = new ABuyNewspapers(this);
            buyNewspapers.setPapersLeftToBuy(MgContext.MAX_NEWSPAPRES_PER_DAY - transactions.getNewspaperCount());
            buyNewspapers.perform();
        }

    }

}
