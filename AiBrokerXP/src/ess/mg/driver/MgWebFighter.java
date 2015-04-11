package ess.mg.driver;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import ess.mg.actions.FightResult;

public abstract class MgWebFighter extends MgWebReader {

    public static final String FIGHTING = "FIGHTING";

    public FightResult referralFight() {
        navigateTo(BASE_URL_ACCOUNT + "/fight");

        final FightResult result = checkFightCount(new FightResult());
        if (!result.isMaxFightCountReached()) {
            try {
                final WebElement buttonFight = driver.findElement(By.className("buttonFight"));
                final String opponentUrl = buttonFight.getAttribute("href");
                buttonFight.click();
                pauseForSubmit();

                navigateTo(opponentUrl);

                try {
                    attackReferral(result);
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

    public FightResult referralFightTrainer() {
        navigateTo(BASE_URL_ACCOUNT + "/fight");

        final FightResult result = checkFightCount(new FightResult());
        if (!result.isMaxFightCountReached()) {
            final List<WebElement> navigation = driver.findElement(By.className("nd_pagination")).findElements(By.tagName("a"));
            final WebElement last = navigation.get(navigation.size() - 1);
            last.click();
            pauseForRead();

            try {
                final List<WebElement> buttonsFight = driver.findElements(By.className("buttonFight"));
                final WebElement buttonFight = buttonsFight.get(buttonsFight.size() - 1);
                final String opponentUrl = buttonFight.getAttribute("href");
                buttonFight.click();
                pauseForSubmit();

                navigateTo(opponentUrl);

                try {
                    attackReferral(result);
                } catch (final NoSuchElementException ex) {
                    return referralFightTrainer();
                }
            } catch (final NoSuchElementException ex) {
                result.setMessage(FIGHTING);
                result.setFighting(true);
            }
        }
        return result;
    }

    private void attackReferral(final FightResult result) {
        final WebElement nd_submit_big = driver.findElement(By.className("nd_submit_big"));
        //System.out.println(nd_submit_big.getAttribute("value"));
        nd_submit_big.click();
        pauseForSubmit();

        driver.findElement(By.className("nd_submit_big"));
        result.setSuccessful(true);
    }

    private FightResult checkFightCount(final FightResult result) {
        try {
            final WebElement nd_mess_error = driver.findElement(By.className("nd_mess_error"));
            result.setMessage(nd_mess_error.getText());
            result.setMaximumFightCountReached(true);
        } catch (final NoSuchElementException e) {
            result.setMaximumFightCountReached(false);
        }
        return result;
    }

}
