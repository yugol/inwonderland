package ess.mg.optimizers;

import ess.common.Price;
import ess.mg.MgContext;

public class ActivityResult {

    private final Price  grossReturn;
    private final double energy;

    public ActivityResult(final Price grossReturn, final double energy) {
        this.grossReturn = grossReturn;
        this.energy = energy;
    }

    public double getEnergy() {
        return energy;
    }

    public Price getGrossReturn() {
        return grossReturn;
    }

    public Price getNetReturn() {
        return MgContext.netReturn(grossReturn);
    }

    @Override
    public String toString() {
        return "net = " + getNetReturn() + " gross = " + grossReturn + " energy = " + energy;
    }

}
