package ess.mg.actions;

import ess.Price;

public class PurchaseResult extends ActionResult {

    private Price price;

    public Price getPrice() {
        return price;
    }

    public void setPrice(final Price price) {
        this.price = price;
    }

}
