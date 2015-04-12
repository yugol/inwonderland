package ess.gt.driver;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ess.common.driver.model.Shares;
import ess.gt.GtContext;

public class GtWebReader extends GtWebBase {

    public void fetchPlayerContext(final GtContext context) {
        final WebElement mr_right_coins = driver.findElement(By.id("mr_right_coins"));
        final List<WebElement> fortune = mr_right_coins.findElements(By.cssSelector("span[class=\"right\"]"));
        context.setEuroAmount(parseDouble(fortune.get(0).getText()));
        context.setGoldAmount(parseDouble(fortune.get(1).getText()));
        context.setRonAmount(parseDouble(fortune.get(2).getText()));
    }

    public void fetchShares(final GtContext context) {
        navigateTo(BASE_URL_ACCOUNT + "/partners/sharemarket");

        final WebElement euro = driver.findElement(By.cssSelector("span[class=\"size18 bold shadow\"] span[class=\"green\"]"));
        final String euroAmount = euro.getText();
        final WebElement gold = driver.findElement(By.cssSelector("span[class=\"size18 bold shadow\"] span[class=\"gold\"]"));
        final String goldAmount = gold.getText();
        final WebElement share = driver.findElement(By.cssSelector("span[class=\"green shadow\"]"));
        final String sharePrice = share.getText();

        final Shares shares = new Shares(
                parseDouble(normalizeNumberString(euroAmount)),
                parseDouble(normalizeNumberString(goldAmount)),
                parseDouble(normalizeNumberString(sharePrice)));
        context.setShares(shares);
    }

}
