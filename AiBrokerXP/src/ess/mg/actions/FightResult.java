package ess.mg.actions;

public class FightResult extends ActionResult {

    private boolean maxFightCountReached = false;
    private boolean fighting             = false;

    public boolean isFighting() {
        return fighting;
    }

    public boolean isMaxFightCountReached() {
        return maxFightCountReached;
    }

    public void setFighting(final boolean fighting) {
        this.fighting = fighting;
    }

    public void setMaximumFightCountReached(final boolean maxFightCountReached) {
        this.maxFightCountReached = maxFightCountReached;
    }

}
