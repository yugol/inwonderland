package ess.mg.driver;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import aibroker.util.Moment;
import ess.common.Price;
import ess.mg.MgContext;
import ess.mg.MgMarket;
import ess.mg.driver.model.Newspaper;
import ess.mg.driver.model.Shares;
import ess.mg.driver.model.Transaction;
import ess.mg.driver.model.Transactions;
import ess.mg.goods.Goods;
import ess.mg.goods.Quality;
import ess.mg.goods.food.Wine;

public abstract class MgWebReader extends MgWebBase {

    public Double fetchEuroGoldExchangeRate() {
        navigateTo(BASE_URL_ACCOUNT + "/" + MgMarket.FINANCIAL.urlChunk);

        final WebElement mt13 = driver.findElement(By.className("mt13"));
        final List<WebElement> td = mt13.findElements(By.tagName("td"));
        final String euroGoldExchangeRate = td.get(1).getText();
        for (final MgWebDriverListener listener : listeners) {
            listener.onFetchEuroGoldExchangeRate(euroGoldExchangeRate);
        }
        return parseDouble(euroGoldExchangeRate);
    }

    public void fetchFightCount(final MgContext global) {
        navigateTo(BASE_URL_ACCOUNT + "/fight");

        try {
            final WebElement nd_mess_info = driver.findElement(By.className("nd_mess_info"));
            final String message = nd_mess_info.getText();
            final String[] chunks = message.trim().split(" ");
            global.setFightCount(Integer.parseInt(chunks[2]));
        } catch (final NoSuchElementException e) {
            global.setFightCount(MgContext.MAX_FIGHTS_PER_DAY);
        }
    }

    public void fetchGlobalContext(final MgContext global) {
        final WebElement _mr_bonus_fight = driver.findElement(By.className("_mr_bonus_fight"));
        WebElement p = _mr_bonus_fight.findElement(By.tagName("p"));
        final String fightBonus = p.getText();
        global.setFightBonus(Price.ron(parseDouble(fightBonus.split(" ")[0])));

        final WebElement _mr_bonus_work = driver.findElement(By.className("_mr_bonus_work"));
        p = _mr_bonus_work.findElement(By.tagName("p"));
        final String workBonus = p.getText();
        global.setWorkBonus(Price.ron(parseDouble(workBonus.split(" ")[0])));

        final WebElement ms_stats = driver.findElement(By.className("ms_stats"));
        final List<WebElement> highlighted1 = ms_stats.findElements(By.className("highlighted1"));
        global.setEnergy(parseDouble(highlighted1.get(0).findElement(By.tagName("b")).getText()));
        global.setExperience(parseDouble(highlighted1.get(1).findElement(By.tagName("b")).getText()));
        global.setKnowledge(parseDouble(highlighted1.get(2).findElement(By.tagName("b")).getText()));

        final WebElement site_timer = driver.findElement(By.id("site_timer"));
        global.setServerTime(Moment.fromIso(site_timer.getText()));

        final WebElement ms_eur = driver.findElement(By.className("ms_eur"));
        global.setEuroAmount(parseDouble(ms_eur.findElements(By.tagName("b")).get(1).getText()));

        final WebElement ms_gold = driver.findElement(By.className("ms_gold"));
        global.setGoldAmount(parseDouble(ms_gold.findElements(By.tagName("b")).get(1).getText()));

        final WebElement ms_x = driver.findElement(By.className("ms_x"));
        global.setRonAmount(parseDouble(ms_x.findElements(By.tagName("b")).get(1).getText()));

        try {
            final WebElement timerMin_fight = driver.findElement(By.id("timerMin_fight"));
            global.setFightRemainingMinutes(parseInt(timerMin_fight.getText()));
        } catch (final NoSuchElementException ex) {
            global.setFightRemainingMinutes(0);
        }
    }

    public Double fetchGoldRonExchangeRate() {
        navigateTo(BASE_URL_ACCOUNT + "/" + MgMarket.FINANCIAL.urlChunk + "/moneda_gold");

        final WebElement mt13 = driver.findElement(By.className("mt13"));
        final List<WebElement> td = mt13.findElements(By.tagName("td"));
        final String goldRonExchangeRate = td.get(1).getText();
        for (final MgWebDriverListener listener : listeners) {
            listener.onFetchGoldRonExchangeRate(goldRonExchangeRate);
        }
        return parseDouble(goldRonExchangeRate);
    }

    public void fetchInventory(final MgContext global) {
        navigateTo(BASE_URL_ACCOUNT + "/inventory");

        final List<WebElement> nd_in_qty = driver.findElements(By.className("nd_in_qty"));
        global.setCuisine(Quality.LOW, parseInt(nd_in_qty.get(0).getText()));
        global.setCuisine(Quality.NORMAL, parseInt(nd_in_qty.get(1).getText()));
        global.setCuisine(Quality.HIGH, parseInt(nd_in_qty.get(2).getText()));
        global.setCoffe(Quality.LOW, parseInt(nd_in_qty.get(3).getText()));
        global.setCoffe(Quality.NORMAL, parseInt(nd_in_qty.get(4).getText()));
        global.setCoffe(Quality.HIGH, parseInt(nd_in_qty.get(5).getText()));
        global.setCheese(parseInt(nd_in_qty.get(6).getText()));
    }

