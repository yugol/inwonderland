package ess.common.driver;

import java.awt.Rectangle;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import aibroker.Context;
import ess.common.EssContext;

public abstract class EssDriverBase {

    private static void pause(final long sleepTime) {
        try {
            Thread.sleep(sleepTime);
        } catch (final InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected static String normalizeNumberString(String number) {
        try {
            number = number.trim();
            number = number.split(" ")[0];
            number = number.replace(",", ".");
        } catch (final Exception e) {
        }
        return number;
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

    protected final WebDriver driver = new FirefoxDriver();

    public EssDriverBase() {
        final Rectangle bounds = Context.getBrowserWindowBounds();
        if (bounds != null) {
            driver.manage().window().setPosition(new Point(bounds.x, bounds.y));
            driver.manage().window().setSize(new org.openqa.selenium.Dimension(bounds.width, bounds.height));
        }
    }

    public abstract Boolean buyGoldFromRon(final int goldAmount);

    public void close() {
        driver.quit();
    }

    public abstract void fetchEuroGoldExchangeRate(final EssContext context);

    public abstract void fetchGoldRonExchangeRate(final EssContext context);

    public abstract void fetchShares(final EssContext context);

    public abstract EssContext login();

    public abstract Boolean sellGoldToRon(final int goldAmount);

    protected void navigateTo(final String url) {
        System.out.println("Navigating to: " + url);
        driver.navigate().to(url);
        pauseForRead();
    }

}