package ess.mg.agents.basic;

import ess.mg.agents.Agent;
import ess.mg.agents.actions.Action;
import ess.mg.agents.actions.WorkResult;

public class AWork extends Action<WorkResult> {

    public AWork(final Agent performer, final int timeout) {
        super(performer, timeout);
    }

    @Override
    protected WorkResult execute() {
        return getAgent().getDriver().work(null);
    }

}
