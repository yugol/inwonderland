package aibroker.model.drivers.amibroker;

import aibroker.model.Ohlc;

class QuotesFile {

    static final byte[] MARKER                 = { 66, 82, 79, 75, 68, 65, 84, 52 };

    static final int    MARKER_0               = 0;
    static final int    MARKER_LEN             = 8;
    static final int    DESCRIPTOR_0           = MARKER_0 + MARKER_LEN;
    static final int    DESCRIPTOR_LEN         = 454;
    static final int    RECORD_COUNT_0         = DESCRIPTOR_0 + DESCRIPTOR_LEN;
    static final int    RECORD_COUNT_LEN       = 4;
    static final int    QUOTE_0                = RECORD_COUNT_0 + RECORD_COUNT_LEN;
    static final int    QUOTE_LEN              = 30;

    // descriptor format

    static final int    MARGIN_DEPOSIT_0       = 8 - DESCRIPTOR_0;
    static final int    MARGIN_DEPOSIT_LEN     = MasterFile.MARGIN_DEPOSIT_LEN;
    static final int    POINT_VALUE_0          = 12 - DESCRIPTOR_0;
    static final int    POINT_VALUE_LEN        = MasterFile.POINT_VALUE_LEN;
    static final int    ROUND_LOT_SIZE_0       = 16 - DESCRIPTOR_0;
    static final int    ROUND_LOT_SIZE_LEN     = 4;

    static final int    FULL_NAME_0            = 22 - DESCRIPTOR_0;
    static final int    FULL_NAME_LEN          = 64;
    static final int    ADDRESS_0              = 86 - DESCRIPTOR_0;
    static final int    ADDRESS_LEN            = 128;
    static final int    COUNTRY_0              = 214 - DESCRIPTOR_0;
    static final int    COUNTRY_LEN            = 18;

    static final int    CURRENCY_0             = 254 - DESCRIPTOR_0;
    static final int    CURRENCY_LEN           = 18;
    static final int    ALIAS_0                = 262 - DESCRIPTOR_0;
    static final int    ALIAS_LEN              = 16;
    static final int    SYMBOL_0               = 278 - DESCRIPTOR_0;
    static final int    SYMBOL_LEN             = 26;

    static final int    GROUP_FLAGS_1          = 304 - DESCRIPTOR_0;
    static final int    GROUP_FLAGS_0          = 305 - DESCRIPTOR_0;
    static final int    MARKET_FLAGS_1         = 306 - DESCRIPTOR_0;
    static final int    MARKET_FLAGS_0         = 307 - DESCRIPTOR_0;
    static final int    CODE_0                 = 308 - DESCRIPTOR_0;
    static final int    CODE_LEN               = 4;
    static final int    SHARES_OUTSTANDING_0   = 312 - DESCRIPTOR_0;
    static final int    SHARES_OUTSTANDING_LEN = 4;
    static final int    PAR_VALUE_0            = 316 - DESCRIPTOR_0;
    static final int    PAR_VALUE_LEN          = 4;
    static final int    BOOK_VALUE_0           = 320 - DESCRIPTOR_0;
    static final int    BOOK_VALUE_LEN         = 4;

