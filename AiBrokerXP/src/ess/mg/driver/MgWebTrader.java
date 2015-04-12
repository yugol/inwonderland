package ess.mg.driver;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

public abstract class MgWebTrader extends MgWebShopper {

    @Override
    public Boolean buyGoldFromRon(final int goldAmount) {
        final WebElement amount_buy = driver.findElement(By.name("amount_buy"));
        amount_buy.sendKeys(String.valueOf(goldAmount));
        final List<WebElement> nd_submit_inv = driver.findElements(By.className("nd_submit_inv"));
        nd_submit_inv.get(0).click();
        pauseForSubmit();
        try {
            driver.findElement(By.className("nd_mess_error"));
            return false;
        } catch (final NoSuchElementException nsee) {
            return true;
        }
    }

    @Override
    public Boolean sellGoldToRon(final int goldAmount) {
        final WebElement amount_buy = driver.findElement(By.name("amount_sell"));
        amount_buy.sendKeys(String.valueOf(goldAmount));
        final List<WebElement> nd_submit_inv = driver.findElements(By.className("nd_submit_inv"));
        nd_submit_inv.get(1).click();
        pauseForSubmit();
        try {
            driver.findElement(By.className("nd_mess_error"));
            return false;
        } catch (final NoSuchElementException nsee) {
            return true;
        }
    }

}
