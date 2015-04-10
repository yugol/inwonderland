package ess.mg.agents.operator;

import ess.mg.MG;
import ess.mg.actions.Action;
import ess.mg.actions.TradeResult;
import ess.mg.agents.Agent;

public class ABuyGold extends Action<TradeResult> {

    private double  maximumPrice     = 0;
    private double  minumumRonAmount = 0;
    private boolean enabled          = false;

    public ABuyGold(final Agent performer) {
        super(performer);
    }

    public double getMaximumPrice() {
        return maximumPrice;
    }

    public double getMinumumRonAmount() {
        return minumumRonAmount;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }

    public void setMaximumPrice(final double maximumPrice) {
        this.maximumPrice = maximumPrice;
    }

    public void setMinumumRonAmount(final double minumumRonAmount) {
        this.minumumRonAmount = minumumRonAmount;
    }

    @Override
    protected TradeResult execute() {
        final TradeResult result = new TradeResult();
        final MG state = getAgent().getGlobal();

        final Double goldPrice = getAgent().getDriver().fetchGoldRonExchangeRate();
        state.setGoldRonExchangeRate(goldPrice);

        if (goldPrice < getMaximumPrice()) {
            final double actualGoldPrice = goldPrice * (1 + MG.EXCHANGE_FEE);
            int goldAmount = (int) ((state.getRonAmount() - getMinumumRonAmount()) / actualGoldPrice);
            if (goldAmount > 0) {
                if (!isEnabled()) {
                    goldAmount *= 10;
                }
                result.setSuccessful(getAgent().getDriver().buyGoldFromRon(goldAmount));
            }
        }
        return result;
    }

}
