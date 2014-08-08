package tools;

import java.util.Calendar;
import aibroker.Context;
import aibroker.model.SeqDesc;
import aibroker.model.domains.Feed;
import aibroker.model.domains.Grouping;
import aibroker.model.domains.Market;
import aibroker.model.domains.Sampling;
import aibroker.model.domains.Updater;
import aibroker.model.drivers.sql.SqlDb;
import aibroker.util.Moment;

public class SqlDefaultSequencesPopulator {

    private static class SB2 extends SeqDesc {

        public SB2(final String name) {
            super(name);
        }

        public void settlement(final int month, final int year) {
            settlement = calculateSettlement(month, year);
        }

        public void settlement(final String month, final int year) {
            settlement(Moment.monthNameToIndex(month), year);
        }

    }

    public static void addDefaultSequences(final SqlDb qdb) {
        addYahooRegular("DJIA", "1985-01-29", qdb);

        addYahooRegular("AAPL", "1984-09-07", qdb);
        addYahooRegular("FB", "2012-05-18", qdb);
        addYahooRegular("GOOG", "2004-08-19", qdb);
        addYahooRegular("MCD", "1970-01-02", qdb);
        addYahooRegular("V", "2008-03-19", qdb);

        addBvbRegular("SIF3", "2000-01-02", qdb);
        addBvbRegular("SIF4", "2000-01-02", qdb);
        addBvbRegular("SIF5", "2000-01-02", qdb);

        add_DEDJIA_RON(qdb);
        add_DESIF3(qdb);
        add_DESIF4(qdb);
        add_DESIF5(qdb);
        add_DEOIL_LSC(qdb);
        add_EURUSD_RON(qdb);
        add_SIBGOLD(qdb);
        add_SIBGOLD_RON(qdb);

        add_DEAPL(qdb);
        add_DEFCB(qdb);
        add_DEGGL(qdb);
        add_DEMCD(qdb);
        add_DEVSA(qdb);
    }

    private static void add_DEAPL(final SqlDb qdb) {
        final SB2 builder = (SB2) new SB2("DEAPL").support("AAPL");
        builder.market(Market.FUTURES).fee(2.6).multiplier(10.0).margin(500.0);

        builder.sampling(Sampling.SECOND).grouping(Grouping.TICK);
        builder.updater(Updater.SIBEX_FUT_TICK);
        builder.feed(Feed.ORIG);

        builder.settlement("DEC", 2013);
        qdb.add(builder);

        for (int year = 2014; year <= Context.LAST_YEAR; ++year) {
            for (int month = Calendar.MARCH; month <= Calendar.DECEMBER; month += 3) {
                builder.settlement(month, year);
                qdb.add(builder);
            }
        }
    }

    private static void add_DEDJIA_RON(final SqlDb qdb) {
        final SB2 builder = (SB2) new SB2("DEDJIA_RON").support("DJIA");
        builder.market(Market.FUTURES).fee(1.2).multiplier(1.0).margin(500.0);

        builder.sampling(Sampling.SECOND).grouping(Grouping.TICK);
        builder.updater(Updater.SIBEX_FUT_TICK);
        builder.feed(Feed.ORIG);

        for (int year = 2010; year <= Context.LAST_YEAR; ++year) {
            for (int month = Calendar.MARCH; month <= Calendar.DECEMBER; month += 3) {
                builder.settlement(month, year);
                qdb.add(builder);
            }
        }
    }

    private static void add_DEFCB(final SqlDb qdb) {
        final SB2 builder = (SB2) new SB2("DEFCB").support("FB");
        builder.market(Market.FUTURES).fee(2.6).multiplier(100.0).margin(500.0);

        builder.sampling(Sampling.SECOND).grouping(Grouping.TICK);
        builder.updater(Updater.SIBEX_FUT_TICK);
        builder.feed(Feed.ORIG);

        builder.settlement("DEC", 2013);
        qdb.add(builder);

        for (int year = 2014; year <= Context.LAST_YEAR; ++year) {
            for (int month = Calendar.MARCH; month <= Calendar.DECEMBER; month += 3) {
                builder.settlement(month, year);
                qdb.add(builder);
            }
        }
    }

    private static void add_DEGGL(final SqlDb qdb) {
        final SB2 builder = (SB2) new SB2("DEGGL").support("GOOG");
        builder.market(Market.FUTURES).fee(2.6).multiplier(1.0).margin(500.0);

        builder.sampling(Sampling.SECOND).grouping(Grouping.TICK);
        builder.updater(Updater.SIBEX_FUT_TICK);
        builder.feed(Feed.ORIG);

        builder.settlement("DEC", 2013);
        qdb.add(builder);

        for (int year = 2014; year <= Context.LAST_YEAR; ++year) {
            for (int month = Calendar.MARCH; month <= Calendar.DECEMBER; month += 3) {
                builder.settlement(month, year);
                qdb.add(builder);
            }
        }
    }

