package ess.gt;

import aibroker.Context;
import ess.common.EssLogger;

public class GtLogger extends EssLogger {

    public static final String ACTIVE_USERS   = "GT_USERS";
    public static final String EURO_BALANCE   = "GT_EURO_BALANCE";
    public static final String GOLD_BALANCE   = "GT_GOLD_BALANCE";
    public static final String SHARE_PRICE    = "GT_SHARE_PRICE";
    public static final String EURO_GOLD_RATE = "GT_EURO_GOLD";
    public static final String GOLD_RON_RATE  = "GT_GOLD_RON";

    public GtLogger() {
        super(Context.getLogger(GtLogger.class));
    }

    @Override
    public void logActiveUsersCount(final Integer value) {
        log(ACTIVE_USERS, value);
    }

    @Override
    public void logEuroBalance(final Double value) {
        log(EURO_BALANCE, value);
    }

    @Override
    public void logEuroGoldRate(final Double value) {
        log(EURO_GOLD_RATE, value);
    }

    @Override
    public void logGoldBalance(final Double value) {
        log(GOLD_BALANCE, value);
    }

    @Override
    public void logGoldRonRate(final Double value) {
        log(GOLD_RON_RATE, value);
    }

    @Override
    public void logSharePrice(final Double value) {
        log(SHARE_PRICE, value);
    }

}
