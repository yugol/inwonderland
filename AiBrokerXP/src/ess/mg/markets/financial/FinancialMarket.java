package ess.mg.markets.financial;

import ess.Price;
import ess.mg.MG;

public class FinancialMarket {

    public static ExchangeResult buyGoldWithRon(final int goldAmount, final double exchangeRate) {
        final double goldPrice = goldAmount * exchangeRate;
        final double goldFee = goldAmount * MG.EXCHANGE_FEE;
        return new ExchangeResult(0, goldAmount - goldFee, goldPrice);
    }

    public static Price estimateExchangeRonToGold(final double ronAmount, final double exchangeRate) {
        final double goldAmount = ronAmount / actualGoldPrice(exchangeRate);
        return Price.gold(goldAmount);
    }

    public static ExchangeResult simulateExchangeRonToGold(final double ronAmount, final double exchangeRate) {
        final double goldPrice = actualGoldPrice(exchangeRate);
        final double goldAmount = Math.floor(ronAmount / goldPrice);
        final double remainingRonAmount = ronAmount - goldAmount * goldPrice;
        return new ExchangeResult(0, goldAmount, remainingRonAmount);
    }

    private static double actualGoldPrice(final double exchangeRate) {
        final double exchangeFee = exchangeRate * MG.EXCHANGE_FEE;
        return exchangeRate + exchangeFee;
    }

}
