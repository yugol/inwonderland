package ess.mg.agents.operator;

import ess.mg.MgContext;
import ess.mg.actions.Action;
import ess.mg.actions.TradeResult;
import ess.mg.agents.Agent;

public class ATradeGold extends Action<TradeResult> {

    private double  buyGoldPrice      = 0;
    private double  sellGoldPrice     = Double.MAX_VALUE;
    private double  minumumRonAmount  = 0;
    private double  minumumGoldAmount = 0;
    private boolean enabled           = false;

    public ATradeGold(final Agent performer) {
        super(performer);
    }

    public double getBuyGoldPrice() {
        return buyGoldPrice;
    }

    public double getMinumumGoldAmount() {
        return minumumGoldAmount;
    }

    public double getMinumumRonAmount() {
        return minumumRonAmount;
    }

    public double getSellGoldPrice() {
        return sellGoldPrice;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setBuyGoldPrice(final double buyGoldPrice) {
        this.buyGoldPrice = buyGoldPrice;
    }

    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }

    public void setMinumumGoldAmount(final double minumumGoldAmount) {
        this.minumumGoldAmount = minumumGoldAmount;
    }

    public void setMinumumRonAmount(final double minumumRonAmount) {
        this.minumumRonAmount = minumumRonAmount;
    }

    public void setSellGoldPrice(final double sellGoldPrice) {
        this.sellGoldPrice = sellGoldPrice;
    }

    @Override
    protected TradeResult execute() {
        final TradeResult result = new TradeResult();
        final MgContext state = getAgent().getContext();

        final Double goldPrice = getAgent().getDriver().fetchGoldRonExchangeRate();
        state.setGoldRonExchangeRate(goldPrice);

        if (goldPrice < getBuyGoldPrice()) {
            int goldAmount = (int) ((state.getRonAmount() - getMinumumRonAmount()) / goldPrice);
            if (goldAmount > 0) {
                if (!isEnabled()) {
                    goldAmount *= 10;
                }
                result.setSuccessful(getAgent().getDriver().buyGoldFromRon(goldAmount));
            }
        }

        if (goldPrice > getSellGoldPrice()) {
            int goldAmount = (int) (state.getGoldAmount() - getMinumumRonAmount());
            if (goldAmount > 0) {
                if (!isEnabled()) {
                    goldAmount *= 10;
                }
                result.setSuccessful(getAgent().getDriver().sellGoldToRon(goldAmount));
            }
        }

        return result;
    }

}
