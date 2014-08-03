package aibroker.model.cloud;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import aibroker.model.Ohlc;
import aibroker.model.Quotes;
import aibroker.model.cloud.sources.tranzactiibursiere.BvbRegsDailyNormDownloader;
import aibroker.model.cloud.sources.tranzactiibursiere.BvbRegsDailyOrigDownloader;
import aibroker.model.cloud.sources.tranzactiibursiere.SibexFuturesTicksDownloader;
import aibroker.model.cloud.sources.yahoo.YahooDailyDownloader;
import aibroker.model.domains.Updater;
import aibroker.util.Moment;
import static aibroker.util.StringUtil.isNullOrBlank;

public abstract class CloudDataSource extends DataSource {

    private static class QuotesStatus {

        public final Quotes      quotes = new Quotes();
        public final Set<Moment> dates  = new HashSet<Moment>();

    }

    private static final Logger logger = LoggerFactory.getLogger(CloudDataSource.class);

    public static CloudDataSource getDataSource(final Updater updater) {
        switch (updater) {
            case BVB_REG_DAILY_BASE:
                return BvbRegsDailyOrigDownloader.getInstance();
            case BVB_REG_DAILY_NORM:
                return BvbRegsDailyNormDownloader.getInstance();
            case SIBEX_FUT_TICK:
                return SibexFuturesTicksDownloader.getInstance();
            case YAHOO_DAILY:
                return YahooDailyDownloader.getInstance();
            default:
                return null;
        }
    }

    protected static String joinName(final String symbol, final String settlement) {
        final StringBuilder name = new StringBuilder(symbol.trim().toUpperCase());
        if (!isNullOrBlank(settlement)) {
            name.append("-").append(settlement.trim().toUpperCase());
        }
        return name.toString();
    }

    private final Map<String, QuotesStatus> quotesCash      = new HashMap<String, QuotesStatus>();
    public final Set<Moment>                downloadedDates = new HashSet<Moment>();

    public List<Ohlc> getQuotes(final String symbol, final String settlement, final Moment date) throws Exception {
        final Moment firstDate = date.getBeginningOfDay();
        final Moment lastDate = date.getEndOfDay();
        final String name = joinName(symbol, settlement);
        QuotesStatus status = quotesCash.get(name);
        if (status == null) {
            status = new QuotesStatus();
            quotesCash.put(name, status);
        }
        if (!downloadedDates.contains(firstDate) && !status.dates.contains(firstDate)) {
            logger.debug("Downloading quotes for " + name + " on " + firstDate.toIsoDate());
            downloadQuotesFor(symbol, settlement, firstDate);
        }
        return status.quotes.slice(firstDate, lastDate);
    }

    protected void addDate(Moment date) {
        date = date.getBeginningOfDay();
        downloadedDates.add(date);
    }

    protected void addDate(final String symbol, final String settlement, final Moment date) {
        final QuotesStatus status = quotesCash.get(joinName(symbol, settlement));
        status.dates.add(date.getBeginningOfDay());
    }

    protected void addQuote(final String symbol, final String settlement, final Ohlc ohlc) {
        final String name = joinName(symbol, settlement);
        QuotesStatus status = quotesCash.get(name);
        if (status == null) {
            status = new QuotesStatus();
            quotesCash.put(name, status);
        }
        status.quotes.add(ohlc);
    }

    protected abstract void downloadQuotesFor(String symbol, String settlement, Moment date) throws Exception;

    protected void sortQuotes() {
        for (final QuotesStatus status : quotesCash.values()) {
            status.quotes.sort();
        }
    }

    protected void sortQuotes(final String symbol, final String settlement) {
        final QuotesStatus status = quotesCash.get(joinName(symbol, settlement));
        status.quotes.sort();
    }

}
