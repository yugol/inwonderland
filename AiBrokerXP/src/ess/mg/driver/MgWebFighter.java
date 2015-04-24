package ess.mg.driver;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import ess.mg.agents.dto.ArenaFightResult;
import ess.mg.agents.dto.ReferralFightResult;

public abstract class MgWebFighter extends MgWebReader {

    public static final String FIGHTING = "FIGHTING";

    public ReferralFightResult referralFight() {
        navigateTo(BASE_URL_ACCOUNT + "/fight");

        final ReferralFightResult result = checkFightCount(new ReferralFightResult());
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

    public ReferralFightResult referralFightTrainer() {
        navigateTo(BASE_URL_ACCOUNT + "/fight");

        final ReferralFightResult result = checkFightCount(new ReferralFightResult());
        if (!result.isMaxFightCountReached()) {
            try {
                final List<WebElement> navigation = driver.findElement(By.className("nd_pagination")).findElements(By.tagName("a"));
                final WebElement last = navigation.get(navigation.size() - 1);
                last.click();
                pauseForRead();
            } catch (final NoSuchElementException ex) {
            }
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

    public ArenaFightResult searchArenaOpponent(final int startFrom) {
        navigateTo(BASE_URL_ACCOUNT + "/arena/fighters/" + startFrom);

        final ArenaFightResult fightResult = new ArenaFightResult(startFrom);
        final List<WebElement> fighters = driver.findElements(By.cssSelector("#list_fighters table tbody tr"));
        for (final WebElement fighter : fighters) {
            final String energy = fighter.findElement(By.cssSelector("td:nth-of-type(5)")).getText().split("\\.")[0];
            if ("1".equals(energy)) {
                fightResult.setOpponentFound(true);
                final WebElement preview = fighter.findElement(By.cssSelector("td:nth-of-type(6) a"));
                final String opponentUrl = preview.getAttribute("href");
                preview.click();
                pauseForSubmit();

                navigateTo(opponentUrl);
                // navigateTo(opponentUrl);
                // navigateTo(opponentUrl);

                final List<WebElement> errors = driver.findElements(By.className("nd_mess_error"));
                if (errors.size() == 0) {
                    final List<WebElement> stats = driver.findElements(By.cssSelector("table[class='nd_list mt13'] tbody tr td b"));
                    final Double totalAttack = Double.parseDouble(stats.get(8).getText());
                    final Double totalDefense = Double.parseDouble(stats.get(17).getText());
                    if (totalAttack > totalDefense) {
                        final WebElement attack = driver.findElement(By.cssSelector("input[class='mt13']"));
                        // System.out.println(attack.getAttribute("value"));
                        attack.click();
                        pauseForSubmit();
                        fightResult.setSuccessful(true);
                        break;
                    }
                } else {
                    break;
                }
            } else {
                fightResult.incrementOpponentIndex();
            }
        }
        return fightResult;
    }

    private void attackReferral(final ReferralFightResult result) {
        final WebElement nd_submit_big = driver.findElement(By.className("nd_submit_big"));
        // System.out.println(nd_submit_big.getAttribute("value"));
        nd_submit_big.click();
        pauseForSubmit();

        driver.findElement(By.className("nd_submit_big"));
        result.setSuccessful(true);
    }

    private ReferralFightResult checkFightCount(final ReferralFightResult result) {
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
