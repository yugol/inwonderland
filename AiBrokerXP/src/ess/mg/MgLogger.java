package ess.mg;

import aibroker.Context;
import ess.common.EssLogger;
import ess.mg.driver.MgWebDriverListener;

public class MgLogger extends EssLogger implements MgWebDriverListener {

    public static final String ACTIVE_USERS   = "MG_USERS";
    public static final String EURO_BALANCE   = "MG_EURO_BALANCE";
    public static final String GOLD_BALANCE   = "MG_GOLD_BALANCE";
    public static final String SHARE_PRICE    = "MG_SHARE_PRICE";
    public static final String EURO_GOLD_RATE = "MG_EURO_GOLD";
    public static final String GOLD_RON_RATE  = "MG_GOLD_RON";
    public static final String WORK_WAGE      = "MG_WORK_WAGE";

    private Integer            activeUsers;
    private Double             euroBalance;
    private Double             goldBalance;
    private Double             sharePrice;
    private Double             euroGoldExchangeRate;
    private Double             goldRonExchangeRate;
    private Double             workWage;

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

    @Override
    public void onFetchActiveUsers(final String activeUsers) {
        try {
            this.activeUsers = Integer.parseInt(activeUsers);
            log(ACTIVE_USERS, this.activeUsers);
        } catch (final Exception ex) {
            this.activeUsers = null;
        }
    }

    @Override
    public void onFetchEurAmount(final String eurAmount) {
        try {
            euroBalance = Double.parseDouble(eurAmount);
            log(EURO_BALANCE, euroBalance);
        } catch (final Exception ex) {
            euroBalance = null;
        }
    }

    @Override
    public void onFetchEuroGoldExchangeRate(final String rate) {
        try {
            euroGoldExchangeRate = Double.parseDouble(rate);
            log(EURO_GOLD_RATE, euroGoldExchangeRate);
        } catch (final Exception ex) {
            euroGoldExchangeRate = null;
        }
    }

    @Override
    public void onFetchGoldAmount(final String goldAmount) {
        try {
            goldBalance = Double.parseDouble(goldAmount);
            log(GOLD_BALANCE, goldBalance);
        } catch (final Exception ex) {
            goldBalance = null;
        }
    }

    @Override
    public void onFetchGoldRonExchangeRate(final String rate) {
        try {
            goldRonExchangeRate = Double.parseDouble(rate);
            log(GOLD_RON_RATE, goldRonExchangeRate);
        } catch (final Exception ex) {
            euroGoldExchangeRate = null;
        }
    }

    @Override
    public void onFetchSharePrice(final String sharePrice) {
        try {
            this.sharePrice = Double.parseDouble(sharePrice);
            log(SHARE_PRICE, this.sharePrice);
        } catch (final Exception ex) {
            this.sharePrice = null;
        }
    }

    @Override
    public void onFetchWorkWage(final String wage) {
        try {
            workWage = Double.parseDouble(wage);
            log(WORK_WAGE, workWage);
        } catch (final Exception ex) {
            euroBalance = null;
        }

    }

}
