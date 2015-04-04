package ess.mg.agents.basic.actions;

import ess.mg.actions.Action;
import ess.mg.actions.ActionResult;
import ess.mg.agents.Agent;

public class Initialize extends Action<ActionResult> {

    public Initialize(final Agent performer) {
        super(performer);
    }

    public Initialize(final Agent performer, final int timeout) {
        super(performer, timeout);
    }

    @Override
    protected ActionResult execute() {
        final ActionResult result = new ActionResult();
        getAgent().getDriver().login();
        getAgent().getDriver().fetchFightCount(getAgent().getGlobal());
        getAgent().getDriver().fetchWorkCount(getAgent().getGlobal());
        getAgent().getDriver().fetchInventory(getAgent().getGlobal());
        getAgent().getDriver().fetchGlobalContext(getAgent().getGlobal());
        result.setSuccessful(true);
        return result;
    }

}
