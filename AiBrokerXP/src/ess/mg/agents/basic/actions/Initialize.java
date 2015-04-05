package ess.mg.agents.basic.actions;

import aibroker.util.Moment;
import ess.mg.actions.Action;
import ess.mg.actions.ActionResult;
import ess.mg.agents.Agent;
import ess.mg.driver.model.Transaction;
import ess.mg.driver.model.Transactions;

public class Initialize extends Action<ActionResult> {

    private final Moment history;

    public Initialize(final Moment history, final Agent performer) {
        super(performer);
        this.history = history;
    }

    public Initialize(final Moment history, final Agent performer, final int timeout) {
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
        getAgent().getDriver().fetchGlobalContext(getAgent().getGlobal());
        readTransactions();
        result.setSuccessful(true);
        return result;
    }

}
