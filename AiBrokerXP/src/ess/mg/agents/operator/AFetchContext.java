package ess.mg.agents.operator;

import aibroker.util.Moment;
import ess.mg.MgContext;
import ess.mg.actions.Action;
import ess.mg.actions.ActionResult;
import ess.mg.agents.Agent;
import ess.mg.driver.model.Transaction;
import ess.mg.driver.model.Transactions;

public class AFetchContext extends Action<ActionResult> {

    private Moment  epoch;
    private boolean readTransactions;

    public AFetchContext(final Agent performer) {
        super(performer);
    }

    public Moment getEpoch() {
        return epoch;
    }

    public boolean isReadTransactions() {
        return readTransactions;
    }

    public void setEpoch(final Moment epoch) {
        this.epoch = epoch;
    }

    public void setReadTransactions(final boolean readTransactions) {
        this.readTransactions = readTransactions;
    }

    private void readTransactions() {
        getAgent().getGlobal().getTransactions().clear();
        outer: for (int point = 0; point < 100; point += 20) {
            final Transactions foo = getAgent().getDriver().fetchTransactions(point);
            for (final Transaction bar : foo) {
                if (epoch.compareTo(bar.getMoment()) <= 0) {
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
        final MgContext state = getAgent().getGlobal();

        getAgent().getDriver().login();
        if (readTransactions) {
            readTransactions();
        }
        state.setWage(getAgent().getDriver().fetchWage());
        state.setShares(getAgent().getDriver().fetchShares());
        state.setEuroGoldExchangeRate(getAgent().getDriver().fetchEuroGoldExchangeRate());
        getAgent().getDriver().fetchGlobalContext(state);

        result.setSuccessful(true);
        return result;
    }

}
