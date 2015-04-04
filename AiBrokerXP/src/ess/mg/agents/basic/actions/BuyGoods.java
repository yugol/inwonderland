package ess.mg.agents.basic.actions;

import ess.mg.actions.Action;
import ess.mg.actions.PurchaseResult;
import ess.mg.agents.Agent;
import ess.mg.goods.Goods;
import ess.mg.markets.MgMarket;

public class BuyGoods extends Action<PurchaseResult> {

    private final Goods goods;

    public BuyGoods(final Goods goods, final Agent performer, final int timeout) {
        super(performer, timeout);
        this.goods = goods;
    }

    @Override
    protected PurchaseResult execute() {
        return getAgent().getDriver().buyGoods(goods, MgMarket.LOCAL);
    }

}