    static final int    INDUSTRY_3             = 348 - DESCRIPTOR_0;
    static final int    INDUSTRY_2             = 349 - DESCRIPTOR_0;
    static final int    INDUSTRY_1             = 350 - DESCRIPTOR_0;
    static final int    INDUSTRY_0             = 351 - DESCRIPTOR_0;
    static final int    INDUSTRY_LEN           = 4;
    static final int    FLAGS_ALPHA_3          = 352 - DESCRIPTOR_0;
    static final int    FLAGS_ALPHA_2          = 353 - DESCRIPTOR_0;
    static final int    FLAGS_ALPHA_1          = 354 - DESCRIPTOR_0;
    static final int    FLAGS_ALPHA_0          = 355 - DESCRIPTOR_0;
    static final int    YEAR_RESULTS_0         = 356 - DESCRIPTOR_0;
    static final int    YEAR_RESULTS_LEN       = 16;
    static final int    SALES_INCOME_0         = 372 - DESCRIPTOR_0;
    static final int    SALES_INCOME_LEN       = 16;
    static final int    EBT_0                  = 388 - DESCRIPTOR_0;
    static final int    EBT_LEN                = 16;
    static final int    EAT_0                  = 404 - DESCRIPTOR_0;
    static final int    EAT_LEN                = 16;
    static final int    TICK_SIZE_0            = 420 - DESCRIPTOR_0;
    static final int    TICK_SIZE_LEN          = 4;
    static final int    WEB_ID_0               = 424 - DESCRIPTOR_0;
    static final int    WEB_ID_LEN             = 10;
    static final int    FLAGS_BETA_0           = 434 - DESCRIPTOR_0;
    static final int    FLAGS_BETA_LEN         = 1;

    // record format

    static final int    DELTA_DATE             = 0;
    static final int    DELTA_CLOSE            = DELTA_DATE + 4;
    static final int    DELTA_OPEN             = DELTA_CLOSE + 4;
    static final int    DELTA_HIGH             = DELTA_OPEN + 4;
    static final int    DELTA_LOW              = DELTA_HIGH + 4;
    static final int    DELTA_VOLUME           = DELTA_LOW + 4;
    static final int    DELTA_OPEN_INT         = DELTA_VOLUME + 4;

    // end of file marker

    static final int    END_MARKER_LEN         = 4;

    static byte[] initBuffer(final AmibrokerSeq sequence) {
        final byte[] buffer = new byte[QUOTE_0 + sequence.getQuotes().size() * QUOTE_LEN + END_MARKER_LEN];
        System.arraycopy(MARKER, 0, buffer, MARKER_0, MARKER_LEN);
        System.arraycopy(sequence.getQuotesDescriptor(), 0, buffer, DESCRIPTOR_0, DESCRIPTOR_LEN);
        AmibrokerCodec.writeBigEndianInteger(buffer, QuotesFile.RECORD_COUNT_0, sequence.getQuotes().size());
        return buffer;
    }

    static Ohlc readQuote(final int qNo, final byte[] buffer) {
        final int offset = QUOTE_0 + qNo * QUOTE_LEN;
        return new Ohlc(
                AmibrokerCodec.readMoment(buffer, offset),
                AmibrokerCodec.readBigEndianFloat(buffer, offset + DELTA_CLOSE),
                AmibrokerCodec.readBigEndianFloat(buffer, offset + DELTA_OPEN),
                AmibrokerCodec.readBigEndianFloat(buffer, offset + DELTA_HIGH),
                AmibrokerCodec.readBigEndianFloat(buffer, offset + DELTA_LOW),
                AmibrokerCodec.readBigEndianInteger(buffer, offset + DELTA_VOLUME),
                AmibrokerCodec.readBigEndianInteger(buffer, offset + DELTA_OPEN_INT));
    }

    static void writeQuote(final int qNo, final Ohlc ohlc, final byte[] buffer) {
        final int offset = QUOTE_0 + qNo * QUOTE_LEN;
        AmibrokerCodec.writeMoment(buffer, offset, ohlc.moment);
        AmibrokerCodec.writeBigEndianFloat(buffer, offset + DELTA_CLOSE, ohlc.close);
        AmibrokerCodec.writeBigEndianFloat(buffer, offset + DELTA_OPEN, ohlc.open);
        AmibrokerCodec.writeBigEndianFloat(buffer, offset + DELTA_HIGH, ohlc.high);
        AmibrokerCodec.writeBigEndianFloat(buffer, offset + DELTA_LOW, ohlc.low);
        AmibrokerCodec.writeBigEndianInteger(buffer, offset + DELTA_VOLUME, ohlc.volume);
        AmibrokerCodec.writeBigEndianInteger(buffer, offset + DELTA_OPEN_INT, ohlc.openInt);
    }

}
