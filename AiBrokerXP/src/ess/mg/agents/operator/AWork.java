package ess.mg.agents.operator;

import ess.common.actions.Action;
import ess.mg.agents.MgAgent;
import ess.mg.agents.dto.WorkResult;

public class AWork extends Action<MgAgent, WorkResult> {

    public AWork(final MgAgent performer) {
        super(performer);
    }

    @Override
    protected WorkResult execute() {
        return getAgent().getDriver().work(getAgent().getContext(), null);
    }

}
