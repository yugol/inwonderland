package aibroker.agents.manager.util;

public class SettlementMonth {

    public final int    index;
    public final String monthName;

    public SettlementMonth(final String monthName, final int index) {
        super();
        this.index = index;
        this.monthName = monthName;
    }

    @Override
    public String toString() {
        return monthName;
    }

}
