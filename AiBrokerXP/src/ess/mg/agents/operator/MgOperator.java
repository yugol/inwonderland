package ess.mg.agents.operator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import javax.swing.Timer;
import aibroker.util.Moment;
import ess.mg.MgContext;
import ess.mg.agents.MgAgent;
import ess.mg.agents.dto.FightResult;
import ess.mg.agents.dto.PurchaseResult;
import ess.mg.agents.dto.WorkResult;
import ess.mg.driver.dto.Transactions;
import ess.mg.goods.Quality;
import ess.mg.goods.food.Cuisine;
import ess.mg.goods.food.Dairy;
import ess.mg.goods.food.Wine;

public class MgOperator extends MgAgent {

    public static void main(final String... args) {
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

    private static final int     LIFE_TIME           = 12 * 60 * 1000;

    private static final double  BUY_GOLD_PRICE      = 10.1000;
    private static final double  SELL_GOLD_PRICE     = 11.1000;
    private static final double  MIN_RON_AMOUNT      = 10;
    private static final double  MIN_GOLD_AMOUNT     = 1;

    private static final boolean MANAGE_COMPANIES    = false;
    private static final double  MAX_HOURLY_WAGE     = 3;

    private static final Moment  ACTIVITY_TIME       = Moment.fromIso("03:00:00");
    private static final Moment  PRE_ENERGISE_START  = ACTIVITY_TIME.newAdd(Calendar.MINUTE, 1);
    private static final Moment  PRE_ENERGISE_STOP   = PRE_ENERGISE_START.newAdd(Calendar.MINUTE, 13);
    private static final Moment  POST_ENERGISE_START = ACTIVITY_TIME.newAdd(Calendar.MINUTE, 16);
    private static final Moment  POST_ENERGISE_STOP  = POST_ENERGISE_START.newAdd(Calendar.MINUTE, 20);
    private static final Moment  ACTIVITY_START      = ACTIVITY_TIME.newAdd(Calendar.MINUTE, 30);
    private static final Moment  ACTIVITY_STOP       = ACTIVITY_TIME.newAdd(Calendar.MINUTE, 240);

    @Override
    public void run() {
        final Moment nowTime = Moment.fromIso(Moment.getNow().toIsoTime());

        final ALogin login = new ALogin(this);
        login.setEpoch(Moment.fromIso(Moment.getNow().toIsoDate(), ACTIVITY_TIME.toIsoTime()));
        login.setReadTransactions(ACTIVITY_TIME.compareTo(nowTime) <= 0 && nowTime.compareTo(ACTIVITY_STOP) < 0);
        login.perform();

        final ATradeGold tradeGold = new ATradeGold(this);
        tradeGold.setBuyGoldPrice(BUY_GOLD_PRICE);
        tradeGold.setSellGoldPrice(SELL_GOLD_PRICE);
        tradeGold.setMinumumRonAmount(MIN_RON_AMOUNT);
        tradeGold.setMinumumGoldAmount(MIN_GOLD_AMOUNT);
        tradeGold.setEnabled(true);
        tradeGold.perform();

        final Moment serverTime = getContext().getServerTime();
        final Transactions transactions = getContext().getTransactions();

        if (PRE_ENERGISE_START.compareTo(serverTime) <= 0 && serverTime.compareTo(PRE_ENERGISE_STOP) < 0) {
            if (transactions.getFoodCount() <= 0) {
                final ABuyGoods buyCuisine = new ABuyGoods(this);
                buyCuisine.setGoods(new Cuisine(Quality.HIGH));
                buyCuisine.perform();
            }
        }

        if (POST_ENERGISE_START.compareTo(serverTime) <= 0 && serverTime.compareTo(POST_ENERGISE_STOP) < 0) {
            if (transactions.getMilkCount() <= 0) {
                final ABuyGoods buyMilk = new ABuyGoods(this);
                buyMilk.setGoods(new Dairy(Quality.HIGH));
                final PurchaseResult purchaseResult = buyMilk.perform();
                if (!purchaseResult.isSuccessful()) {
                    setRepeatAfter(10 * 1000);
                }
            }
            if (transactions.getWineCount() <= 0) {
                final ABuyGoods buyWine = new ABuyGoods(this);
                buyWine.setGoods(new Wine());
                buyWine.perform();
            }
            if (transactions.getNewspaperCount() < MgContext.MAX_NEWSPAPRES_PER_DAY) {
                final ABuyNewspapers buyNewspapers = new ABuyNewspapers(this);
                buyNewspapers.setPapersLeftToBuy(MgContext.MAX_NEWSPAPRES_PER_DAY - transactions.getNewspaperCount());
                buyNewspapers.perform();
            }
        }

        if (MANAGE_COMPANIES) {
            final AManageCompany manageCompany = new AManageCompany(this);
            manageCompany.setCompanyUrl("Vacca-Villa");
            manageCompany.setMaxHourlyWage(MAX_HOURLY_WAGE);
            manageCompany.perform();
        }

        if (ACTIVITY_START.compareTo(serverTime) <= 0 && serverTime.compareTo(ACTIVITY_STOP) < 0) {
            if (transactions.getFightCount() < MgContext.MAX_FIGHTS_PER_DAY) {
                final AFight fight = new AFight(this);
                final FightResult result = fight.perform();
                if (!result.isMaxFightCountReached()) { return; }
            }
            if (transactions.getWorkCount() < MgContext.MAX_WORK_PER_DAY) {
                final AWork work = new AWork(this);
                final WorkResult workResult = work.perform();
                if (!workResult.isSuccessful()) {
                    setRepeatAfter(30 * 1000);
                }
            }
        }
    }

}