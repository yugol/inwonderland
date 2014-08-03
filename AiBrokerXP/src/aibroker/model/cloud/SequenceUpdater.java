package aibroker.model.cloud;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import aibroker.model.Ohlc;
import aibroker.model.Quotes;
import aibroker.model.Sequence;
import aibroker.model.SequenceSelector;
import aibroker.model.domains.Feed;
import aibroker.model.domains.Updater;
import aibroker.model.drivers.sql.SqlDatabase;
import aibroker.model.drivers.sql.SqlSequence;
import aibroker.util.Moment;

public class SequenceUpdater implements Runnable {

    private boolean                            cancel    = false;
    private final List<SequenceUpdateListener> listeners = new ArrayList<SequenceUpdateListener>();
    private final Sequence                     sequence;

    public SequenceUpdater(final Sequence sequence) {
        this.sequence = sequence;
    }

    public void addUpdateListener(final SequenceUpdateListener listener) {
        if (listener != null) {
            listeners.add(listener);
        }
    }

    @Override
    public void run() {
        final Updater updater = sequence.getUpdater();
        switch (updater) {
            case BVB_REG_DAILY_BASE:
            case BVB_REG_DAILY_NORM:
            case YAHOO_DAILY:
                updateFomCloud(updater);
                break;
            case CACHED:
                updateFromCached();
                break;
            case CACHED_SIBEX_FUT_TICK:
                updateFromLogs();
            default:
                break;
        }
    }

    private void updateFomCloud(final Updater updater) {
        final CloudDataSource source = CloudDataSource.getDataSource(updater);

        Moment firstDate = null;
        final Ohlc lastQuote = sequence.getLastQuote();
        if (lastQuote != null) {
            firstDate = lastQuote.moment;
        }
        if (firstDate == null) {
            if (sequence instanceof SqlSequence) {
                firstDate = ((SqlSequence) sequence).getFirstDayOfTransaction();
            }
        }
        if (firstDate == null) {
            if (sequence.isRegular()) {
                firstDate = Moment.fromIso("2000-01-01");
            } else {
                firstDate = Moment.fromIso(sequence.getSettlement().toIsoDate());
                firstDate.set(Calendar.DAY_OF_MONTH, 1);
                firstDate.add(Calendar.MONTH, -5);
            }
        }
        firstDate = firstDate.getBeginningOfDay();

        Moment lastDate = Moment.getEndOfToday();
        if (!sequence.isRegular()) {
            final Moment settlement = sequence.getSettlement().getEndOfDay();
            if (settlement.compareTo(lastDate) < 0) {
                lastDate = settlement;
            }
        }

        if (firstDate.compareTo(lastDate) < 0) {
            final String symbol = sequence.getSymbol();
            final String settlement = sequence.getSettlementString();
            for (final SequenceUpdateListener listener : listeners) {
                listener.onBeginDownloading(symbol, settlement, firstDate, lastDate);
            }
            boolean deleteDate = true;
            while (firstDate.compareTo(lastDate) < 0) {
                for (final SequenceUpdateListener listener : listeners) {
                    listener.onDownloading(symbol, settlement, firstDate);
                }
                try {
                    final List<Ohlc> quotes = source.getQuotes(symbol, settlement, firstDate);
                    if (deleteDate) {
                        sequence.deleteQuotes(firstDate);
                        deleteDate = false;
                    }
                    sequence.addQuotes(quotes);
                } catch (final Exception e) {
                    e.printStackTrace();
                    for (final SequenceUpdateListener listener : listeners) {
                        listener.onError(symbol, settlement, firstDate, e);
                    }
                }
                firstDate.add(Calendar.DAY_OF_YEAR, 1);
                for (final SequenceUpdateListener listener : listeners) {
                    cancel = cancel || listener.onDownloaded(symbol, settlement);
                }
                if (cancel) {
                    break;
                }
            }
            if (!cancel) {
                for (final SequenceUpdateListener listener : listeners) {
                    listener.onEndDownloading(symbol, settlement);
                }
            }
        }
    }

    private void updateFromCached() {
        final String symbol = sequence.getSymbol();
        final String settlement = sequence.getSettlementString();
        final Moment firstDate = new Moment();
        try {
            for (final SequenceUpdateListener listener : listeners) {
                listener.onDownloading(symbol, settlement, firstDate);
            }
            final SqlSequence sequence = (SqlSequence) this.sequence;
            final SqlDatabase sqlDb = sequence.getDatabase();
            final SequenceSelector selector = sequence.toSelector();
            if (selector.getSettlement() == null || selector.getSettlement().getYear() == 2000) {
                selector.setSettlement(null);
                selector.setJoinSettlements(true);
            }
            selector.setFeed(Feed.ORIG);
            final SqlSequence origSequence = sqlDb.getSequence(selector);
            final Quotes quotes = origSequence.getQuotes();
            sequence.deleteQuotes();
            sequence.addQuotes(quotes);
            for (final SequenceUpdateListener listener : listeners) {
                cancel = cancel || listener.onDownloaded(symbol, settlement);
            }
        } catch (final SQLException e) {
            e.printStackTrace();
            for (final SequenceUpdateListener listener : listeners) {
                listener.onError(symbol, settlement, firstDate, e);
            }
        }
        if (!cancel) {
            for (final SequenceUpdateListener listener : listeners) {
                listener.onEndDownloading(symbol, settlement);
            }
        }
    }

    private void updateFromLogs() {
        final String symbol = sequence.getSymbol();
        final String settlement = sequence.getSettlementString();
        final Moment firstDate = new Moment();
        for (final SequenceUpdateListener listener : listeners) {
            listener.onError(symbol, settlement, firstDate, new UnsupportedOperationException("CACHED_SIBEX_FUT_TICK is not yet implemented"));
        }
        for (final SequenceUpdateListener listener : listeners) {
            listener.onEndDownloading(symbol, settlement);
        }
    }

}
