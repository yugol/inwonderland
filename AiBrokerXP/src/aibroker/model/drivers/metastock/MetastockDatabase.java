package aibroker.model.drivers.metastock;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import aibroker.model.Ohlc;
import aibroker.model.Quotes;
import aibroker.model.QuotesDatabase;
import aibroker.model.Sequence;
import aibroker.model.SequenceBuilder;
import aibroker.model.SequenceSelector;
import aibroker.util.BrokerException;
import aibroker.util.ByteCodec;
import aibroker.util.FileUtil;

public class MetastockDatabase extends QuotesDatabase {

    public MetastockDatabase(final File dbFolder) throws BrokerException {
        super(dbFolder);
        readMaster();
    }

    @Override
    public Sequence add(final SequenceBuilder tBuilder) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void drop() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Sequence> getSequences(final SequenceSelector selector) {
        final List<Sequence> sequences = new ArrayList<Sequence>();
        final Sequence sequence = getSequence(selector.getName());
        sequences.add(sequence);
        return sequences;
    }

    @Override
    public void save() {
        throw new UnsupportedOperationException();
    }

    private void readMaster() throws BrokerException {
        final byte[] masterData = FileUtil.readBytes(new File(dbLocation, FormatDescriptor.MASTER_FILE_NAME));
        final int recCount = ByteCodec.readUnsignedByte(masterData, 0);
        for (int recNo = 0; recNo < recCount; ++recNo) {
            final MetastockSequence sequence = MetastockSequence.fromRawData(this, masterData, (recNo + 1) * FormatDescriptor.MASTER_RECORD_LENGTH);
            add(sequence);
        }
    }

    @Override
    protected Quotes readQuotes(final Sequence t) {
        final MetastockSequence sequence = (MetastockSequence) t;
        final byte[] quotesData = FileUtil.readBytes(new File(dbLocation, sequence.getQuotesFileName()));
        final int quotesCount = quotesData.length / sequence.getByteCount() - 1;
        final Quotes quotes = new Quotes(quotesCount);
        for (int qNo = 0; qNo < quotesCount; ++qNo) {
            final int offset = (qNo + 1) * sequence.getByteCount();
            final Ohlc ohlc = new Ohlc(MetastockDecoder.readMsMoment(quotesData, offset),
                    MetastockDecoder.readMsFloat(quotesData, offset + 4),
                    MetastockDecoder.readMsFloat(quotesData, offset + 8),
                    MetastockDecoder.readMsFloat(quotesData, offset + 12),
                    MetastockDecoder.readMsFloat(quotesData, offset + 16),
                    (int) MetastockDecoder.readMsFloat(quotesData, offset + 20));
            quotes.add(ohlc);
        }
        return quotes;
    }

}
