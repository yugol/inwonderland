package aibroker.model.drivers.amibroker;

import java.util.Arrays;
import aibroker.model.Quotes;
import aibroker.model.Sequence;
import aibroker.model.SequenceDescriptor;
import aibroker.util.ByteCodec;

public class AmibrokerSequence extends Sequence {

    static AmibrokerSequence fromRawData(final AmibrokerDatabase amiDb, final byte[] data, final int offset) {
        final SequenceDescriptor sb = new SequenceDescriptor(ByteCodec.readString(data, MasterFile.SYMBOL_0, MasterFile.SYMBOL_LEN));
        final AmibrokerSequence sequence = new AmibrokerSequence(amiDb, sb);
        System.arraycopy(data, offset, sequence.masterDescriptor, 0, MasterFile.RECORD_LEN);
        sequence.dirty = false;
        return sequence;
    }

    private final byte[] masterDescriptor = new byte[MasterFile.RECORD_LEN];
    private final byte[] quotesDescriptor = new byte[QuotesFile.DESCRIPTOR_LEN];
    private boolean      deleted          = false;
    private boolean      dirty            = true;

    AmibrokerSequence(final AmibrokerDatabase amiDb, final SequenceDescriptor sb) {
        super(amiDb, sb);
    }

    public String getAddress() {
        return ByteCodec.readString(masterDescriptor, MasterFile.ADDRESS_0, MasterFile.ADDRESS_LEN);
    }

    public String getAlias() {
        return ByteCodec.readString(masterDescriptor, MasterFile.ALIAS_0, MasterFile.ALIAS_LEN);
    }

    public int getBookValue() {
        return ByteCodec.readLittleEndianInteger(masterDescriptor, MasterFile.BOOK_VALUE_0);
    }

    public int getCode() {
        return ByteCodec.readLittleEndianInteger(masterDescriptor, MasterFile.CODE_0);
    }

    public String getCountry() {
        return ByteCodec.readString(masterDescriptor, MasterFile.COUNTRY_0, MasterFile.COUNTRY_LEN);
    }

    public String getCurrency() {
        return ByteCodec.readString(masterDescriptor, MasterFile.CURRENCY_0, MasterFile.CURRENCY_LEN);
    }

    public int getEat(final int q) {
        return ByteCodec.readLittleEndianInteger(masterDescriptor, MasterFile.EAT_0 + q * 4);
    }

    public int getEbt(final int q) {
        return ByteCodec.readLittleEndianInteger(masterDescriptor, MasterFile.EBT_0 + q * 4);
    }

    public String getFullName() {
        return ByteCodec.readString(masterDescriptor, MasterFile.FULL_NAME_0, MasterFile.FULL_NAME_LEN);
    }

    public int getGroup() {
        return ByteCodec.readUnsignedByte(masterDescriptor, MasterFile.GROUP_FLAGS_0);
    }

    public int getIndustry() {
        return ByteCodec.readUnsignedByte(masterDescriptor, MasterFile.INDUSTRY_FLAGS_0);
    }

    public float getMarginDeposit() {
        return AmibrokerCodec.readLittleEndianFloat(masterDescriptor, MasterFile.MARGIN_DEPOSIT_0);
    }

    public int getMarket() {
        final int m0 = ByteCodec.readUnsignedByte(masterDescriptor, MasterFile.MARKET_FLAGS_0);
        final int last = m0 & 0x01;
        final int m1 = ByteCodec.readUnsignedByte(masterDescriptor, MasterFile.MARKET_FLAGS_1);
        int market = m1 != 0 ? 1 : 0;
        market <<= 6;
        market += m0 >> 2;
        market <<= 1;
        return market + last;
    }

    public int getParValue() {
        return ByteCodec.readLittleEndianInteger(masterDescriptor, MasterFile.PAR_VALUE_0);
    }

    public float getPointValue() {
        return AmibrokerCodec.readLittleEndianFloat(masterDescriptor, MasterFile.POINT_VALUE_0);
    }

