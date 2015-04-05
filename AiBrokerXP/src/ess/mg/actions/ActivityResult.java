package ess.mg.actions;

import ess.Price;
import ess.mg.MG;

public class ActivityResult extends ActionResult {

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
        return MG.netReturn(grossReturn);
    }

    @Override
    public String toString() {
        return "net = " + getNetReturn() + " gross = " + grossReturn + " energy = " + energy;
    }

}