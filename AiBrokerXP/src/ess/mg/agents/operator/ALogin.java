package ess.mg.agents.operator;

import aibroker.util.Moment;
import ess.mg.MgContext;
import ess.mg.actions.Action;
import ess.mg.actions.ActionResult;
import ess.mg.agents.Agent;
import ess.mg.driver.model.Transaction;
import ess.mg.driver.model.Transactions;

public class ALogin extends Action<ActionResult> {

    private Moment  epoch;
    private boolean readTransactions;

    public ALogin(final Agent performer) {
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
        getAgent().getContext().getTransactions().clear();
        outer: for (int point = 0; point < 100; point += 20) {
            final Transactions foo = getAgent().getDriver().fetchTransactions(point);
            for (final Transaction bar : foo) {
                if (epoch.compareTo(bar.getMoment()) <= 0) {
                    getAgent().getContext().getTransactions().add(bar);
                } else {
                    break outer;
                }
            }
        }
    }

    @Override
    protected ActionResult execute() {
        final MgContext state = getAgent().getDriver().login();
        getAgent().setContext(state);
        if (readTransactions) {
            readTransactions();
        }
        state.setWage(getAgent().getDriver().fetchWage());
        state.setShares(getAgent().getDriver().fetchShares());
        state.setEuroGoldExchangeRate(getAgent().getDriver().fetchEuroGoldExchangeRate());
        getAgent().getDriver().fetchGlobalContext(state);

        final ActionResult result = new ActionResult();
        result.setSuccessful(true);
        return result;
    }

}
