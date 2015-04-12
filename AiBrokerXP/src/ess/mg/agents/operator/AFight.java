package ess.mg.agents.operator;

import ess.common.actions.Action;
import ess.mg.actions.FightResult;
import ess.mg.agents.MgAgent;

public class AFight extends Action<MgAgent, FightResult> {

    public AFight(final MgAgent performer) {
        super(performer);
    }

    @Override
    protected FightResult execute() {
        return getAgent().getDriver().referralFightTrainer();
    }

}
