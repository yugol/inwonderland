package ess.mg.driver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ess.mg.actions.ActionResult;

public abstract class MgWebFighter extends MgWebReader {

    public ActionResult referralFightTrainer() {
        driver.navigate().to(BASE_URL_ACCOUNT + "/fight");
        pauseForRead();

        final WebElement buttonFight = driver.findElement(By.className("buttonFight"));
        buttonFight.click();
        pauseForSubmit();

        driver.navigate().to(BASE_URL_ACCOUNT + "/fight/view_user/498");
        pauseForRead();

        final WebElement nd_submit_big = driver.findElement(By.className("nd_submit_big"));
        System.out.println(nd_submit_big.getAttribute("value"));
        // nd_submit_big.click();
        pauseForSubmit();

        final ActionResult result = new ActionResult();
        result.setSuccessful(true);
        return result;
    }

}
