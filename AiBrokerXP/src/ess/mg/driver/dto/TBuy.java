package ess.mg.driver.dto;

import aibroker.util.Moment;
import ess.common.Price;

public abstract class TBuy extends Transaction {

    private final Price  price;
    private final double amount;

    protected TBuy(final boolean income, final Moment moment, final Price price, final double amount) {
        super(income, moment);
        this.price = price;
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    public Price getPrice() {
        return price;
    }

}
