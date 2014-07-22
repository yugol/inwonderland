package aibroker.engines;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import aibroker.model.domains.Market;

public class Order {

    private final String         id        = UUID.randomUUID().toString();
    private final Market         market    = Market.FUTURES;
    private final Operation      operation;
    private final String         symbol;
    private final int            volume;
    private float                price;
    private String               observations;
    private final Term           term      = Term.DAY;

    private final List<Position> execution = new ArrayList<Position>();

    public Order(final Operation operation, final String symbol, final int volume) {
        this.operation = operation;
        this.symbol = symbol;
        this.volume = volume;
    }

    public void addExecution(final Transaction t) {
        final Position p = new Position(t, operation.toSide());
        execution.add(p);
    }

    @Override
    public boolean equals(final Object other) {
        if (other != null && other instanceof Order) { return id.equals(((Order) other).id); }
        return false;
    }

    public Operation getAction() {
        return operation;
    }

    public List<Position> getExecution() {
        return execution;
    }

    public int getExecutionVolume() {
        int volumes = 0;
        for (final Position p : execution) {
            volumes += p.getVolume();
        }
        return volumes;
    }

    public String getId() {
        return id;
    }

    public Market getMarket() {
        return market;
    }

    public String getObservations() {
        return observations;
    }

    public float getPrice() {
        return price;
    }

    public String getSymbol() {
        return symbol;
    }

    public Term getTerm() {
        return term;
    }

    public int getVolume() {
        return volume;
    }

    public boolean isExecuted() {
        return volume <= getExecutionVolume();
    }

    public void setObservations(final String observations) {
        this.observations = observations;
    }

    public void setPrice(final float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Order [id=" + id + ", operation=" + operation + ", symbol=" + symbol + ", volume=" + volume + "]";
    }

}
