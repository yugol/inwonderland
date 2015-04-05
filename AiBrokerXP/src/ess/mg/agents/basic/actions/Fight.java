package ess.mg.agents.basic.actions;

import ess.mg.actions.Action;
import ess.mg.actions.ActionResult;
import ess.mg.agents.Agent;

public class Fight extends Action<ActionResult> {

    public Fight(final Agent performer, final int timeout) {
        super(performer, timeout);
    }

    @Override
    protected ActionResult execute() {
        return getAgent().getDriver().referralFight();
    }

}
