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

    private static final Logger       logger            = LoggerFactory.getLogger(SibexFuturesMarket.class);
    public static final String        SIBEX_FUTURES_URL = "http://www.sibex.ro/index.php?p=realMarket&s=1&l=en&sort=1";

    private final Map<String, Record> status            = new HashMap<String, Record>();

    @Override
    public boolean isClosed() {
        return !isOpened();
    }

    @Override
    public boolean isOpened() {
        final String time = Moment.getNow().toIsoTime();
        return time.compareTo(Context.getSibexCloseTime()) < 0;
    }

    @Override
    public void run() {
        while (isOpened()) {
            try {
                logger.info("Reading Sibex futures");
                update();
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
        final Record prevRecord = status.get(record.getId());
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
        status.put(record.getId(), record);
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
