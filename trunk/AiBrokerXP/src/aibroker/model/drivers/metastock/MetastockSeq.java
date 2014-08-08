package aibroker.model.drivers.metastock;

import aibroker.model.Seq;
import aibroker.model.SeqDesc;
import aibroker.model.domains.Sampling;
import aibroker.util.BrokerException;
import aibroker.util.ByteCodec;
import aibroker.util.Moment;

public class MetastockSeq extends Seq {

    public static MetastockSeq fromRawData(final MetastockDb msq, final byte[] data, final int offset) {
        final SeqDesc sb = new SeqDesc(ByteCodec.readString(data, offset + FormatDescriptor.SYMBOL_0, FormatDescriptor.SYMBOL_LEN));
        final MetastockSeq sequence = new MetastockSeq(msq, sb);
        switch (MetastockDecoder.readByteAsChar(data, offset + FormatDescriptor.TIME_FRAME)) {
            case 'Y':
                sequence.sampling = Sampling.YEARLY;
                break;
            case 'Q':
                sequence.sampling = Sampling.QUARTERLY;
                break;
            case 'M':
                sequence.sampling = Sampling.MONTHLY;
                break;
            case 'W':
                sequence.sampling = Sampling.WEEKLY;
                break;
            case 'D':
                sequence.sampling = Sampling.DAILY;
                break;
            default:
                final int idaTime = MetastockDecoder.readLittleEndianShortAsInt(data, FormatDescriptor.IDA_TIME_0);
                if (idaTime != 0) {
                    throw new BrokerException("Unsupported intraday time base: " + idaTime);
                } else {
                    sequence.sampling = Sampling.SECOND;
                }
        }
        sequence.fileNo = MetastockDecoder.readByteAsShort(data, offset + FormatDescriptor.FILE_NUM);
        sequence.fileType = MetastockDecoder.readByteAsChar(data, offset + FormatDescriptor.FILE_TYPE_0);
        sequence.byteCount = MetastockDecoder.readByte(data, offset + FormatDescriptor.RECORD_LENGTH);
        sequence.fieldCount = MetastockDecoder.readByte(data, offset + FormatDescriptor.RECORD_COUNT);
        sequence.v28 = MetastockDecoder.readByteAsChar(data, offset + FormatDescriptor.CT_V2_8_FLAG) == 'Y';
        sequence.firstDate = MetastockDecoder.readMsMoment(data, offset + FormatDescriptor.FIRST_DATE_0);
        sequence.lastDate = MetastockDecoder.readMsMoment(data, offset + FormatDescriptor.LAST_DATE_0);
        sequence.issueName = ByteCodec.readString(data, offset + FormatDescriptor.ISSUE_NAME_0, FormatDescriptor.ISSUE_NAME_LEN);
        sequence.autorun = MetastockDecoder.readByteAsChar(data, offset + FormatDescriptor.FLAG) == '*';
        return sequence;
    }

    private Sampling sampling;
    private String   issueName;
    private short    fileNo;
    private char     fileType;
    private byte     byteCount;
    private byte     fieldCount;
    private boolean  v28;
    private Moment   firstDate;
    private Moment   lastDate;
    private boolean  autorun;

    MetastockSeq(final MetastockDb msDb, final SeqDesc sb) {
        super(msDb, sb);
    }

    public byte getByteCount() {
        return byteCount;
    }

    public byte getFieldCount() {
        return fieldCount;
    }

    public short getFileNo() {
        return fileNo;
    }

    public char getFileType() {
        return fileType;
    }

    public Moment getFirstDate() {
        return firstDate;
    }

    public String getIssueName() {
        return issueName;
    }

    public Moment getLastDate() {
        return lastDate;
    }

    public Sampling getSampling() {
        return sampling;
    }

    public boolean isAutorun() {
        return autorun;
    }

    public boolean isV28() {
        return v28;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(super.toString());
        sb.append("\n    #").append(fileNo).append(" [").append(fileType).append("] ");
        sb.append(fieldCount).append(" fields in ").append(byteCount).append(" bytes");
        sb.append("\n    ").append(firstDate.toIsoDate()).append(" --> ").append(lastDate.toIsoDate());
        return sb.toString();
    }

    String getQuotesFileName() {
        return "F" + fileNo + ".DAT";
    }

}
