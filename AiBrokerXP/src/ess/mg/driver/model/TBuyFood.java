package ess.mg.driver.model;

import aibroker.util.Moment;
import ess.Price;

public class TBuyFood extends TBuy {

    protected TBuyFood(final boolean income, final Moment moment, final Price price, final double amount) {
        super(income, moment, price, amount);
    }

}