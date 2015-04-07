package ess.mg.agents.actions;

import ess.Price;

public class WorkResult extends ActionResult {

    private Price wage;

    public Price getWage() {
        return wage;
    }

    public void setWage(final Price wage) {
        this.wage = wage;
    }

}