    private static void add_DEMCD(final SqlDb qdb) {
        final SB2 builder = (SB2) new SB2("DEMCD").support("MCD");
        builder.market(Market.FUTURES).fee(2.6).multiplier(10.0).margin(500.0);

        builder.sampling(Sampling.SECOND).grouping(Grouping.TICK);
        builder.updater(Updater.SIBEX_FUT_TICK);
        builder.feed(Feed.ORIG);

        builder.settlement("DEC", 2013);
        qdb.add(builder);

        for (int year = 2014; year <= Context.LAST_YEAR; ++year) {
            for (int month = Calendar.MARCH; month <= Calendar.DECEMBER; month += 3) {
                builder.settlement(month, year);
                qdb.add(builder);
            }
        }
    }

    private static void add_DEOIL_LSC(final SqlDb qdb) {
        final SB2 builder = (SB2) new SB2("DEOIL_LSC").support("OIL_LSC");
        builder.market(Market.FUTURES).fee(1.2).multiplier(100.0).margin(500.0);

        builder.sampling(Sampling.SECOND).grouping(Grouping.TICK);
        builder.updater(Updater.SIBEX_FUT_TICK);
        builder.feed(Feed.ORIG);

        for (int month = Calendar.MAY; month <= Calendar.DECEMBER; ++month) {
            builder.settlement(month, 2011);
            qdb.add(builder);
        }

        for (int year = 2012; year <= Context.LAST_YEAR; ++year) {
            for (int month = Calendar.JANUARY; month <= Calendar.DECEMBER; ++month) {
                builder.settlement(month, year);
                qdb.add(builder);
            }
        }
    }

    private static void add_DESIF3(final SqlDb qdb) {
        final SB2 builder = (SB2) new SB2("DESIF3").support("SIF3");
        builder.market(Market.FUTURES).fee(0.7).multiplier(1000.0).margin(250.0);

        builder.sampling(Sampling.SECOND).grouping(Grouping.TICK);
        builder.updater(Updater.SIBEX_FUT_TICK);
        builder.feed(Feed.ORIG);

        for (int month = Calendar.JUNE; month <= Calendar.DECEMBER; month += 3) {
            builder.settlement(month, 2007);
            qdb.add(builder);
        }

        for (int year = 2008; year <= Context.LAST_YEAR; ++year) {
            for (int month = Calendar.MARCH; month <= Calendar.DECEMBER; month += 3) {
                builder.settlement(month, year);
                qdb.add(builder);
            }
        }
    }

    private static void add_DESIF5(final SqlDb qdb) {
        final SB2 builder = (SB2) new SB2("DESIF5").support("SIF5");
        builder.market(Market.FUTURES).fee(0.6).multiplier(1000.0).margin(250.0);

        builder.sampling(Sampling.SECOND).grouping(Grouping.TICK);
        builder.updater(Updater.SIBEX_FUT_TICK);
        builder.feed(Feed.ORIG);

        for (int month = Calendar.JUNE; month <= Calendar.DECEMBER; month += 3) {
            builder.settlement(month, 2007);
            qdb.add(builder);
        }

        for (int year = 2008; year <= Context.LAST_YEAR; ++year) {
            for (int month = Calendar.MARCH; month <= Calendar.DECEMBER; month += 3) {
                builder.settlement(month, year);
                qdb.add(builder);
            }
        }
    }

    private static void add_DEVSA(final SqlDb qdb) {
        final SB2 builder = (SB2) new SB2("DEVSA").support("V");
        builder.market(Market.FUTURES).fee(2.6).multiplier(10.0).margin(500.0);

        builder.sampling(Sampling.SECOND).grouping(Grouping.TICK);
        builder.updater(Updater.SIBEX_FUT_TICK);
        builder.feed(Feed.ORIG);

        builder.settlement("DEC", 2013);
        qdb.add(builder);

        for (int year = 2014; year <= Context.LAST_YEAR; ++year) {
            for (int month = Calendar.MARCH; month <= Calendar.DECEMBER; month += 3) {
                builder.settlement(month, year);
                qdb.add(builder);
            }
        }
    }

