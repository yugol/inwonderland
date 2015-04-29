package ess.mg.agents.trader;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import ess.mg.MgContext;
import ess.mg.agents.MgAgent;

public class MgTrader extends MgAgent {

    public static void main(final String... args) {
        final MgTrader trader = new MgTrader();
        final Timer timer = new Timer(LIFE_TIME, new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent e) {
                trader.stop();
                System.exit(1);
            }

        });
        timer.setRepeats(false);
        timer.start();
        trader.start();
    }

    private static final int     LIFE_TIME              = 5 * 60 * 1000;

    private static final boolean TRADE_GOLD             = true;
    private static final double  BUY_GOLD_PRICE         = 10.1000;
    private static final double  SELL_GOLD_PRICE        = 11.1000;
    private static final double  MIN_RON_STOCK          = 25;
    private static final double  MIN_GOLD_STOCK         = 1;
    private static final double  MAX_TRADED_GOLD_AMOUNT = 100;

    @Override
    public void run() {
        final MgContext context = getDriver().login();
        setContext(context);

        getDriver().fetchShares(context);
        getLogger().logShares(context.getShares());

        getDriver().fetchEuroGoldExchangeRate(context);
        getLogger().logEuroGoldRate(context.getEuroGoldExchangeRate());

        getDriver().fetchPlayerContext(context);
        final ATradeGold tradeGold = new ATradeGold(this);
        tradeGold.setBuyGoldPrice(BUY_GOLD_PRICE);
        tradeGold.setSellGoldPrice(SELL_GOLD_PRICE);
        tradeGold.setMinRonStock(MIN_RON_STOCK);
        tradeGold.setMinGoldStock(MIN_GOLD_STOCK);
        tradeGold.setMaxTradedGoldAmount(MAX_TRADED_GOLD_AMOUNT);
        tradeGold.setEnabled(TRADE_GOLD);
        tradeGold.perform();
    }

}
