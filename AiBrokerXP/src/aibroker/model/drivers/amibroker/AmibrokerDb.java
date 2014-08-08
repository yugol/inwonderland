package aibroker.model.drivers.amibroker;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FileUtils;
import aibroker.model.Quotes;
import aibroker.model.QuotesDb;
import aibroker.model.Seq;
import aibroker.model.SeqDesc;
import aibroker.model.SeqSel;
import aibroker.util.ByteCodec;
import aibroker.util.FileUtil;

public class AmibrokerDb extends QuotesDb {

    private static final String SUB_FOLDERS         = "_1234567890qwertyuiopasdfghjklzxcvbnm";
    private static final String LAYOUTS_FOLDER_NAME = "Layouts";
    private static final String MASTER_FILE_NAME    = "broker.master";

    private static void createNewDatabase(final String path) {
        final File folder = new File(path);
        folder.mkdirs();
        for (int i = 0; i < SUB_FOLDERS.length(); ++i) {
            final File subFolder = new File(folder, SUB_FOLDERS.substring(i, i + 1));
            subFolder.mkdir();
        }
        final File layoutFolder = new File(folder, LAYOUTS_FOLDER_NAME);
        layoutFolder.mkdir();
        FileUtil.writeBytes(new File(folder, MASTER_FILE_NAME), MasterFile.initBuffer(0));
    }

    private final byte[] descriptor = new byte[MasterFile.RECORD_0];

    public AmibrokerDb(final File dbFolder) {
        super(dbFolder);
        readMaster();
    }

    @Override
    public AmibrokerSeq add(final Seq sequence) {
        final AmibrokerSeq aSequence = add(sequence.getName());
        aSequence.setQuotes(sequence.getQuotes());
        return aSequence;
    }

    @Override
    public AmibrokerSeq add(final SeqDesc tBuilder) {
        final AmibrokerSeq aSequence = new AmibrokerSeq(this, tBuilder);
        aSequence.setName(aSequence.getName());
        aSequence.setPointValue(1f);
        aSequence.setContinuousQuotation(true);
        aSequence.setLocalDatabaseOnly(true);
        return (AmibrokerSeq) super.add(aSequence);
    }

    public AmibrokerSeq add(final String name) {
        return add(new SeqDesc(name));
    }

    @Override
    public void drop() throws IOException {
        FileUtils.deleteDirectory(dbLocation);
    }

    @Override
    public List<AmibrokerSeq> getSequences(final SeqSel selector) {
        final List<AmibrokerSeq> sequences = new ArrayList<AmibrokerSeq>();
        final AmibrokerSeq sequence = getSequence(selector.getName());
        sequences.add(sequence);
        return sequences;
    }

    public String getVersion() {
        return ByteCodec.readString(descriptor, 0, MasterFile.MARKER_LEN);
    }

    @Override
    public void save() {
        if (!dbLocation.exists()) {
            createNewDatabase(dbLocation.getAbsolutePath());
        }
        int sequenceCount = 0;
        for (final Seq sequence : this) {
            final AmibrokerSeq tmp = (AmibrokerSeq) sequence;
            if (tmp.isDirty()) {
                if (tmp.isDeleted()) {
                    deleteSequence(tmp);
                } else {
                    writeSequence(tmp);
                    ++sequenceCount;
                }
            }
        }
        writeMaster(sequenceCount);
    }

    private void deleteSequence(final AmibrokerSeq sequence) {
        sequence.delete();
    }

    private File getQuotesFile(String name) {
        char bucket = name.toLowerCase().charAt(0);
        if (!Character.isLetter(bucket) && !Character.isDigit(bucket)) {
            bucket = '_';
        }
        final File qFolder = new File(dbLocation, bucket + "");
        if (FileUtil.isReserved(name)) {
            final StringBuilder temp = new StringBuilder(name);
            temp.replace(2, 2, "_");
            name = temp.toString();
        }
        final File qFile = new File(qFolder, name);
        return qFile;
    }

    private int getRecordCount() {
        return ByteCodec.readLittleEndianInteger(descriptor, MasterFile.RECORD_COUNT_0);
    }

    private void readMaster() {
        if (!dbLocation.exists()) {
            dbLocation.mkdirs();
        }
        final File masterFile = new File(dbLocation, MASTER_FILE_NAME);
        if (!masterFile.exists()) {
            createNewDatabase(dbLocation.getAbsolutePath());
        }
        final byte[] buffer = FileUtil.readBytes(masterFile);
        System.arraycopy(buffer, MasterFile.MARKER_0, descriptor, 0, descriptor.length);

        final int recordCount = getRecordCount();
        int offset = MasterFile.RECORD_0;
        for (int i = 0; i < recordCount; ++i) {
            super.add(AmibrokerSeq.fromRawData(this, buffer, offset));
            offset += MasterFile.RECORD_LEN;
        }
    }

    private void writeMaster(final int sequenceCount) {
        final byte[] buffer = MasterFile.initBuffer(sequenceCount);
        int tNo = 0;
        for (final Seq sequence : this) {
            final AmibrokerSeq amiSequence = (AmibrokerSeq) sequence;
            if (!amiSequence.isDeleted()) {
                MasterFile.writeSequence(tNo++, amiSequence, buffer);
            }
        }
        FileUtil.writeBytes(new File(dbLocation, MASTER_FILE_NAME), buffer);
    }

    private void writeSequence(final AmibrokerSeq sequence) {
        final byte[] buffer = QuotesFile.initBuffer(sequence);
        final Quotes quotes = sequence.getQuotes();
        for (int qNo = 0; qNo < quotes.size(); ++qNo) {
            QuotesFile.writeQuote(qNo, quotes.get(qNo), buffer);
        }
        FileUtil.writeBytes(getQuotesFile(sequence.getName()), buffer);
    }

    @Override
    protected AmibrokerSeq getSequence(final String symbol) {
        return (AmibrokerSeq) super.getSequence(symbol);
    }

    @Override
    protected Quotes readQuotes(final Seq sequence) {
        final byte[] buffer = FileUtil.readBytes(getQuotesFile(sequence.getName()));
        final int qCount = AmibrokerCodec.readBigEndianInteger(buffer, QuotesFile.RECORD_COUNT_0);
        final Quotes quotes = new Quotes();
        for (int qNo = 0; qNo < qCount; ++qNo) {
            quotes.add(QuotesFile.readQuote(qNo, buffer));
        }
        return quotes;
    }

}