    @Override
    public Quotes getQuotes() {
        return super.getQuotes();
    }

    public byte[] getQuotesDescriptor() {
        return quotesDescriptor;
    }

    public float getRoundLotSize() {
        return AmibrokerCodec.readLittleEndianFloat(masterDescriptor, MasterFile.ROUND_LOT_SIZE_0);
    }

    public int getSalesIncome(final int q) {
        return ByteCodec.readLittleEndianInteger(masterDescriptor, MasterFile.SALES_INCOME_0 + q * 4);
    }

    public int getSharesOutstanding() {
        return ByteCodec.readLittleEndianInteger(masterDescriptor, MasterFile.SHARES_OUTSTANDING_0);
    }

    public float getTickSize() {
        return AmibrokerCodec.readLittleEndianFloat(masterDescriptor, MasterFile.TICK_SIZE_0);
    }

    public String getWebId() {
        return ByteCodec.readString(masterDescriptor, MasterFile.WEB_ID_0, MasterFile.WEB_ID_LEN);
    }

    public int getYearResults(final int q) {
        return ByteCodec.readLittleEndianInteger(masterDescriptor, MasterFile.YEAR_RESULTS_0 + q * 4);
    }

    public boolean isContinuousQuotation() {
        return (masterDescriptor[MasterFile.MARKET_FLAGS_0] & 0x02) > 0;
    }

    public boolean isFavourite() {
        return (masterDescriptor[MasterFile.FLAGS_ALPHA_0] & 0x02) > 0;
    }

    public boolean isIndex() {
        return (masterDescriptor[MasterFile.FLAGS_ALPHA_0] & 0x01) > 0;
    }

    public boolean isLocalDatabaseOnly() {
        return (masterDescriptor[MasterFile.FLAGS_BETA_0] & 0x01) > 0;
    }

    public void setContinuousQuotation(final boolean val) {
        masterDescriptor[MasterFile.MARKET_FLAGS_0] = BitUtil.apply(masterDescriptor[MasterFile.MARKET_FLAGS_0], 0x02, val);
        quotesDescriptor[QuotesFile.MARKET_FLAGS_0] = BitUtil.apply(quotesDescriptor[QuotesFile.MARKET_FLAGS_0], 0x02, val);
        dirty = true;
    }

    public void setFavourite(final boolean val) {
        masterDescriptor[MasterFile.FLAGS_ALPHA_0] = BitUtil.apply(masterDescriptor[MasterFile.FLAGS_ALPHA_0], 0x02, val);
        quotesDescriptor[QuotesFile.FLAGS_ALPHA_0] = BitUtil.apply(quotesDescriptor[QuotesFile.FLAGS_ALPHA_0], 0x02, val);
        dirty = true;
    }

    public void setIndex(final boolean val) {
        masterDescriptor[MasterFile.FLAGS_ALPHA_0] = BitUtil.apply(masterDescriptor[MasterFile.FLAGS_ALPHA_0], 0x01, val);
        quotesDescriptor[QuotesFile.FLAGS_ALPHA_0] = BitUtil.apply(quotesDescriptor[QuotesFile.FLAGS_ALPHA_0], 0x01, val);
        dirty = true;
    }

    public void setLocalDatabaseOnly(final boolean val) {
        masterDescriptor[MasterFile.FLAGS_BETA_0] = BitUtil.apply(masterDescriptor[MasterFile.FLAGS_BETA_0], 0x01, val);
        quotesDescriptor[QuotesFile.FLAGS_BETA_0] = BitUtil.apply(quotesDescriptor[QuotesFile.FLAGS_BETA_0], 0x01, val);
        dirty = true;
    }

