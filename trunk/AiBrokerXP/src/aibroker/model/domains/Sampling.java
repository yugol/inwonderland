package aibroker.model.domains;

public enum Sampling {

    NONE ("", false),
    SECOND ("Second", true),
    MIN_1 ("1 Minute", true),
    MIN_5 ("5 Minutes", true),
    MIN_10 ("10 Minutes", true),
    MIN_15 ("15 Minutes", true),
    MIN_30 ("30 Minutes", true),
    HOURLY ("Hourly", true),
    DAILY ("Daily", false),
    WEEKLY ("Weekly", false),
    MONTHLY ("Monthly", false),
    QUARTERLY ("Quarterly", false),
    YEARLY ("Yearly", false)

    ;

    private final String uiName;
    public final boolean intraday;

    private Sampling(final String name, final boolean intraday) {
        uiName = name;
        this.intraday = intraday;
    }

    public Boolean isIntraday() {
        switch (this) {
            case DAILY:
            case WEEKLY:
            case MONTHLY:
            case QUARTERLY:
            case YEARLY:
                return false;

            case SECOND:
            case MIN_1:
            case MIN_10:
            case MIN_15:
            case MIN_30:
            case MIN_5:
            case HOURLY:
                return true;

            case NONE:
                break;
        }
        return null;
    }

    @Override
    public String toString() {
        return uiName;
    }

}
