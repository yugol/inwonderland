package ess.mg.driver;

import ess.common.Price;
import ess.mg.MgMarket;
import ess.mg.goods.Quality;
import ess.mg.goods.weapons.Knife;

public class MgWebReaderTest {

    public static void main(final String... args) {
        final MgWebReader reader = new MgWebDriver();
        reader.login();
        final Price price = reader.fetchPrice(new Knife(Quality.LOW), MgMarket.LOCAL);
        System.out.println(price);
    }

}
