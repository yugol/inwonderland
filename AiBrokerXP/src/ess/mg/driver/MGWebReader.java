package ess.mg.driver;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.Timer;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import aibroker.Context;

public class MGWebReader implements ActionListener {

    public static void main(final String[] args) {
        final MGWebReader reader = new MGWebReader();
        final MGWebRecordLogger record = new MGWebRecordLogger();
        reader.fetch(record);
    }

    private static final long SLEEP_TIME = 10;
    private static final int  EXIT_TIME  = 1000 * 60 * 5;

    private final WebDriver   driver     = new FirefoxDriver();

    @Override
    public void actionPerformed(final ActionEvent e) {
        driver.quit();
        System.exit(1);
    }

    public void fetch(final MGWebReaderListener listener) {
        final Timer timer = new Timer(EXIT_TIME, this);
        timer.setRepeats(false);
        timer.start();

        try {
            driver.navigate().to("http://www.marketglory.com/");
            Thread.sleep(SLEEP_TIME);
            final WebElement last_12h_login = driver.findElement(By.className("last_24h_login"));
            final String activeUsers = last_12h_login.getText();
            listener.onFetchActiveUsers(activeUsers);
            final WebElement character_name = driver.findElement(By.name("character_name"));
            character_name.sendKeys(Context.getEssUser());
            final WebElement character_password = driver.findElement(By.name("character_password"));
            character_password.sendKeys(Context.getEssPassword());
            character_name.submit();

            driver.navigate().to("http://www.marketglory.com/account/work");
            Thread.sleep(SLEEP_TIME);
            final WebElement nd_work_wage = driver.findElement(By.className("nd_work_wage"));
            final WebElement workWage = nd_work_wage.findElement(By.tagName("span"));
            listener.onFetchWorkWage(workWage.getText());

            driver.navigate().to("http://www.marketglory.com/account/partners/sharesmarket");
            Thread.sleep(SLEEP_TIME);
            final List<WebElement> nd_part_amount_big = driver.findElements(By.className("nd_part_amount_big"));
            final List<WebElement> nd_part_amount_small = driver.findElements(By.className("nd_part_amount_small"));
            final String eurAmount = nd_part_amount_big.get(0).getText() + nd_part_amount_small.get(0).getText();
            listener.onFetchEurAmount(eurAmount);
            final String goldAmount = nd_part_amount_big.get(1).getText() + nd_part_amount_small.get(2).getText();
            listener.onFetchGoldAmount(goldAmount);
            final List<WebElement> nd_part_td = driver.findElement(By.className("nd_part_table")).findElements(By.className("nd_part_td"));
            // System.err.println(nd_part_td.get(2).getText());
            final WebElement sharePrice = nd_part_td.get(2).findElement(By.tagName("span"));
            listener.onFetchSharePrice(sharePrice.getText());

            driver.navigate().to("http://www.marketglory.com/account/financial_market/");
            Thread.sleep(SLEEP_TIME);
            WebElement mt13 = driver.findElement(By.className("mt13"));
            List<WebElement> td = mt13.findElements(By.tagName("td"));
            listener.onFetchEuroGoldExchangeRate(td.get(1).getText());

            driver.navigate().to("http://www.marketglory.com/account/financial_market/moneda_gold");
            Thread.sleep(SLEEP_TIME);
            mt13 = driver.findElement(By.className("mt13"));
            td = mt13.findElements(By.tagName("td"));
            listener.onFetchGoldRonExchangeRate(td.get(1).getText());

            driver.quit();
        } catch (final InterruptedException e) {
            e.printStackTrace();
        }
    }

}
