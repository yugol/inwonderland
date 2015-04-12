package ess.gt.driver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import aibroker.Context;
import ess.common.driver.EssDriverBase;
import ess.gt.GtContext;

public class GtWebBase extends EssDriverBase {

    protected static final String BASE_URL         = "http://www.goaltycoon.com/";

    protected static final String BASE_URL_ACCOUNT = BASE_URL + "/account";

    @Override
    public GtContext login() {
        final GtContext context = new GtContext();
        navigateTo(BASE_URL);

        final WebElement _ma_active = driver.findElement(By.className("_ma_active"));
        final String activeUsers = _ma_active.getText();
        context.setActiveUsersCount(parseInt(normalizeNumberString(activeUsers)));

        final WebElement character_name = driver.findElement(By.name("character_name"));
        character_name.sendKeys(Context.getEssUser());
        final WebElement character_password = driver.findElement(By.name("character_password"));
        character_password.sendKeys(Context.getEssPassword());
        character_name.submit();
        pauseForSubmit();

        return context;
    }

}
