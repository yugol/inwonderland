package ess.gt.driver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public abstract class GtWebTrader extends GtWebReader {

    @Override
    public Boolean buyGoldFromRon(final int goldAmount) {
        final WebElement buy_gold = driver.findElement(By.name("buy_gold"));
        buy_gold.sendKeys(String.valueOf(goldAmount));
        buy_gold.submit();
        pauseForSubmit();
        return null;
    }

    @Override
    public Boolean sellGoldToRon(final int goldAmount) {
        final WebElement sell_gold = driver.findElement(By.name("sell_gold"));
        sell_gold.sendKeys(String.valueOf(goldAmount));
        sell_gold.submit();
        pauseForSubmit();
        return null;
    }

}
