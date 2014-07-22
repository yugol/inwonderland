package aibroker.util.convenience;

public enum Stocks {

    // http://www.sibex.ro/index.php?p=specAllFut&l=ro

    DEDJIA_RON ("DJIA", "DEDJIA_RON", new int[] { 3, 6, 9, 12 }, 1, 500, 1.1F + 0.1F),
    EURUSD_RON ("EUR/USD", "EUR/USD_RON", new int[] { 3, 6, 9, 12 }, 10000, 300, 1.1F + 0.1F),
    SIBGOLD ("GOLD", "SIBGOLD", new int[] { 2, 4, 6, 8, 10, 12 }, 1, 500, 0.5F + 0.1F),
    SIF5 ("SIF5", "DESIF5", new int[] { 3, 6, 9, 12 }, 1000, 250, 0.6F)

    ;

    public static Stocks fromSymbol(final String symbol) {
        for (final Stocks stock : values()) {
            if (stock.symbol.equalsIgnoreCase(symbol)) { return stock; }
        }
        return null;
    }

    public static Stocks[] getRegulars() {
        return new Stocks[] { SIF5 };
    }

    public static boolean isSupported(final String symbol) {
        for (final Stocks stock : values()) {
            if (symbol.contains(stock.supportSymbol)) { return true; }
        }
        return false;
    }

    public final String supportSymbol;
    public final String symbol;
    public final int[]  settlements;
    public final float  multiplier;
    public final float  margin;

    public final float  fee;

    private Stocks(final String regularName, final String futuresName, final int[] settlements, final float multiplier, final float margin, final float fee) {
        supportSymbol = regularName;
        symbol = futuresName;
        this.multiplier = multiplier;
        this.margin = margin;
        this.fee = fee;
        this.settlements = settlements;
    }

}
