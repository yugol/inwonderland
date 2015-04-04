package ess.mg.driver;

import ess.mg.actions.PurchaseResult;
import ess.mg.goods.Goods;
import ess.mg.goods.food.Wine;
import ess.mg.markets.MgMarket;

public class MgWebShopperTest {

    public static void main(final String... args) {
        final Goods goods = new Wine();
        final MgWebShopper shopper = new MgWebDriver();
        shopper.login();
        final PurchaseResult result = shopper.buyGoods(goods, MgMarket.LOCAL);
        System.out.println(result.isSuccessful() + " " + result.getPrice());
    }

}
