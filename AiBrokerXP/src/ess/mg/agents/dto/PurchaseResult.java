package ess.mg.agents.dto;

import ess.common.Price;
import ess.common.actions.ActionResult;

public class PurchaseResult extends ActionResult {

    private Price price;

    public Price getPrice() {
        return price;
    }

    public void setPrice(final Price price) {
        this.price = price;
    }

}
