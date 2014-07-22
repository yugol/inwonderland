package aibroker.model.domains;

public enum Feed {

    CACHE ("Cached"),
    ORIG ("Original"),
    LIVE ("Live")

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
