package ess.gt.agents.operator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import ess.gt.GtContext;
import ess.gt.agents.GtAgent;

public class Operator extends GtAgent {

    public static void main(final String... args) {
        final Operator operator = new Operator();
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

    private static final int     LIFE_TIME           = 5 * 60 * 1000;

    private static final boolean ENABLE_GOLD_TRADING = true;
    private static final int     MAX_TRADED_AMOUNT   = 1;
    private static final double  BUY_GOLD_PRICE      = 420;
    private static final double  SELL_GOLD_PRICE     = 590;
    private static final double  MIN_RON_AMOUNT      = 5000;
    private static final double  MIN_GOLD_AMOUNT     = 1;

    @Override
    public void run() {
        final GtContext context = getDriver().login();
        getLogger().logActiveUsersCount(context.getActiveUsersCount());

        getDriver().fetchShares(context);
        getLogger().logShares(context.getShares());

        getDriver().fetchPlayerContext(context);

        getDriver().fetchEuroGoldExchangeRate(context);
        getLogger().logEuroGoldRate(context.getEuroGoldExchangeRate());

        getDriver().fetchGoldRonExchangeRate(context);
        getLogger().logGoldRonRate(context.getGoldRonExchangeRate());

        // trade GOLD

        final Double goldPrice = context.getGoldRonExchangeRate();
        if (goldPrice < BUY_GOLD_PRICE) {
            int goldAmount = (int) ((context.getRonAmount() - MIN_RON_AMOUNT) / goldPrice);
            if (goldAmount > MAX_TRADED_AMOUNT) {
                goldAmount = MAX_TRADED_AMOUNT;
            }
            if (goldAmount > 0) {
                if (!ENABLE_GOLD_TRADING) {
                    goldAmount *= 1000;
                }
                getDriver().buyGoldFromRon(goldAmount);
            }
        }
        if (goldPrice > SELL_GOLD_PRICE) {
            int goldAmount = (int) (context.getGoldAmount() - MIN_GOLD_AMOUNT);
            if (goldAmount > MAX_TRADED_AMOUNT) {
                goldAmount = MAX_TRADED_AMOUNT;
            }
            if (goldAmount > 0) {
                if (!ENABLE_GOLD_TRADING) {
                    goldAmount *= 1000;
                }
                getDriver().sellGoldToRon(goldAmount);
            }
        }

    }

}
