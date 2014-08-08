package aibroker.engines.markets.sibex;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import aibroker.Context;
import aibroker.engines.markets.Market;
import aibroker.util.Moment;
import aibroker.util.WebUtil;

public class SibexFuturesMarket extends Market implements Runnable {

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
                    initializeRecords();
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

    private void initializeRecords() throws Exception {
        final File csvFile = new File(Context.getSibexLogFilePath());
        if (csvFile.exists()) {
            RecordReader.readRecords(liveMarket, csvFile);
        }
        update();
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
            if (record.getVolume() > 0) {
                callNewTransaction(record.toTransaction());
            }
        }
        liveMarket.put(record.getId(), record);
    }

    private void update() throws Exception {
        final String snapshot = WebUtil.getPageHtml(SIBEX_FUTURES_URL);
        int index = snapshot.indexOf("newTemplatehead");
        while ((index = snapshot.indexOf("<tr id=\"", index)) >= 0) {
            final Record record = RecordReader.readRecord(snapshot, index);
            merge(record);
            index++;
        }
    }

}
