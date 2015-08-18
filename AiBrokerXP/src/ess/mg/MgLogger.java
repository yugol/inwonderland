package ess.mg;

import aibroker.Context;
import ess.common.EssLogger;

public class MgLogger extends EssLogger {

    public static final String ACTIVE_USERS = "MG_USERS";
    public static final String EURO_BALANCE = "MG_EURO_BALANCE";
    public static final String GOLD_BALANCE = "MG_GOLD_BALANCE";
    public static final String SHARE_PRICE = "MG_SHARE_PRICE";
    public static final String EURO_GOLD_RATE = "MG_EURO_GOLD";
    public static final String GOLD_RON_RATE = "MG_GOLD_RON";
    public static final String WORK_WAGE = "MG_WORK_WAGE";
    public static final String RON_AMOUNT = "MG_USER_RON";
    public static final String GOLD_AMOUNT = "MG_USER_GOLD";
    public static final String EUR_AMOUNT = "MG_USER_EUR";

    public MgLogger() {
        super(Context.getLogger(MgLogger.class));
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

    public void logWorkWage(final Double value) {
        log(WORK_WAGE, value);
    }

    public void logRonAmount(final Double value) {
        log(RON_AMOUNT, value);
    }

    public void logGoldAmount(final Double value) {
        log(GOLD_AMOUNT, value);
    }

    public void logEuroAmount(final Double value) {
        log(EUR_AMOUNT, value);
    }
}
