package ess.mg.agents.grtrader;

import ess.mg.actions.TradeResult;
import ess.mg.agents.Agent;
import ess.mg.agents.sampler.RecordLogger;

public class GoldRonTrader extends Agent {

    public static void main(final String... args) throws InterruptedException {
        new GoldRonTrader().start();
    }

    private static final long FREQUENCY = 10 * 1000;

    @Override
    public void run() {
        setRepeatAfter(0);
        new ALogin(this, 20 * 1000).perform();
        final ATrade tradeAction = new ATrade(this, 30 * 1000);
        while (true) {
            final TradeResult result = tradeAction.perform();
            new RecordLogger().onFetchEuroGoldExchangeRate(String.valueOf(result.getExchangeRate()));
            try {
                Thread.sleep(FREQUENCY);
            } catch (final InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
