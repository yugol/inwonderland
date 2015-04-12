package ess.common;

import ess.common.driver.model.Shares;

public abstract class EssContext {

    private int    activeUsersCount;

    private Shares shares;

    private double euroAmount = 0;
    private double goldAmount = 0;
    private double ronAmount  = 0;

    public int getActiveUsersCount() {
        return activeUsersCount;
    }

    public double getEuroAmount() {
        return euroAmount;
    }

    public double getGoldAmount() {
        return goldAmount;
    }

    public double getRonAmount() {
        return ronAmount;
    }

    public Shares getShares() {
        return shares;
    }

    public void setActiveUsersCount(final int activeUsersCount) {
        this.activeUsersCount = activeUsersCount;
    }

    public void setEuroAmount(final double euroAmount) {
        this.euroAmount = euroAmount;
    }

    public void setGoldAmount(final double goldAmount) {
        this.goldAmount = goldAmount;
    }

    public void setRonAmount(final double ronAmount) {
        this.ronAmount = ronAmount;
    }

    public void setShares(final Shares shares) {
        this.shares = shares;
    }

}
