package aibroker.engines.markets.sibex;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import aibroker.Context;
import aibroker.engines.markets.Market;
import aibroker.util.Moment;
import aibroker.util.NumberUtil;
import aibroker.util.WebUtil;

public class SibexFuturesMarket extends Market implements Runnable {

    static Record readRecord(final String snapshot, final int index) {
        int beginIndex = index + 8;
        int endIndex = snapshot.indexOf("\">", beginIndex);
        final Record record = new Record(snapshot.substring(beginIndex, endIndex));

        // realtime market depth
        beginIndex = snapshot.indexOf("<td", beginIndex + 1);

        // Symbol
        beginIndex = snapshot.indexOf("<td", beginIndex + 1);
        beginIndex = snapshot.indexOf(">", beginIndex) + 1;
        endIndex = snapshot.indexOf("</td>", beginIndex);
        record.setSymbol(snapshot.substring(beginIndex, endIndex));

        // Expiry month
        beginIndex = snapshot.indexOf("<td", beginIndex + 1);
        beginIndex = snapshot.indexOf(">", beginIndex) + 1;
        endIndex = snapshot.indexOf("</td>", beginIndex);
        record.setExpiryMonth(snapshot.substring(beginIndex, endIndex));

        // Bid Price
        beginIndex = snapshot.indexOf("<td", beginIndex + 1);
        beginIndex = snapshot.indexOf(">", beginIndex) + 1;
        endIndex = snapshot.indexOf("</td>", beginIndex);
        record.setBidPrice(NumberUtil.parseFloat(snapshot.substring(beginIndex, endIndex)));

        // Ask Price
        beginIndex = snapshot.indexOf("<td", beginIndex + 1);
        beginIndex = snapshot.indexOf(">", beginIndex) + 1;
        endIndex = snapshot.indexOf("</td>", beginIndex);
        record.setAskPrice(NumberUtil.parseFloat(snapshot.substring(beginIndex, endIndex)));

        // Last Price
        beginIndex = snapshot.indexOf("<td", beginIndex + 1);
        beginIndex = snapshot.indexOf(">", beginIndex) + 1;
        endIndex = snapshot.indexOf("</td>", beginIndex);
        record.setLastPrice(NumberUtil.parseFloat(snapshot.substring(beginIndex, endIndex)));

        // Delta Day
        beginIndex = snapshot.indexOf("<td", beginIndex + 1);

        // Delta Day %
        beginIndex = snapshot.indexOf("<td", beginIndex + 1);

        // Volume
        beginIndex = snapshot.indexOf("<td", beginIndex + 1);
        beginIndex = snapshot.indexOf(">", beginIndex) + 1;
        endIndex = snapshot.indexOf("</td>", beginIndex);
        record.setVolume(NumberUtil.parseInt(snapshot.substring(beginIndex, endIndex)));

        // Trades
        beginIndex = snapshot.indexOf("<td", beginIndex + 1);
        beginIndex = snapshot.indexOf(">", beginIndex) + 1;
        endIndex = snapshot.indexOf("</td>", beginIndex);
        record.setTrades(NumberUtil.parseInt(snapshot.substring(beginIndex, endIndex)));

        // Open int
        beginIndex = snapshot.indexOf("<td", beginIndex + 1);
        beginIndex = snapshot.indexOf(">", beginIndex) + 1;
        endIndex = snapshot.indexOf("</td>", beginIndex);
        record.setOpenInt(NumberUtil.parseInt(snapshot.substring(beginIndex, endIndex)));

        // Delta Open Int Prev day
        beginIndex = snapshot.indexOf("<td", beginIndex + 1);

        // Open
        beginIndex = snapshot.indexOf("<td", beginIndex + 1);

        // High
        beginIndex = snapshot.indexOf("<td", beginIndex + 1);

        // Low
        beginIndex = snapshot.indexOf("<td", beginIndex + 1);

        // Expiry Date
        beginIndex = snapshot.indexOf("<td", beginIndex + 1);

        return record;
    }

    private static final Logger       logger             = LoggerFactory.getLogger(SibexFuturesMarket.class);
    public static final String        SIBEX_FUTURES_URL  = "http://www.sibex.ro/index.php?p=realMarket&s=1&l=en&sort=1";
    private static final long         PREPARATION_TIME   = 1000 * 60 * 15;                                              // 5 minutes

    private final long                preOpenTime;
    private final long                openTime;
    private final long                closeTime;
    private final long                preCloseTime;

    private boolean                   preOpenSignalSent  = false;
    private boolean                   preCloseSignalSent = false;
    private boolean                   opened             = false;

    private final Map<String, Record> liveMarket         = new HashMap<String, Record>();

    public SibexFuturesMarket() {
        openTime = Moment.fromIso(Moment.getNow().toIsoDate() + " " + Context.getSibexOpenTime()).getTimeInMillis();
        preOpenTime = openTime - PREPARATION_TIME;
        closeTime = Moment.fromIso(Moment.getNow().toIsoDate() + " " + Context.getSibexCloseTime()).getTimeInMillis() + PREPARATION_TIME;
        preCloseTime = closeTime - PREPARATION_TIME * 2;

        logger.info(" Prepare Open Signal: " + new Moment(preOpenTime).toIsoDatetime());
        logger.info("         Open Signal: " + new Moment(openTime).toIsoDatetime());
        logger.info("Prepare Close Signal: " + new Moment(preCloseTime).toIsoDatetime());
        logger.info("        Close Signal: " + new Moment(closeTime).toIsoDatetime());
    }

    @Override
    public boolean isClosed() {
        return !isOpened();
    }

    @Override
    public boolean isOpened() {
        return opened;
    }

    @Override
    public void run() {
        while (true) {
            try {
                final Moment now = Moment.getNow();
                final long nowMillis = now.getTimeInMillis();
                opened = openTime <= nowMillis && nowMillis < closeTime;
                if (!preOpenSignalSent && preOpenTime < nowMillis) {
                    logger.info("Prepare open Sibex futures");

                    callMarketPrepareOpen(now);
                    preOpenSignalSent = true;
                }
                if (!preCloseSignalSent && preCloseTime < nowMillis) {
                    logger.info("Prepare close Sibex futures");
                    callMarketPrepareClose(now);
                    preCloseSignalSent = true;
                }
                if (preOpenSignalSent) {
                    logger.info("Reading Sibex futures");
                    update();
                }
                if (closeTime < nowMillis) {
                    logger.info("Closing Sibex futures");
                    callMarketClosed(now);
                    break;
                }
                Thread.sleep(Context.getSibexPollInterval());
            } catch (final Exception e) {
                logger.error("", e);
            }
        }
    }

    @Override
    public void start() {
        new Thread(this).start();
    }

    private void merge(final Record record) {
        final Record prevRecord = liveMarket.get(record.getId());
        if (prevRecord != null) {
            if (prevRecord.getBidPrice() != record.getBidPrice() || prevRecord.getAskPrice() != record.getAskPrice()) {
                callBidAskChanged(record.toBidAsk());
            }
            if (prevRecord.getVolume() < record.getVolume()) {
                callNewTransaction(record.toTransaction(prevRecord));
            }
        } else {
            // callNewTransaction(record.toTransaction());
        }
        liveMarket.put(record.getId(), record);
    }

    private void update() throws Exception {
        final String snapshot = WebUtil.getPageHtml(SIBEX_FUTURES_URL);
        int index = snapshot.indexOf("newTemplatehead");
        while ((index = snapshot.indexOf("<tr id=\"", index)) >= 0) {
            final Record record = readRecord(snapshot, index);
            merge(record);
            index++;
        }
    }

}
