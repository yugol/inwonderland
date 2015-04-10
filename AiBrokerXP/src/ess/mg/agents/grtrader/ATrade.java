package ess.mg.agents.grtrader;

import ess.mg.actions.Action;
import ess.mg.actions.TradeResult;
import ess.mg.agents.Agent;

public class ATrade extends Action<TradeResult> {

    private static final double BUY_GOLD_TREASHOLD  = 10.35;
    private static final double SELL_GOLD_TREASHOLD = 11.15;

    private static final double MIN_RON_AMOUNT      = 25;
    private static final double MIN_GOLD_AMOUNT     = 0;
    private static final int    MULTIPLIER          = 1000;

    public ATrade(final Agent performer) {
        super(performer);
    }

    public ATrade(final Agent performer, final int timeout) {
        super(performer, timeout);
    }

    @Override
    protected TradeResult execute() {
        final TradeResult result = new TradeResult();
        final Double goldPrice = getAgent().getDriver().fetchGoldRonExchangeRate();
        result.setExchangeRate(goldPrice);
        getAgent().getDriver().fetchGlobalContext(getAgent().getGlobal());
        final double actualGoldPrice = goldPrice * 1.03;
        if (goldPrice <= BUY_GOLD_TREASHOLD) {
            final int goldAmount = (int) ((getAgent().getGlobal().getRonAmount() - MIN_RON_AMOUNT) / actualGoldPrice);
            if (goldAmount > 0) {
                result.setSuccessful(getAgent().getDriver().buyGoldFromRon(goldAmount * MULTIPLIER));
            }
        }
        if (goldPrice >= SELL_GOLD_TREASHOLD) {
            final int goldAmount = (int) ((getAgent().getGlobal().getGoldAmount() - MIN_GOLD_AMOUNT) / actualGoldPrice);
            if (goldAmount > 0) {
                result.setSuccessful(getAgent().getDriver().sellGoldToRon(goldAmount * MULTIPLIER));
            }
        }
        return result;
    }

}
