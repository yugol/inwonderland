package ess.mg.agents.sampler;

import ess.mg.actions.Action;
import ess.mg.actions.ActionResult;
import ess.mg.agents.Agent;

public class ASample extends Action<ActionResult> {

    public ASample(final Agent performer, final int timeout) {
        super(performer, timeout);
    }

    @Override
    protected ActionResult execute() {
        final ActionResult result = new ActionResult();
        getAgent().getDriver().login();
        getAgent().getDriver().fetchWage();
        getAgent().getDriver().fetchShares();
        getAgent().getDriver().fetchEuroGoldExchangeRate();
        getAgent().getDriver().fetchGoldRonExchangeRate();
        result.setSuccessful(true);
        return result;
    }
}
