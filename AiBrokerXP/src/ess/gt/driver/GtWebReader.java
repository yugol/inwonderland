package ess.gt.driver;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ess.common.EssContext;
import ess.common.driver.model.Shares;

public abstract class GtWebReader extends GtWebBase {

    public void fetchEuroGoldExchangeRate(final EssContext context) {
        navigateTo(BASE_URL_ACCOUNT + "/financial/euro2gold");

        final WebElement _mr_buy_center = driver.findElement(By.cssSelector("span[class='_mr_buy_center']"));
        final String rate = _mr_buy_center.getText();
        context.setEuroGoldExchangeRate(parseDouble(normalizeNumberString(rate)));
    }

    public void fetchGoldRonExchangeRate(final EssContext context) {
        navigateTo(BASE_URL_ACCOUNT + "/financial/gold2local");

        final WebElement _mr_buy_center = driver.findElement(By.cssSelector("span[class='_mr_buy_center']"));
        final String rate = _mr_buy_center.getText();
        context.setGoldRonExchangeRate(parseDouble(normalizeNumberString(rate)));
    }

    public void fetchPlayerContext(final EssContext context) {
        navigateTo(BASE_URL_ACCOUNT);

        final WebElement mr_right_coins = driver.findElement(By.id("mr_right_coins"));
        final List<WebElement> fortune = mr_right_coins.findElements(By.cssSelector("span[class='right']"));
        context.setEuroAmount(parseDouble(fortune.get(0).getText()));
        context.setGoldAmount(parseDouble(fortune.get(1).getText()));
        context.setRonAmount(parseDouble(fortune.get(2).getText()));
    }

    public void fetchShares(final EssContext context) {
        navigateTo(BASE_URL_ACCOUNT + "/partners/sharemarket");

        final WebElement euro = driver.findElement(By.cssSelector("span[class='size18 bold shadow'] span[class='green']"));
        final String euroAmount = euro.getText();
        final WebElement gold = driver.findElement(By.cssSelector("span[class='size18 bold shadow'] span[class='gold']"));
        final String goldAmount = gold.getText();
        final WebElement share = driver.findElement(By.cssSelector("span[class='green shadow']"));
        final String sharePrice = share.getText();

        final Shares shares = new Shares(
                parseDouble(normalizeNumberString(euroAmount)),
                parseDouble(normalizeNumberString(goldAmount)),
                parseDouble(normalizeNumberString(sharePrice)));
        context.setShares(shares);
    }

}
