package aibroker.engines.markets.sibex;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import aibroker.model.SeqDesc;
import aibroker.util.NumberUtil;
import aibroker.util.StringUtil;

public class RecordReader {

    public static Record readRecord(final String snapshot, final int index) {
        int beginIndex = index + 8;
        int endIndex = snapshot.indexOf("\">", beginIndex);
        final Record record = new Record();

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
        record.setBidPrice(NumberUtil.parseFloatRo(snapshot.substring(beginIndex, endIndex)));

        // Ask Price
        beginIndex = snapshot.indexOf("<td", beginIndex + 1);
        beginIndex = snapshot.indexOf(">", beginIndex) + 1;
        endIndex = snapshot.indexOf("</td>", beginIndex);
        record.setAskPrice(NumberUtil.parseFloatRo(snapshot.substring(beginIndex, endIndex)));

        // Last Price
        beginIndex = snapshot.indexOf("<td", beginIndex + 1);
        beginIndex = snapshot.indexOf(">", beginIndex) + 1;
        endIndex = snapshot.indexOf("</td>", beginIndex);
        record.setLastPrice(NumberUtil.parseFloatRo(snapshot.substring(beginIndex, endIndex)));

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

    public static void readRecords(final Map<String, Record> liveMarket, final File csvFile) throws IOException {
        try (BufferedReader csv = new BufferedReader(new FileReader(csvFile))) {
            String line = csv.readLine();
            while ((line = csv.readLine()) != null) {
                if (!StringUtil.isNullOrBlank(line)) {
                    final String[] chunks = line.split(",");
                    final Record r1 = new Record();
                    r1.setSymbol(SeqDesc.getSymbol(chunks[0]));
                    r1.setExpiryMonth(SeqDesc.getSettlement(chunks[0]));
                    r1.setLastPrice(NumberUtil.parseFloatRo(chunks[3]));
                    r1.setVolume(NumberUtil.parseInt(chunks[4]));
                    r1.setOpenInt(NumberUtil.parseInt(chunks[5]));

                    final Record r0 = liveMarket.get(r1.getId());
                    if (r0 != null) {
                        r0.setLastPrice(r1.getLastPrice());
                        r0.setVolume(r0.getVolume() + r1.getVolume());
                        r0.setOpenInt(r1.getOpenInt());
                    } else {
                        liveMarket.put(r1.getId(), r1);
                    }
                }
            }
        }
    }

}
