package ess.mg.driver;

import ess.mg.MgMarket;
import ess.mg.agents.dto.PurchaseResult;
import ess.mg.goods.Quality;
import ess.mg.goods.food.Dairy;

public class MgWebShopperTest {

    public static void main(final String... args) {
        final MgWebShopper shopper = new MgWebDriver();
        shopper.login();
        final PurchaseResult result = shopper.buyGoods(new Dairy(Quality.HIGH), MgMarket.LOCAL);
        System.out.println(result.isSuccessful() + " " + result.getPrice());
    }

}
