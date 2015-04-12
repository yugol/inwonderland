package ess.mg.driver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import aibroker.Context;
import ess.common.driver.EssDriverBase;
import ess.mg.MgContext;
import ess.mg.MgMarket;
import ess.mg.goods.Goods;
import ess.mg.goods.Gradable;

public abstract class MgWebBase extends EssDriverBase {

    protected static final String BASE_URL         = "http://www.marketglory.com";
    protected static final String BASE_URL_ACCOUNT = BASE_URL + "/account";

    @Override
    public MgContext login() {
        final MgContext context = new MgContext();
        navigateTo(BASE_URL);

        final WebElement last_12h_login = driver.findElement(By.cssSelector("div[class='last_24h_login']"));
        final String activeUsers = last_12h_login.getText();
        context.setActiveUsersCount(parseInt(normalizeNumberString(activeUsers)));

        final WebElement character_name = driver.findElement(By.name("character_name"));
        character_name.sendKeys(Context.getEssUser());
        final WebElement character_password = driver.findElement(By.name("character_password"));
        character_password.sendKeys(Context.getEssPassword());
        character_name.submit();
        pauseForSubmit();

        return context;
    }

    protected String buildGoodsUrl(final Goods goods, final MgMarket market) {
        final StringBuilder url = new StringBuilder(BASE_URL_ACCOUNT);
        url.append("/").append(market.urlChunk);
        url.append("/").append(goods.getCategory());
        url.append("/").append(goods.getName());
        if (goods instanceof Gradable) {
            url.append("/q").append(((Gradable) goods).getQuality().stars);
        }
        return url.toString();
    }

}
