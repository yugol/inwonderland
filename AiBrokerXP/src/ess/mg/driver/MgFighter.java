package ess.mg.driver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class MgFighter extends MgWebReader {

    public static void main(final String... args) {
        final MgFighter fighter = new MgFighter();
        fighter.login();
        fighter.referralFightTrainer();
        fighter.close();
    }

    private static final long SAFE_SLEEP = 1000 * 3;

    public void referralFightTrainer() {
        driver.navigate().to(BASE_URL_ACCOUNT + "/fight");
        pause();

        final WebElement buttonFight = driver.findElement(By.className("buttonFight"));
        buttonFight.click();
        pause(SAFE_SLEEP);

        driver.navigate().to(BASE_URL_ACCOUNT + "/fight/view_user/498");
        pause();

        final WebElement nd_submit_big = driver.findElement(By.className("nd_submit_big"));
        nd_submit_big.click();

        pause(SAFE_SLEEP);
    }

}
