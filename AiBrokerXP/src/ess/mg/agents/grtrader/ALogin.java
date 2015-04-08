package ess.mg.agents.grtrader;

import ess.mg.agents.Action;
import ess.mg.agents.ActionResult;
import ess.mg.agents.Agent;

public class ALogin extends Action<ActionResult> {

    public ALogin(final Agent performer) {
        super(performer);
    }

    public ALogin(final Agent performer, final int timeout) {
        super(performer, timeout);
    }

    @Override
    protected ActionResult execute() {
        final ActionResult result = new ActionResult();
        getAgent().getDriver().login();
        result.setSuccessful(true);
        return result;
    }

}
