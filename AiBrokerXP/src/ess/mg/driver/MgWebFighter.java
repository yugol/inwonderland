package ess.mg.driver;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import ess.mg.actions.FightResult;

public abstract class MgWebFighter extends MgWebReader {

    public static final String FIGHTING = "FIGHTING";

    public FightResult referralFight() {
        final FightResult result = new FightResult();
        driver.navigate().to(BASE_URL_ACCOUNT + "/fight");
        pauseForRead();

        try {
            final WebElement nd_mess_error = driver.findElement(By.className("nd_mess_error"));
            result.setMessage(nd_mess_error.getText());
            result.setMaximumFightCountReached(true);
        } catch (final NoSuchElementException e) {
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
                    //System.out.println(nd_submit_big.getAttribute("value"));
                    nd_submit_big.click();
                    pauseForSubmit();

                    driver.findElement(By.className("nd_submit_big"));
                    result.setSuccessful(true);
                } catch (final NoSuchElementException ex) {
                    return referralFight();
                }
            } catch (final NoSuchElementException ex) {
                result.setMessage(FIGHTING);
                result.setFighting(true);
            }
        }

        return result;
    }

}
