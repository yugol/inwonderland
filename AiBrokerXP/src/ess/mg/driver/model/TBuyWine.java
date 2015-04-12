package ess.mg.driver.model;

import aibroker.util.Moment;
import ess.common.Price;

public class TBuyWine extends TBuy {

    public TBuyWine(final boolean income, final Moment moment, final Price price, final double amount) {
        super(income, moment, price, amount);
    }

}
