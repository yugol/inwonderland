package ess.mg.driver;

import ess.Price;
import ess.mg.agents.basic.PurchaseResult;

public class MgWebShopperTest {

    public static void main(final String... args) {
        final MgWebShopper shopper = new MgWebDriver();
        shopper.login();
        final PurchaseResult result = shopper.buyNewspaper(1, Price.ron(0.01));
        System.out.println(result.isSuccessful() + " " + result.getPrice());
    }

}
