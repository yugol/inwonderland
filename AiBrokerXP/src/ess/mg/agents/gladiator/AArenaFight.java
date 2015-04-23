package ess.mg.agents.gladiator;

import ess.common.actions.Action;
import ess.mg.agents.MgAgent;
import ess.mg.agents.dto.ArenaFightResult;

public class AArenaFight extends Action<MgAgent, ArenaFightResult> {

    public AArenaFight(final MgAgent agent) {
        super(agent);
    }

    @Override
    protected ArenaFightResult execute() {
        // find arena opponent
        // log opponent position
        // fight opponent
        // read status
        // ... keep searching
        return null;
    }

}
