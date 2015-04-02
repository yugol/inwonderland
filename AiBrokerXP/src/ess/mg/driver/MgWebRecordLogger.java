package ess.mg.driver;

import org.slf4j.Logger;
import aibroker.Context;
import aibroker.util.Moment;

public class MgWebRecordLogger implements MgWebReaderListener {

    private static final Logger logger       = Context.getLogger(MgWebRecordLogger.class);

    public static final String  ACTIVE_USERS = "MG_USERS";
    public static final String  WORK_WAGE    = "MG_WORK_WAGE";
    public static final String  EURO_BALANCE = "MG_EURO_BALANCE";
    public static final String  GOLD_BALANCE = "MG_GOLD_BALANCE";
    public static final String  SHARE_PRICE  = "MG_SHARE_PRICE";
    public static final String  EURO_GOLD    = "MG_EURO_GOLD";
    public static final String  GOLD_RON     = "MG_GOLD_RON";

    private final Moment        moment       = Moment.getNow();
    private Integer             activeUsers;
    private Double              euroBalance;
    private Double              goldBalance;
    private Double              sharePrice;
    private Double              euroGoldExchangeRate;
    private Double              goldRonExchangeRate;
    private Double              workWage;

    @Override
    public void onFetchActiveUsers(final String activeUsers) {
        try {
            this.activeUsers = Integer.parseInt(activeUsers);
            logger.info(moment.toCompactIsoDatetime() + "," + ACTIVE_USERS + "," + this.activeUsers);
        } catch (final Exception ex) {
            this.activeUsers = null;
        }
    }

    @Override
    public void onFetchEurAmount(final String eurAmount) {
        try {
            euroBalance = Double.parseDouble(eurAmount);
            logger.info(moment.toCompactIsoDatetime() + "," + EURO_BALANCE + "," + euroBalance);
        } catch (final Exception ex) {
            euroBalance = null;
        }
    }

    @Override
    public void onFetchEuroGoldExchangeRate(final String rate) {
        try {
            euroGoldExchangeRate = Double.parseDouble(rate);
            logger.info(moment.toCompactIsoDatetime() + "," + EURO_GOLD + "," + euroGoldExchangeRate);
        } catch (final Exception ex) {
            euroGoldExchangeRate = null;
        }
    }

    @Override
    public void onFetchGoldAmount(final String goldAmount) {
        try {
            goldBalance = Double.parseDouble(goldAmount);
            logger.info(moment.toCompactIsoDatetime() + "," + GOLD_BALANCE + "," + goldBalance);
        } catch (final Exception ex) {
            goldBalance = null;
        }
    }

    @Override
    public void onFetchGoldRonExchangeRate(final String rate) {
        try {
            goldRonExchangeRate = Double.parseDouble(rate);
            logger.info(moment.toCompactIsoDatetime() + "," + GOLD_RON + "," + goldRonExchangeRate);
        } catch (final Exception ex) {
            euroGoldExchangeRate = null;
        }
    }

    @Override
    public void onFetchSharePrice(final String sharePrice) {
        try {
            this.sharePrice = Double.parseDouble(sharePrice);
            logger.info(moment.toCompactIsoDatetime() + "," + SHARE_PRICE + "," + this.sharePrice);
        } catch (final Exception ex) {
            this.sharePrice = null;
        }
    }

    @Override
    public void onFetchWorkWage(final String wage) {
        try {
            workWage = Double.parseDouble(wage);
            logger.info(moment.toCompactIsoDatetime() + "," + WORK_WAGE + "," + workWage);
        } catch (final Exception ex) {
            euroBalance = null;
        }

    }

}
