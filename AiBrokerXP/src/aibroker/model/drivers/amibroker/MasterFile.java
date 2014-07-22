package aibroker.model.drivers.amibroker;

class MasterFile {

    static final byte[] MARKER                 = { 66, 82, 79, 75, 77, 65, 83, 84 };

    static final int    MARKER_0               = 0;
    static final int    MARKER_LEN             = MARKER.length;
    static final int    RECORD_COUNT_0         = MARKER_0 + MARKER_LEN;
    static final int    RECORD_COUNT_LEN       = 4;
    static final int    RECORD_0               = RECORD_COUNT_0 + RECORD_COUNT_LEN;
    static final int    RECORD_LEN             = QuotesFile.DESCRIPTOR_LEN;

    // record format

    static final int    MARGIN_DEPOSIT_0       = 12 - RECORD_0;
    static final int    MARGIN_DEPOSIT_LEN     = 4;
    static final int    POINT_VALUE_0          = 16 - RECORD_0;
    static final int    POINT_VALUE_LEN        = 4;
    static final int    ROUND_LOT_SIZE_0       = 20 - RECORD_0;
    static final int    ROUND_LOT_SIZE_LEN     = 4;

    static final int    FULL_NAME_0            = 26 - RECORD_0;
    static final int    FULL_NAME_LEN          = 64;
    static final int    ADDRESS_0              = 90 - RECORD_0;
    static final int    ADDRESS_LEN            = 128;
    static final int    COUNTRY_0              = 218 - RECORD_0;
    static final int    COUNTRY_LEN            = 18;

    static final int    CURRENCY_0             = 258 - RECORD_0;
    static final int    CURRENCY_LEN           = 18;
    static final int    ALIAS_0                = 266 - RECORD_0;
    static final int    ALIAS_LEN              = 16;
    static final int    SYMBOL_0               = 282 - RECORD_0;
    static final int    SYMBOL_LEN             = 26;

    static final int    MARKET_FLAGS_0         = 308 - RECORD_0;
    static final int    MARKET_FLAGS_1         = 309 - RECORD_0;
    static final int    GROUP_FLAGS_0          = 310 - RECORD_0;
    static final int    GROUP_FLAGS_1          = 311 - RECORD_0;
    static final int    CODE_0                 = 312 - RECORD_0;
    static final int    CODE_LEN               = 4;
    static final int    SHARES_OUTSTANDING_0   = 316 - RECORD_0;
    static final int    SHARES_OUTSTANDING_LEN = 4;
    static final int    PAR_VALUE_0            = 320 - RECORD_0;
    static final int    PAR_VALUE_LEN          = 4;
    static final int    BOOK_VALUE_0           = 324 - RECORD_0;
    static final int    BOOK_VALUE_LEN         = 4;

    static final int    INDUSTRY_FLAGS_0       = 352 - RECORD_0;
    static final int    INDUSTRY_FLAGS_1       = 353 - RECORD_0;
    static final int    INDUSTRY_FLAGS_2       = 354 - RECORD_0;
    static final int    INDUSTRY_FLAGS_3       = 355 - RECORD_0;
    static final int    FLAGS_ALPHA_0          = 356 - RECORD_0;
    static final int    FLAGS_ALPHA_1          = 357 - RECORD_0;
    static final int    FLAGS_ALPHA_2          = 358 - RECORD_0;
    static final int    FLAGS_ALPHA_3          = 359 - RECORD_0;
    static final int    YEAR_RESULTS_0         = 360 - RECORD_0;
    static final int    YEAR_RESULTS_LEN       = 16;
    static final int    SALES_INCOME_0         = 376 - RECORD_0;
    static final int    SALES_INCOME_LEN       = 16;
    static final int    EBT_0                  = 392 - RECORD_0;
    static final int    EBT_LEN                = 16;
    static final int    EAT_0                  = 408 - RECORD_0;
    static final int    EAT_LEN                = 16;
    static final int    TICK_SIZE_0            = 424 - RECORD_0;
    static final int    TICK_SIZE_LEN          = 4;
    static final int    WEB_ID_0               = 428 - RECORD_0;
    static final int    WEB_ID_LEN             = 10;
    static final int    FLAGS_BETA_0           = 438 - RECORD_0;
    static final int    FLAGS_BETA_LEN         = 1;

    static byte[] initBuffer(final int recordCount) {
        final byte[] buffer = new byte[RECORD_0 + recordCount * RECORD_LEN];
        System.arraycopy(MARKER, 0, buffer, MARKER_0, MARKER_LEN);
        AmibrokerCodec.writeLittleEndianInteger(buffer, MasterFile.RECORD_COUNT_0, recordCount);
        return buffer;
    }

    static void writeSequence(final int tNo, final AmibrokerSequence sequence, final byte[] buffer) {
        final int recordStartIndex = RECORD_0 + tNo * RECORD_LEN;
        System.arraycopy(sequence.getMasterDescriptor(), 0, buffer, recordStartIndex, RECORD_LEN);
    }

    private MasterFile() {
    }

}
