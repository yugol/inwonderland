package ess.mg.driver.model;

import aibroker.util.Moment;
import ess.common.Price;

public class TBuyMilk extends TBuy {

    protected TBuyMilk(final boolean income, final Moment moment, final Price price, final double amount) {
        super(income, moment, price, amount);
    }

}
