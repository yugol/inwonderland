package ess.mg.agents.basic;

import ess.mg.agents.Action;
import ess.mg.agents.ActionResult;
import ess.mg.agents.Agent;

public class AFight extends Action<ActionResult> {

    public AFight(final Agent performer, final int timeout) {
        super(performer, timeout);
    }

    @Override
    protected ActionResult execute() {
        return getAgent().getDriver().referralFight();
    }

}
