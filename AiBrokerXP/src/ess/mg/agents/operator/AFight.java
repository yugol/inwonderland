package ess.mg.agents.operator;

import ess.mg.actions.Action;
import ess.mg.actions.ActionResult;
import ess.mg.agents.Agent;

public class AFight extends Action<ActionResult> {

    public AFight(final Agent performer) {
        super(performer);
    }

    @Override
    protected ActionResult execute() {
        return getAgent().getDriver().referralFight();
    }

}
