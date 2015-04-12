package ess.common;

import ess.common.driver.model.Shares;

public abstract class EssContext {

    private Integer activeUsersCount;

    private Shares  shares;
    private Double  euroGoldExchangeRate;
    private Double  goldRonExchangeRate;

    private Double  euroAmount;
    private Double  goldAmount;
    private Double  ronAmount;

    public Integer getActiveUsersCount() {
        return activeUsersCount;
    }

    public Double getEuroAmount() {
        return euroAmount;
    }

    public Double getEuroGoldExchangeRate() {
        return euroGoldExchangeRate;
    }

    public Double getGoldAmount() {
        return goldAmount;
    }

    public Double getGoldRonExchangeRate() {
        return goldRonExchangeRate;
    }

    public Double getRonAmount() {
        return ronAmount;
    }

    public Shares getShares() {
        return shares;
    }

    public void setActiveUsersCount(final Integer activeUsersCount) {
        this.activeUsersCount = activeUsersCount;
    }

    public void setEuroAmount(final Double euroAmount) {
        this.euroAmount = euroAmount;
    }

    public void setEuroGoldExchangeRate(final Double euroGoldExchangeRate) {
        this.euroGoldExchangeRate = euroGoldExchangeRate;
    }

    public void setGoldAmount(final Double goldAmount) {
        this.goldAmount = goldAmount;
    }

    public void setGoldRonExchangeRate(final Double goldRonExchangeRate) {
        this.goldRonExchangeRate = goldRonExchangeRate;
    }

    public void setRonAmount(final Double ronAmount) {
        this.ronAmount = ronAmount;
    }

    public void setShares(final Shares shares) {
        this.shares = shares;
    }

}
