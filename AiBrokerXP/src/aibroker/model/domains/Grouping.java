package aibroker.model.domains;

public enum Grouping {

    OHLC ("OHLC"),
    TICK ("Tick")

    ;

    private final String uiName;

    private Grouping(final String name) {
        uiName = name;
    }

    @Override
    public String toString() {
        return uiName;
    }

}
