package ess.mg.driver.model;

import aibroker.util.Moment;
import ess.Price;

public class TFight extends Transaction {

    private final Price prize;

    protected TFight(final boolean income, final Moment moment, final Price prize) {
        super(income, moment);
        this.prize = prize;
    }

    public Price getPrize() {
        return prize;
    }

}
