package ess.mg.driver;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import ess.mg.agents.actions.ActionResult;

public abstract class MgWebFighter extends MgWebReader {

    public static final String FIGHTING = "FIGHTING";

    public ActionResult referralFight() {
        final ActionResult result = new ActionResult();
        driver.navigate().to(BASE_URL_ACCOUNT + "/fight");
        pauseForRead();

        try {
            final WebElement buttonFight = driver.findElement(By.className("buttonFight"));
            final String opponentUrl = buttonFight.getAttribute("href");
            buttonFight.click();
            pauseForSubmit();

            System.out.println("Fighting: " + opponentUrl);
            driver.navigate().to(opponentUrl);
            pauseForRead();

            try {
                final WebElement nd_submit_big = driver.findElement(By.className("nd_submit_big"));
                // System.out.println(nd_submit_big.getAttribute("value"));
                nd_submit_big.click();
                pauseForSubmit();

                driver.findElement(By.className("nd_submit_big"));
                result.setSuccessful(true);
            } catch (final NoSuchElementException e) {
                return referralFight();
            }
        } catch (final NoSuchElementException e) {
            result.setMessage(FIGHTING);
        }

        return result;
    }

}
