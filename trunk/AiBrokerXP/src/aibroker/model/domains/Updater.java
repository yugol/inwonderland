package aibroker.model.domains;

public enum Updater {

    BVB_REG_DAILY_BASE ("BVB Regular Daily Base"),
    BVB_REG_DAILY_NORM ("BVB Regular Daily Normalized"),
    YAHOO_DAILY ("Yahoo Daily"),
    CACHED ("Cashed Sequence"),
    CACHED_SIBEX_FUT_TICK ("SIBEX Futures Intraday Ticks"),
    NONE ("- Select One -"),
    SIBEX_FUT_TICK ("(deprecated) SIBEX Futures Intraday Ticks"),

    ;

    private final String uiName;

    private Updater(final String name) {
        uiName = name;
    }

    @Override
    public String toString() {
        return uiName;
    }

}