    private static void add_EURUSD_RON(final SqlDb qdb) {
        final SB2 builder = (SB2) new SB2("EUR/USD_RON").support("EUR/USD");
        builder.market(Market.FUTURES).fee(1.2).multiplier(10000.0).margin(300.0);

        builder.sampling(Sampling.SECOND).grouping(Grouping.TICK);
        builder.updater(Updater.SIBEX_FUT_TICK);
        builder.feed(Feed.ORIG);

        for (int month = Calendar.JUNE; month <= Calendar.DECEMBER; month += 3) {
            builder.settlement(month, 2011);
            qdb.add(builder);
        }

        for (int year = 2012; year <= Context.LAST_YEAR; ++year) {
            for (int month = Calendar.MARCH; month <= Calendar.DECEMBER; month += 3) {
                builder.settlement(month, year);
                qdb.add(builder);
            }
        }
    }

    private static void add_SIBGOLD(final SqlDb qdb) {
        final SB2 builder = (SB2) new SB2("SIBGOLD").support("GOLD");
        builder.market(Market.FUTURES).fee(0.6).multiplier(10.0).margin(500.0);

        builder.sampling(Sampling.SECOND).grouping(Grouping.TICK);
        builder.updater(Updater.SIBEX_FUT_TICK);
        builder.feed(Feed.ORIG);

        for (int month = Calendar.JUNE; month <= Calendar.DECEMBER; month += 3) {
            builder.settlement(month, 2007);
            qdb.add(builder);
        }

        for (int year = 2008; year <= 2009; ++year) {
            for (int month = Calendar.MARCH; month <= Calendar.DECEMBER; month += 3) {
                builder.settlement(month, year);
                qdb.add(builder);
            }
        }

        builder.settlement("MAR", 10);
        qdb.add(builder);

        builder.settlement("IUN", 10);
        qdb.add(builder);

        for (int month = Calendar.APRIL; month <= Calendar.DECEMBER; month += 2) {
            builder.settlement(month, 2011);
            qdb.add(builder);
        }

        for (int year = 2012; year <= Context.LAST_YEAR; ++year) {
            for (int month = Calendar.FEBRUARY; month <= Calendar.DECEMBER; month += 2) {
                builder.settlement(month, year);
                qdb.add(builder);
            }
        }
    }

    private static void add_SIBGOLD_RON(final SqlDb qdb) {
        final SB2 builder = (SB2) new SB2("SIBGOLD_RON").support("GOLD");
        builder.market(Market.FUTURES).fee(1.2).multiplier(1.0).margin(500.0);

        builder.sampling(Sampling.SECOND).grouping(Grouping.TICK);
        builder.updater(Updater.SIBEX_FUT_TICK);
        builder.feed(Feed.ORIG);

        for (int year = 2011; year <= Context.LAST_YEAR; ++year) {
            for (int month = Calendar.FEBRUARY; month <= Calendar.DECEMBER; month += 2) {
                builder.settlement(month, year);
                qdb.add(builder);
            }
        }
    }

    private static void addBvbRegular(final String symbol, final String firstTransaction, final SqlDb qdb) {
        final SB2 builder = (SB2) new SB2(symbol).name(symbol).support(symbol);
        builder.market(Market.REGS).sampling(Sampling.DAILY).grouping(Grouping.OHLC);
        builder.firstDayOfTransaction(firstTransaction);

        builder.feed(Feed.ORIG);
        builder.updater(Updater.BVB_REG_DAILY_BASE);
        qdb.add(builder);
    }

    private static void addYahooRegular(final String symbol, final String firstTransaction, final SqlDb qdb) {
        final SB2 builder = (SB2) new SB2(symbol).name(symbol).support(symbol);
        builder.market(Market.REGS).sampling(Sampling.DAILY).grouping(Grouping.OHLC);
        builder.feed(Feed.ORIG);
        builder.updater(Updater.YAHOO_DAILY);
        builder.firstDayOfTransaction(firstTransaction);
        qdb.add(builder);
    }

    static void add_DESIF4(final SqlDb qdb) {
        final SB2 builder = (SB2) new SB2("DESIF4").support("SIF4");
        builder.market(Market.FUTURES).fee(0.7).multiplier(1000.0).margin(250.0);

        builder.sampling(Sampling.SECOND).grouping(Grouping.TICK);
        builder.updater(Updater.SIBEX_FUT_TICK);
        builder.feed(Feed.ORIG);

        for (int month = Calendar.JUNE; month <= Calendar.DECEMBER; month += 3) {
            builder.settlement(month, 2007);
            qdb.add(builder);
        }

        for (int year = 2008; year <= Context.LAST_YEAR; ++year) {
            for (int month = Calendar.MARCH; month <= Calendar.DECEMBER; month += 3) {
                builder.settlement(month, year);
                qdb.add(builder);
            }
        }
    }

}
