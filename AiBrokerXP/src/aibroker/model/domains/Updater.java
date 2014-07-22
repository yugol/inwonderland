package aibroker.model.domains;

public enum Updater {

    BVB_REG_DAILY_BASE ("BVB Regular Daily Base"),
    BVB_REG_DAILY_NORM ("BVB Regular Daily Normalized"),
    YAHOO_DAILY ("Yahoo Daily"),
    SIBEX_FUT_TICK ("SIBEX Futures Intraday Ticks"),
    CACHED ("Cashed Sequence"),
    NONE ("- Select One -")

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
