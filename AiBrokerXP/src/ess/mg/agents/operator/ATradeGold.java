package ess.mg.agents.operator;

import ess.common.actions.Action;
import ess.common.actions.TradeResult;
import ess.mg.MgContext;
import ess.mg.agents.MgAgent;

public class ATradeGold extends Action<MgAgent, TradeResult> {

    private double  buyGoldPrice  = 0;
    private double  sellGoldPrice = Double.MAX_VALUE;
    private double  minRonAmount  = 0;
    private double  minGoldAmount = 0;
    private boolean enabled       = false;
    private double  maxTradedGoldAmount;

    public ATradeGold(final MgAgent performer) {
        super(performer);
    }

    public double getBuyGoldPrice() {
        return buyGoldPrice;
    }

    public double getMaxTradedGoldAmount() {
        return maxTradedGoldAmount;
    }

    public double getMinGoldAmount() {
        return minGoldAmount;
    }

    public double getMinRonAmount() {
        return minRonAmount;
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

    public void setMaxTradedGoldAmount(final double maxTradedGoldAmount) {
        this.maxTradedGoldAmount = maxTradedGoldAmount;
    }

    public void setMinGoldStock(final double minumumGoldAmount) {
        minGoldAmount = minumumGoldAmount;
    }

    public void setMinRonStock(final double minumumRonAmount) {
        minRonAmount = minumumRonAmount;
    }

    public void setSellGoldPrice(final double sellGoldPrice) {
        this.sellGoldPrice = sellGoldPrice;
    }

    @Override
    protected TradeResult execute() {
        final TradeResult result = new TradeResult();
        final MgContext context = getAgent().getContext();

        getAgent().getDriver().fetchGoldRonExchangeRate(context);
        getAgent().getLogger().logGoldRonRate(context.getGoldRonExchangeRate());

        final Double goldPrice = context.getGoldRonExchangeRate();
        if (goldPrice < getBuyGoldPrice()) {
            int goldAmount = (int) ((context.getRonAmount() - getMinRonAmount()) / goldPrice);
            if (goldAmount > 0) {
                if (goldAmount > getMaxTradedGoldAmount()) {
                    goldAmount = (int) getMaxTradedGoldAmount();
                }
                if (!isEnabled()) {
                    goldAmount *= 100;
                }
                result.setSuccessful(getAgent().getDriver().buyGoldFromRon(goldAmount));
            }
        }
        if (goldPrice > getSellGoldPrice()) {
            int goldAmount = (int) (context.getGoldAmount() - getMinGoldAmount());
            if (goldAmount > 0) {
                if (goldAmount > getMaxTradedGoldAmount()) {
                    goldAmount = (int) getMaxTradedGoldAmount();
                }
                if (!isEnabled()) {
                    goldAmount *= 10;
                }
                result.setSuccessful(getAgent().getDriver().sellGoldToRon(goldAmount));
            }
        }

        return result;
    }
}
