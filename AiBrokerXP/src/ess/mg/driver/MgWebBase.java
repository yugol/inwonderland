package ess.mg.driver;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import aibroker.Context;
import ess.common.driver.EssDriverBase;
import ess.mg.MgContext;
import ess.mg.MgLogger;
import ess.mg.MgMarket;
import ess.mg.goods.Goods;
import ess.mg.goods.Gradable;

public abstract class MgWebBase extends EssDriverBase {

    protected static final String             BASE_URL         = "http://www.marketglory.com";
    protected static final String             BASE_URL_ACCOUNT = BASE_URL + "/account";

    protected final List<MgWebDriverListener> listeners        = new ArrayList<MgWebDriverListener>();

    public void addRecordLogger(final MgLogger logger) {
        listeners.add(logger);
    }

    @Override
    public MgContext login() {
        navigateTo(BASE_URL);

        final WebElement last_12h_login = driver.findElement(By.className("last_24h_login"));
        final String activeUsers = last_12h_login.getText();
        for (final MgWebDriverListener listener : listeners) {
            listener.onFetchActiveUsers(activeUsers);
        }

        final WebElement character_name = driver.findElement(By.name("character_name"));
        character_name.sendKeys(Context.getEssUser());
        final WebElement character_password = driver.findElement(By.name("character_password"));
        character_password.sendKeys(Context.getEssPassword());
        character_name.submit();

        final MgContext context = new MgContext();
        context.setActiveUsersCount(parseInt(activeUsers));
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
