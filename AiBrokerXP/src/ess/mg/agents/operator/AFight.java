package ess.mg.agents.operator;

import ess.common.actions.Action;
import ess.mg.agents.MgAgent;
import ess.mg.agents.dto.FightResult;

public class AFight extends Action<MgAgent, FightResult> {

    public AFight(final MgAgent performer) {
        super(performer);
    }

    @Override
    protected FightResult execute() {
        return getAgent().getDriver().referralFightTrainer();
    }

}
