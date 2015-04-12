package ess.common;

import org.slf4j.Logger;
import aibroker.util.Moment;
import ess.common.driver.model.Shares;

public abstract class EssLogger {

    public static final String SEPARATOR = ";";

    private final Logger       logger;
    private final Moment       moment    = Moment.getNow();

    protected EssLogger(final Logger logger) {
        this.logger = logger;
    }

    public void log(final String message) {
        logger.info(message);
    }

    public abstract void logActiveUsersCount(final Integer value);

    public abstract void logEuroBalance(final Double value);

    public abstract void logEuroGoldRate(final Double value);

    public abstract void logGoldBalance(final Double value);

    public abstract void logGoldRonRate(final Double value);

    public abstract void logSharePrice(final Double value);

    public void logShares(final Shares shares) {
        logEuroBalance(shares.getEurAmount());
        logGoldBalance(shares.getGoldAmount());
        logSharePrice(shares.getSharePrice());
    }

    protected void log(final String series, final Object message) {
        log(moment.toIsoDatetime() + SEPARATOR + series + SEPARATOR + String.valueOf(message));
    }

}
