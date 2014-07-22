package aibroker.engines;

public class Position extends Transaction {

    private final Side side;

    public Position(final Transaction t, final Side side) {
        super(t.getSymbol());
        setMoment(t.getMoment());
        setPrice(t.getPrice());
        setVolume(t.getVolume());
        this.side = side;
    }

    public Side getSide() {
        return side;
    }

    @Override
    public String toString() {
        return "Position [" + side + ", " + getMoment().toIsoDatetime() + ", price=" + getPrice() + ", volume=" + getVolume() + "]";
    }

}
