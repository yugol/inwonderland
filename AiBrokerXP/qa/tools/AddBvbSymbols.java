package tools;

import aibroker.TestConfig;
import aibroker.model.SeqDesc;
import aibroker.model.cloud.sources.bvb.BvbSeqDescriptionReader;
import aibroker.model.domains.Feed;
import aibroker.model.domains.Grouping;
import aibroker.model.domains.Market;
import aibroker.model.domains.Sampling;
import aibroker.model.domains.Updater;
import aibroker.model.drivers.sql.SqlDb;

public class AddBvbSymbols {

    public static void main(final String[] args) throws Exception {
        final SqlDb sqlDb = new SqlDb(TestConfig.TEST_SQL_DB_FILE);
        // sqlDb = Databases.DEFAULT();
        for (final String symbol : SYMBOLS) {
            final SeqDesc sDesc = BvbSeqDescriptionReader.readDescription(symbol);
            sDesc.market(Market.REGS);
            sDesc.grouping(Grouping.OHLC);
            sDesc.sampling(Sampling.DAILY);
            sDesc.feed(Feed.ORIG);
            sDesc.updater(Updater.BVB_REG_DAILY_BASE);
            System.out.println("Adding " + symbol);
            sqlDb.add(sDesc);
            sDesc.feed(Feed.NORM);
            sDesc.updater(Updater.BVB_REG_DAILY_NORM);
            sqlDb.add(sDesc);
        }
    }

    private static final String[] SYMBOLS = {
        "ALR",
        "ATB",
        "BCC",
        "BIO",
        "BRD",
        "BRK",
        "COFI",
        "COTE",
        "EL",
        "ELMA",
        "FP",
        "IMP",
        "OIL",
        "OLT",
        "PREH",
        "RPH",
        "SIF1",
        "SIF2",
        "SIF3",
        "SIF4",
        "SIF5",
        "SNG",
        "SNN",
        "SNP",
        "SOCP",
        "TBM",
        "TEL",
        "TGN",
        "TLV",
    };

}
