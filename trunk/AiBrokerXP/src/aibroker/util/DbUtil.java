package aibroker.util;

import java.io.File;
import java.util.List;
import aibroker.model.Ohlc;
import aibroker.model.QuotesDb;
import aibroker.model.Seq;
import aibroker.model.SeqDesc;
import aibroker.model.SeqSel;
import aibroker.model.cloud.sources.bvb.BvbSeqDescriptionReader;
import aibroker.model.cloud.sources.sibex.SibexSeqDescriptionReader;
import aibroker.model.domains.Feed;
import aibroker.model.domains.Grouping;
import aibroker.model.domains.Market;
import aibroker.model.domains.Sampling;
import aibroker.model.drivers.sql.SqlDb;
import aibroker.model.drivers.sql.SqlSeq;

public class DbUtil {

    public static SeqSel addQuote(final SqlDb db, final String name, final Ohlc ohlc) throws Exception {
        final SeqDesc sDesc = new SeqDesc(name);
        sDesc.setFeed(Feed.ORIG);
        if (name.equals(sDesc.getSymbol())) {
            sDesc.setMarket(Market.REGS);
            sDesc.setSampling(Sampling.DAILY);
            sDesc.setGrouping(Grouping.OHLC);
        } else {
            sDesc.setMarket(Market.FUTURES);
            sDesc.setSampling(Sampling.SECOND);
            sDesc.setGrouping(Grouping.TICK);
        }

        Seq sequence = null;

        // try to find the symbol/market pair
        final SeqSel sSel = new SeqSel();
        sSel.setSymbol(sDesc.getSymbol());
        sSel.setMarket(sDesc.getMarket());
        final List<SqlSeq> sequences = db.getSequences(sSel);
        if (sequences.isEmpty()) {
            // insert new symbol for market
            if (sDesc.getMarket() == Market.REGS) {
                BvbSeqDescriptionReader.fillDescription(sDesc);
                sequence = db.add(sDesc);
            } else if (sDesc.getMarket() == Market.FUTURES) {
                SibexSeqDescriptionReader.fillDescription(sDesc);
                addQuote(db, sDesc.getSupport(), null); // make sure support is in the database
                sequence = db.add(sDesc);
            }
        } else {
            if (sDesc.getMarket() == Market.FUTURES) {
                sDesc.setSupport(sequences.get(0).getSupport());
            }
        }

        // try to add the quotes
        if (ohlc != null) {
            if (sequence == null) {
                // symbol/market exists - find the proper sequence
                sequence = db.add(sDesc);
            }
            sequence.addQuotes(ohlc);
        }

        return sDesc.toSelector();
    }

    public static void readSibexFuturesLog(final QuotesDb db, final File log) {

    }

    public static void readSibexLog(final QuotesDb db, final File log) {

    }

}
