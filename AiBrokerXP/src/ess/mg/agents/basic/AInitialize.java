package ess.mg.agents.basic;

import aibroker.util.Moment;
import ess.mg.agents.Agent;
import ess.mg.agents.actions.Action;
import ess.mg.agents.actions.ActionResult;
import ess.mg.driver.model.Transaction;
import ess.mg.driver.model.Transactions;

public class AInitialize extends Action<ActionResult> {

    private final Moment history;

    public AInitialize(final Moment history, final Agent performer) {
        super(performer);
        this.history = history;
    }

    public AInitialize(final Moment history, final Agent performer, final int timeout) {
        super(performer, timeout);
        this.history = history;
    }

    private void readTransactions() {
        getAgent().getGlobal().getTransactions().clear();
        outer: for (int point = 0; point < 100; point += 20) {
            final Transactions foo = getAgent().getDriver().fetchTransactions(point);
            for (final Transaction bar : foo) {
                if (history.compareTo(bar.getMoment()) <= 0) {
                    getAgent().getGlobal().getTransactions().add(bar);
                } else {
                    break outer;
                }
            }
        }
    }

    @Override
    protected ActionResult execute() {
        final ActionResult result = new ActionResult();
        getAgent().getDriver().login();
        readTransactions();
        getAgent().getDriver().fetchGlobalContext(getAgent().getGlobal());
        result.setSuccessful(true);
        return result;
    }

}
