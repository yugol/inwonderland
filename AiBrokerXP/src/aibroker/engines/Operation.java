package aibroker.engines;

public enum Operation {

    BUY, SELL;

    public Side toSide() {
        switch (this) {
            case BUY:
                return Side.LONG;
            case SELL:
                return Side.SHORT;
            default:
                return null;
        }
    }

}
