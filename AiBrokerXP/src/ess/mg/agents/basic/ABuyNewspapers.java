package ess.mg.agents.basic;

import ess.Price;
import ess.mg.agents.Agent;
import ess.mg.agents.actions.Action;
import ess.mg.agents.actions.ActionResult;
import ess.mg.agents.actions.PurchaseResult;

public class ABuyNewspapers extends Action<ActionResult> {

    private int count;

    public ABuyNewspapers(final int count, final Agent agent, final int timeout) {
        super(agent, timeout);
        this.count = count;
    }

    @Override
    protected ActionResult execute() {
        final ActionResult result = new ActionResult();
        Price maxPrice = Price.ron(0.051);
        while (count > 0) {
            for (int index = 1; index <= 30; ++index) {
                final PurchaseResult purchaseResult = getAgent().getDriver().buyNewspaper(index, maxPrice);
                if (purchaseResult.isSuccessful()) {
                    count--;
                    if (count <= 0) {
                        break;
                    }
                }
            }
            maxPrice = maxPrice.add(Price.ron(0.01));
            if (maxPrice.getAmount() > 0.1) {
                break;
            }
        }
        result.setSuccessful(count <= 0);
        return result;
    }

}
