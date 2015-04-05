package ess.mg.driver.model;

public class Shares {

    private final Double eurAmount;
    private final Double goldAmount;
    private final Double sharePrice;

    public Shares(final Double eurAmount, final Double goldAmount, final Double sharePrice) {
        this.eurAmount = eurAmount;
        this.goldAmount = goldAmount;
        this.sharePrice = sharePrice;
    }

    public Double getEurAmount() {
        return eurAmount;
    }

    public Double getGoldAmount() {
        return goldAmount;
    }

    public Double getSharePrice() {
        return sharePrice;
    }

}
