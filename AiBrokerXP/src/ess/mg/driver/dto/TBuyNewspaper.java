package ess.mg.driver.dto;

import aibroker.util.Moment;
import ess.common.Price;

public class TBuyNewspaper extends TBuy {

    public TBuyNewspaper(final boolean income, final Moment moment, final Price price, final double amount) {
        super(income, moment, price, amount);
    }

}