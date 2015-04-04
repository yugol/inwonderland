package ess.mg.driver;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import aibroker.Context;
import ess.mg.goods.Goods;
import ess.mg.goods.Gradable;
import ess.mg.markets.MgMarket;

public abstract class MgWebBase {

    private static void pause(final long sleepTime) {
        try {
            Thread.sleep(sleepTime);
        } catch (final InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected static Double parseDouble(final String doubleValue) {
        try {
            return Double.parseDouble(doubleValue);
        } catch (final Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    protected static Integer parseInt(final String intValue) {
        try {
            return Integer.parseInt(intValue);
        } catch (final Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    protected static void pauseForRead() {
        pause(10);
    }

    protected static void pauseForSubmit() {
        pause(2500);
    }

    public static final String                BASE_URL         = "http://www.marketglory.com";
    public static final String                BASE_URL_ACCOUNT = BASE_URL + "/account";

    protected final WebDriver                 driver           = new FirefoxDriver();
    protected final List<MgWebReaderListener> readListeners    = new ArrayList<MgWebReaderListener>();

    public void addRecordLogger(final MgWebRecordLogger logger) {
        readListeners.add(logger);
    }

    public void close() {
        driver.quit();
    }

    public Integer login() {
        driver.navigate().to(BASE_URL);
        pauseForRead();

        final WebElement last_12h_login = driver.findElement(By.className("last_24h_login"));
        final String activeUsers = last_12h_login.getText();
        for (final MgWebReaderListener listener : readListeners) {
            listener.onFetchActiveUsers(activeUsers);
        }

        final WebElement character_name = driver.findElement(By.name("character_name"));
        character_name.sendKeys(Context.getEssUser());
        final WebElement character_password = driver.findElement(By.name("character_password"));
        character_password.sendKeys(Context.getEssPassword());
        character_name.submit();

        return parseInt(activeUsers);
    }

    protected String buildGoodsUrl(final Goods goods, final MgMarket market) {
        final StringBuilder url = new StringBuilder(BASE_URL_ACCOUNT);
        url.append("/").append(market.urlChunk);
        url.append("/").append(goods.getCategory());
        url.append("/").append(goods.getName());
        if (goods instanceof Gradable) {
            url.append("/q").append(((Gradable) goods).getQuality().stars);
        }
        return url.toString();
    }

}
