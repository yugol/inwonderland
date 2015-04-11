package ess.mg.driver;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class MgWebManager extends MgWebTrader {

    public boolean activateJob(final String companyUrl, final int index, final double hourlyWage) {
        navigateTo(BASE_URL_ACCOUNT + "/managecompany/" + companyUrl + "/jobs");

        boolean activated = false;
        final List<WebElement> inputText = driver.findElements(By.cssSelector("input[type=\"text\"]"));
        final WebElement salary = inputText.get(3 + index);
        salary.sendKeys("\b\b\b\b\b\b\b\b\b\b");
        salary.sendKeys(String.valueOf(hourlyWage));

        List<WebElement> inputSubmit = driver.findElements(By.cssSelector("input[type=\"submit\"]"));
        final WebElement btnSave = inputSubmit.get(2);
        // System.out.println(btnSave.getAttribute("value"));
        btnSave.click();
        pauseForSubmit();

        inputSubmit = driver.findElements(By.cssSelector("input[type=\"submit\"]"));
        final WebElement btnActivate = inputSubmit.get(3);
        final String value = btnActivate.getAttribute("value");
        if (value.contains("A")) {
            // System.out.println(value);
            btnActivate.click();
            activated = true;
            pauseForSubmit();
        }

        return activated;
    }

}
