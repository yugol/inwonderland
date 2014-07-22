package aibroker.model.domains;

public enum Market {

    FUTURES ("Futures"),
    REGS ("Regular")

    ;

    private final String uiName;

    private Market(final String name) {
        uiName = name;
    }

    public Boolean isDerivative() {
        switch (this) {
            case FUTURES:
                return true;
            case REGS:
                return false;
        }
        return null;
    }

    public boolean isRegular() {
        return !isDerivative();
    }

    @Override
    public String toString() {
        return uiName;
    }

}