    public void setMarket(final int val) {
        int m0 = (val << 1) + (val & 0x01);
        final int m1 = m0 >> 8;
        m0 = BitUtil.apply(m0, 0x02, isContinuousQuotation());
        AmibrokerCodec.writeIntAsByte(masterDescriptor, MasterFile.MARKET_FLAGS_0, m0);
        AmibrokerCodec.writeIntAsByte(masterDescriptor, MasterFile.MARKET_FLAGS_1, m1);
        AmibrokerCodec.writeIntAsByte(quotesDescriptor, QuotesFile.MARKET_FLAGS_0, m0);
        AmibrokerCodec.writeIntAsByte(quotesDescriptor, QuotesFile.MARKET_FLAGS_1, m1);
        dirty = true;
    }

    public void setPointValue(final float val) {
        AmibrokerCodec.writeLittleEndianFloat(masterDescriptor, MasterFile.POINT_VALUE_0, val);
        AmibrokerCodec.writeLittleEndianFloat(quotesDescriptor, QuotesFile.POINT_VALUE_0, val);
        dirty = true;
    }

    @Override
    public void setQuotes(final Quotes quotes) {
        super.setQuotes(quotes);
        dirty = true;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("      margin deposit: ").append(getMarginDeposit()).append("\n");
        sb.append("         point value: ").append(getPointValue()).append("\n");
        sb.append("      round lot size: ").append(getRoundLotSize()).append("\n");
        sb.append("           full name: ").append(getFullName()).append("\n");
        sb.append("             address: ").append(getAddress()).append("\n");
        sb.append("             country: ").append(getCountry()).append("\n");
        sb.append("            currency: ").append(getCurrency()).append("\n");
        sb.append("               alias: ").append(getAlias()).append("\n");
        sb.append("                name: ").append(getName()).append("\n");
        sb.append("              market: ").append(getMarket()).append("\n");
        sb.append("            industry: ").append(getIndustry()).append("\n");
        sb.append("               group: ").append(getGroup()).append("\n");
        sb.append("                code: ").append(getCode()).append("\n");
        sb.append("  shares outstanding: ").append(getSharesOutstanding()).append("\n");
        sb.append("           par value: ").append(getParValue()).append("\n");
        sb.append("          book value: ").append(getBookValue()).append("\n");
        sb.append("           favourite: ").append(isFavourite()).append("\n");
        sb.append("               index: ").append(isIndex()).append("\n");
        sb.append("continuous quotation: ").append(isContinuousQuotation()).append("\n");
        sb.append("        year results: ").append(getYearResults(0)).append(", ").append(getYearResults(1)).append(", ").append(getYearResults(2)).append(", ").append(getYearResults(3)).append("\n");
        sb.append("        sales income: ").append(getSalesIncome(0)).append(", ").append(getSalesIncome(1)).append(", ").append(getSalesIncome(2)).append(", ").append(getSalesIncome(3)).append("\n");
        sb.append("                 ebt: ").append(getEbt(0)).append(", ").append(getEbt(1)).append(", ").append(getEbt(2)).append(", ").append(getEbt(3)).append("\n");
        sb.append("                 eat: ").append(getEat(0)).append(", ").append(getEat(1)).append(", ").append(getEat(2)).append(", ").append(getEat(3)).append("\n");
        sb.append("           tick size: ").append(getTickSize()).append("\n");
        sb.append("              web id: ").append(getWebId()).append("\n");
        sb.append(" local database only: ").append(isLocalDatabaseOnly()).append("\n");
        return sb.toString();
    }

    void delete() {
        deleted = true;
        dirty = true;
    }

    void dump() {
        System.out.println(Arrays.toString(masterDescriptor));
    }

    byte[] getMasterDescriptor() {
        return masterDescriptor;
    }

    boolean isDeleted() {
        return deleted;
    }

    boolean isDirty() {
        return dirty || quotes != null;
    }

    void setName(final String name) {
        AmibrokerCodec.writeString(masterDescriptor, MasterFile.SYMBOL_0, MasterFile.SYMBOL_LEN, name);
        AmibrokerCodec.writeString(quotesDescriptor, QuotesFile.SYMBOL_0, QuotesFile.SYMBOL_LEN, name);
        dirty = true;
    }

}
