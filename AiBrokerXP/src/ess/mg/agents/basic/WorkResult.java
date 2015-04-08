package ess.mg.agents.basic;

import ess.Price;
import ess.mg.agents.ActionResult;

public class WorkResult extends ActionResult {

    private Price wage;

    public Price getWage() {
        return wage;
    }

    public void setWage(final Price wage) {
        this.wage = wage;
    }

}
