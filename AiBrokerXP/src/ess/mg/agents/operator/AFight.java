package ess.mg.agents.operator;

import ess.mg.actions.Action;
import ess.mg.actions.FightResult;
import ess.mg.agents.Agent;

public class AFight extends Action<FightResult> {

    public AFight(final Agent performer) {
        super(performer);
    }

    @Override
    protected FightResult execute() {
        return getAgent().getDriver().referralFightTrainer();
    }

}
