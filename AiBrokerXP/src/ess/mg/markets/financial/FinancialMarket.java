package ess.mg.markets.financial;

import ess.mg.MG;

public class FinancialMarket {

    public static ExchangeResult buyGoldWithRon(final int goldAmount, final double exchangeRate) {
        final double goldPrice = goldAmount * exchangeRate;
        final double goldFee = goldAmount * MG.EXCHANGE_FEE;
        return new ExchangeResult(0, goldAmount - goldFee, goldPrice);
    }

    public static ExchangeResult estimateGoldToRon(final double goldAmount, final double exchangeRate) {
        final double actualExchangeRate = exchangeRate / (1 - MG.EXCHANGE_FEE);
        return new ExchangeResult(0, goldAmount, goldAmount * actualExchangeRate);
    }

}
