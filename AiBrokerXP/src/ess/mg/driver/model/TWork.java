package ess.mg.driver.model;

import aibroker.util.Moment;
import ess.common.Price;

public class TWork extends Transaction {

    private final Price wage;

    public TWork(final boolean income, final Moment moment, final Price wage) {
        super(income, moment);
        this.wage = wage;
    }

    public Price getWage() {
        return wage;
    }

}
