package ess.mg.agents.operator;

import ess.common.actions.Action;
import ess.mg.MgMarket;
import ess.mg.actions.PurchaseResult;
import ess.mg.agents.MgAgent;
import ess.mg.goods.Goods;

public class ABuyGoods extends Action<MgAgent, PurchaseResult> {

    private Goods    goods;
    private MgMarket market = MgMarket.LOCAL;

    public ABuyGoods(final MgAgent performer) {
        super(performer);
    }

    public Goods getGoods() {
        return goods;
    }

    public MgMarket getMarket() {
        return market;
    }

    public void setGoods(final Goods goods) {
        this.goods = goods;
    }

    public void setMarket(final MgMarket market) {
        this.market = market;
    }

    @Override
    protected PurchaseResult execute() {
        return getAgent().getDriver().buyGoods(goods, market);
    }

}
