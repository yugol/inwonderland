package ess.mg.agents.basic;

import ess.Price;
import ess.mg.agents.ActionResult;

public class PurchaseResult extends ActionResult {

    private Price price;

    public Price getPrice() {
        return price;
    }

    public void setPrice(final Price price) {
        this.price = price;
    }

}
