package aibroker.model.domains;

public enum Feed {

    CACHE ("Cached"),
    LIVE ("Live"),
    NORM ("Normalised"),
    ORIG ("Original"),

    ;

    private final String uiName;

    private Feed(final String name) {
        uiName = name;
    }

    @Override
    public String toString() {
        return uiName;
    }

}
