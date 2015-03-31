package ess.mg.markets.financial;

import ess.Price;

public class ExchangeResult {

    private final Price euro;
    private final Price gold;
    private final Price ron;

    public ExchangeResult(final double euroAmount, final double goldAmount, final double ronAmount) {
        euro = Price.euro(euroAmount);
        gold = Price.gold(goldAmount);
        ron = Price.ron(ronAmount);
    }

    public Price getEuro() {
        return euro;
    }

    public Price getGold() {
        return gold;
    }

    public Price getRon() {
        return ron;
    }

}
