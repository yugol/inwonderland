package ess.mg.driver;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import aibroker.Context;
import ess.mg.goods.Goods;
import ess.mg.goods.food.Dairy;
import ess.mg.markets.MgMarket;

public class MgWebReader {

    public static class Shares {

        private final Double eurAmount;
        private final Double goldAmount;
        private final Double sharePrice;

        public Shares(final Double eurAmount, final Double goldAmount, final Double sharePrice) {
            this.eurAmount = eurAmount;
            this.goldAmount = goldAmount;
            this.sharePrice = sharePrice;
        }

        public Double getEurAmount() {
            return eurAmount;
        }

        public Double getGoldAmount() {
            return goldAmount;
        }

        public Double getSharePrice() {
            return sharePrice;
        }

    }

    public static void main(final String[] args) {
        final MgWebReader reader = new MgWebReader();
        reader.login();
        System.out.println(reader.fetchPrice(new Dairy(3), MgMarket.GLOBAL));
        // reader.close();
    }

    public static void pause() {
        try {
            Thread.sleep(SLEEP_TIME);
        } catch (final InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static Double parseDouble(final String doubleValue) {
        try {
            return Double.parseDouble(doubleValue);
        } catch (final Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private static Integer parseInt(final String intValue) {
        try {
            return Integer.parseInt(intValue);
        } catch (final Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static final String          BASE_URL         = "http://www.marketglory.com";
    public static final String          BASE_URL_ACCOUNT = BASE_URL + "/account";
    private static final long           SLEEP_TIME       = 10;

    private final WebDriver             driver           = new FirefoxDriver();
    private final MgWebReaderListener[] listeners;

    public MgWebReader(final MgWebReaderListener... listeners) {
        this.listeners = listeners;
    }

    public void close() {
        driver.quit();
    }

    public Double fetchEuroGoldExchangeRate() {
        driver.navigate().to(BASE_URL_ACCOUNT + "/" + MgMarket.FINANCIAL.urlChunk);
        pause();

        final WebElement mt13 = driver.findElement(By.className("mt13"));
        final List<WebElement> td = mt13.findElements(By.tagName("td"));
        final String euroGoldExchangeRate = td.get(1).getText();
        for (final MgWebReaderListener listener : listeners) {
            listener.onFetchEuroGoldExchangeRate(euroGoldExchangeRate);
        }
        return parseDouble(euroGoldExchangeRate);
    }

    public Double fetchGoldRonExchangeRate() {
        driver.navigate().to(BASE_URL_ACCOUNT + "/" + MgMarket.FINANCIAL.urlChunk + "/moneda_gold");
        pause();

        final WebElement mt13 = driver.findElement(By.className("mt13"));
        final List<WebElement> td = mt13.findElements(By.tagName("td"));
        final String goldRonExchangeRate = td.get(1).getText();
        for (final MgWebReaderListener listener : listeners) {
            listener.onFetchGoldRonExchangeRate(goldRonExchangeRate);
        }
        return parseDouble(goldRonExchangeRate);
    }

    public Double fetchPrice(final Goods goods, final MgMarket market) {
        final StringBuilder url = new StringBuilder(BASE_URL);
        url.append("/account/").append(market.urlChunk);
        url.append("/").append(goods.getCategory());
        url.append("/").append(goods.getName());
        url.append("/q").append(goods.getStars());
        driver.navigate().to(url.toString());
        pause();

        final WebElement nd_mkt_table_price_nr = driver.findElement(By.className("nd_mkt_table_price_nr"));
        final String price = nd_mkt_table_price_nr.getText();
        return parseDouble(price);
    }

    public Shares fetchShares() {
        driver.navigate().to(BASE_URL_ACCOUNT + "/partners/sharesmarket");
        pause();

        final List<WebElement> nd_part_amount_big = driver.findElements(By.className("nd_part_amount_big"));
        final List<WebElement> nd_part_amount_small = driver.findElements(By.className("nd_part_amount_small"));
        final String eurAmount = nd_part_amount_big.get(0).getText() + nd_part_amount_small.get(0).getText();
        for (final MgWebReaderListener listener : listeners) {
            listener.onFetchEurAmount(eurAmount);
        }
        final String goldAmount = nd_part_amount_big.get(1).getText() + nd_part_amount_small.get(2).getText();
        for (final MgWebReaderListener listener : listeners) {
            listener.onFetchGoldAmount(goldAmount);
        }
        final List<WebElement> nd_part_td = driver.findElement(By.className("nd_part_table")).findElements(By.className("nd_part_td"));
        final String sharePrice = nd_part_td.get(2).findElement(By.tagName("span")).getText();
        for (final MgWebReaderListener listener : listeners) {
            listener.onFetchSharePrice(sharePrice);
        }

        final Shares shares = new Shares(parseDouble(eurAmount), parseDouble(goldAmount), parseDouble(sharePrice));
        return shares;
    }

    public Double fetchWage() {
        driver.navigate().to(BASE_URL_ACCOUNT + "/work");
        pause();

        final WebElement nd_work_wage = driver.findElement(By.className("nd_work_wage"));
        final WebElement workWageElement = nd_work_wage.findElement(By.tagName("span"));
        final String workWage = workWageElement.getText();
        for (final MgWebReaderListener listener : listeners) {
            listener.onFetchWorkWage(workWage);
        }
        return parseDouble(workWage);
    }

    public Integer login() {
        driver.navigate().to(BASE_URL);
        pause();

        final WebElement last_12h_login = driver.findElement(By.className("last_24h_login"));
        final String activeUsers = last_12h_login.getText();
        for (final MgWebReaderListener listener : listeners) {
            listener.onFetchActiveUsers(activeUsers);
        }

        final WebElement character_name = driver.findElement(By.name("character_name"));
        character_name.sendKeys(Context.getEssUser());
        final WebElement character_password = driver.findElement(By.name("character_password"));
        character_password.sendKeys(Context.getEssPassword());
        character_name.submit();

        return parseInt(activeUsers);
    }

}
