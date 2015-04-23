package ess.mg.agents.operator;

import ess.common.actions.Action;
import ess.mg.agents.MgAgent;
import ess.mg.agents.dto.ReferralFightResult;

public class AReferralFight extends Action<MgAgent, ReferralFightResult> {

    public AReferralFight(final MgAgent performer) {
        super(performer);
    }

    @Override
    protected ReferralFightResult execute() {
        return getAgent().getDriver().referralFightTrainer();
    }

}
