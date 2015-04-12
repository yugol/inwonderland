package ess.common.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import ess.common.EssContext;

public abstract class EssDriverBase {

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

    protected final WebDriver driver = new FirefoxDriver();

    public void close() {
        driver.quit();
    }

    public abstract EssContext login();

}
