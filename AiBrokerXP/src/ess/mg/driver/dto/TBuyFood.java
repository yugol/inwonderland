package ess.mg.driver.dto;

import aibroker.util.Moment;
import ess.common.Price;

public class TBuyFood extends TBuy {

    protected TBuyFood(final boolean income, final Moment moment, final Price price, final double amount) {
        super(income, moment, price, amount);
    }

}
