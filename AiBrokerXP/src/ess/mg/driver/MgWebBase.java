package ess.mg.driver;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import aibroker.Context;

public abstract class MgWebBase {

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

    protected static void pause() {
        pause(SLEEP_TIME);
    }

    protected static void pause(final long sleepTime) {
        try {
            Thread.sleep(sleepTime);
        } catch (final InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static final String                BASE_URL         = "http://www.marketglory.com";
    public static final String                BASE_URL_ACCOUNT = BASE_URL + "/account";
    private static final long                 SLEEP_TIME       = 10;

    protected final WebDriver                 driver           = new FirefoxDriver();
    protected final List<MgWebReaderListener> readListeners        = new ArrayList<MgWebReaderListener>();

    public void addRecordLogger(final MgWebRecordLogger logger) {
        readListeners.add(logger);
    }

    public void close() {
        driver.quit();
    }

    public Integer login() {
        driver.navigate().to(BASE_URL);
        pause();

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

}