    public Newspaper fetchNewspaper(final int index) {
        navigateTo(BASE_URL_ACCOUNT + "/special/local/latest/" + index);

        final Newspaper newspaper = new Newspaper(index);
        final WebElement _mr_article_big = driver.findElement(By.className("_mr_article_big"));
        newspaper.setTitle(_mr_article_big.findElement(By.tagName("h2")).getText());

        final WebElement _mr_newspaper_infos = driver.findElement(By.className("_mr_newspaper_infos"));

        final WebElement _mr_split_right = _mr_newspaper_infos.findElement(By.className("_mr_split_right"));
        final String sold = _mr_split_right.getText().split(" ")[1];
        newspaper.setSold(parseInt(sold));

        final WebElement gray = _mr_newspaper_infos.findElement(By.className("gray"));
        final String votes = gray.getText().split(" ")[2];
        newspaper.setVotes(parseInt(votes));

        final WebElement _mr_buy_btn_big = _mr_newspaper_infos.findElement(By.className("_mr_buy_btn_big"));
        final String price = _mr_buy_btn_big.getText().split(" ")[0];
        newspaper.setPrice(parseDouble(price));

        return newspaper;
    }

    public Price fetchPrice(final Goods goods, final MgMarket market) {
        navigateTo(buildGoodsUrl(goods, market));

        return readGoodsPrice(goods, market);
    }

    public Shares fetchShares() {
        navigateTo(BASE_URL_ACCOUNT + "/partners/sharesmarket");

        final List<WebElement> nd_part_amount_big = driver.findElements(By.className("nd_part_amount_big"));
        final List<WebElement> nd_part_amount_small = driver.findElements(By.className("nd_part_amount_small"));
        final String eurAmount = nd_part_amount_big.get(0).getText() + nd_part_amount_small.get(0).getText();
        for (final MgWebDriverListener listener : listeners) {
            listener.onFetchEurAmount(eurAmount);
        }
        final String goldAmount = nd_part_amount_big.get(1).getText() + nd_part_amount_small.get(2).getText();
        for (final MgWebDriverListener listener : listeners) {
            listener.onFetchGoldAmount(goldAmount);
        }
        final List<WebElement> nd_part_td = driver.findElement(By.className("nd_part_table")).findElements(By.className("nd_part_td"));
        final String sharePrice = nd_part_td.get(2).findElement(By.tagName("span")).getText();
        for (final MgWebDriverListener listener : listeners) {
            listener.onFetchSharePrice(sharePrice);
        }

        final Shares shares = new Shares(parseDouble(eurAmount), parseDouble(goldAmount), parseDouble(sharePrice));
        return shares;
    }

    public Transactions fetchTransactions(final int from) {
        final Transactions ts = new Transactions();
        navigateTo(BASE_URL_ACCOUNT + "/accountancy/transactions/" + from);

        final WebElement nd_ref_mkt_table = driver.findElement(By.className("nd_ref_mkt_table")).findElement(By.tagName("tbody"));
        final List<WebElement> tr = nd_ref_mkt_table.findElements(By.tagName("tr"));
        for (final WebElement tElement : tr) {
            final List<WebElement> td = tElement.findElements(By.tagName("td"));
            boolean income = true;
            try {
                td.get(0).findElement(By.className("ndti_red_arr"));
                income = false;
            } catch (final NoSuchElementException ex) {
            }
            final String time = td.get(1).getAttribute("title");
            final String description = td.get(2).getText();
            final Transaction t = Transaction.newInstance(income, time, description);
            if (t != null) {
                ts.add(t);
            }
        }
        return ts;
    }

    public Double fetchWage() {
        navigateTo(BASE_URL_ACCOUNT + "/work");

        final WebElement nd_work_wage = driver.findElement(By.className("nd_work_wage"));
        final WebElement workWageElement = nd_work_wage.findElement(By.tagName("span"));
        final String workWage = workWageElement.getText();
        for (final MgWebDriverListener listener : listeners) {
            listener.onFetchWorkWage(workWage);
        }
        return parseDouble(workWage);
    }

    public void fetchWorkCount(final MgContext global) {
        navigateTo(BASE_URL_ACCOUNT + "/work");

        try {
            driver.findElement(By.className("nd_mess_error"));
            global.setWorkCount(1);
        } catch (final NoSuchElementException e) {
            global.setWorkCount(0);
        }
    }

    protected Price readGoodsPrice(final Goods goods, final MgMarket market) {
        String price = null;
        final WebElement nd_mkt_table_price = driver.findElement(By.className("nd_mkt_table_price"));
        if (goods instanceof Wine) {
            final WebElement span = nd_mkt_table_price.findElement(By.tagName("span"));
            price = span.getText();
        } else {
            final WebElement nd_mkt_table_price_nr = nd_mkt_table_price.findElement(By.className("nd_mkt_table_price_nr"));
            price = nd_mkt_table_price_nr.getText();
        }
        final Double amount = parseDouble(price);
        return market == MgMarket.LOCAL ? Price.ron(amount) : Price.gold(amount);
    }
}
