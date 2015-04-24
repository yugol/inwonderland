package ess.mg.agents.dto;

import ess.common.actions.ActionResult;

public class ArenaFightResult extends ActionResult {

    private int     opponentIndex;
    private boolean opponentFound;

    public ArenaFightResult(final int opponentIndex) {
        this.opponentIndex = opponentIndex;
    }

    public int getOpponentIndex() {
        return opponentIndex;
    }

    public void incrementOpponentIndex() {
        opponentIndex += 1;
    }

    public boolean isOpponentFound() {
        return opponentFound;
    }

    public void setOpponentFound(final boolean flag) {
        opponentFound = flag;
    }

}
