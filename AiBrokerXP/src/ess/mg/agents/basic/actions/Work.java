package ess.mg.agents.basic.actions;

import ess.mg.actions.Action;
import ess.mg.actions.WorkResult;
import ess.mg.agents.Agent;

public class Work extends Action<WorkResult> {

    public Work(final Agent performer, final int timeout) {
        super(performer, timeout);
    }

    @Override
    protected WorkResult execute() {
        return getAgent().getDriver().work(null);
    }

}
