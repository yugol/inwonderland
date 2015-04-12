package ess.mg.agents.operator;

import ess.common.Price;
import ess.common.actions.Action;
import ess.common.actions.ActionResult;
import ess.mg.agents.MgAgent;
import ess.mg.agents.dto.PurchaseResult;

public class ABuyNewspapers extends Action<MgAgent, ActionResult> {

    private int    papersLeftToBuy       = 0;
    private double referenceMaximumPrice = 0.051;

    public ABuyNewspapers(final MgAgent agent) {
        super(agent);
    }

    public int getPapersLeftToBuy() {
        return papersLeftToBuy;
    }

    public double getReferenceMaximumPrice() {
        return referenceMaximumPrice;
    }

    public void setPapersLeftToBuy(final int papersLeftToBuy) {
        this.papersLeftToBuy = papersLeftToBuy;
    }

    public void setReferenceMaximumPrice(final double referenceMaximumPrice) {
        this.referenceMaximumPrice = referenceMaximumPrice;
    }

    @Override
    protected ActionResult execute() {
        final ActionResult result = new ActionResult();
        Price maxPrice = Price.ron(getReferenceMaximumPrice());
        while (papersLeftToBuy > 0) {
            for (int index = 1; index <= 30; ++index) {
                final PurchaseResult purchaseResult = getAgent().getDriver().buyNewspaper(index, maxPrice);
                if (purchaseResult.isSuccessful()) {
                    papersLeftToBuy--;
                    if (papersLeftToBuy <= 0) {
                        break;
                    }
                }
            }
            maxPrice = maxPrice.add(Price.ron(0.01));
            if (maxPrice.getAmount() > 0.1) {
                break;
            }
        }
        result.setSuccessful(papersLeftToBuy <= 0);
        return result;
    }

}
