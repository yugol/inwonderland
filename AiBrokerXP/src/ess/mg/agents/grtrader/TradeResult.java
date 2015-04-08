package ess.mg.agents.grtrader;

import ess.mg.agents.ActionResult;

public class TradeResult extends ActionResult {

    private double exchangeRate;

    public double getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(final double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

}
