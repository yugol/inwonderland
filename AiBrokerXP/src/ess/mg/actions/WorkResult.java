package ess.mg.actions;

import ess.common.Price;
import ess.common.actions.ActionResult;

public class WorkResult extends ActionResult {

    private Price wage;

    public Price getWage() {
        return wage;
    }

    public void setWage(final Price wage) {
        this.wage = wage;
    }

}
