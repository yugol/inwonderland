package ess.mg.agents.basic;

import ess.mg.agents.Action;
import ess.mg.agents.Agent;

public class AWork extends Action<WorkResult> {

    public AWork(final Agent performer, final int timeout) {
        super(performer, timeout);
    }

    @Override
    protected WorkResult execute() {
        return getAgent().getDriver().work(null);
    }

}
