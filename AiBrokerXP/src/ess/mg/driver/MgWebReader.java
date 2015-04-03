package ess.mg.driver;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ess.mg.goods.Goods;
import ess.mg.markets.MgMarket;

public class MgWebReader extends MgWebDriver {

    public static class Newspaper {

        private final int index;
        private String    title;
        private Integer   sold;
        private Integer   votes;
        private Double    price;

        public Newspaper(final int index) {
            this.index = index;
        }

        @Override
        public boolean equals(final Object obj) {
            if (this == obj) { return true; }
            if (obj == null) { return false; }
            if (getClass() != obj.getClass()) { return false; }
            final Newspaper other = (Newspaper) obj;
            if (title == null) {
                if (other.title != null) { return false; }
            } else if (!title.equals(other.title)) { return false; }
            return true;
        }

        public int getIndex() {
            return index;
        }

        public Double getPrice() {
            return price;
        }

        public Integer getSold() {
            return sold;
        }

        public String getTitle() {
            return title;
        }

        public Integer getVotes() {
            return votes;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + (title == null ? 0 : title.hashCode());
            return result;
        }

        public void setPrice(final Double price) {
            this.price = price;
        }

        public void setSold(final Integer sold) {
            this.sold = sold;
        }

        public void setTitle(final String title) {
            this.title = title;
        }

        public void setVotes(final Integer votes) {
            this.votes = votes;
        }

        @Override
        public String toString() {
            return String.format("%2d %3.2f %3d %3d %s", index, price, sold, votes, title);
        }

    }

    public static class Shares {

        private final Double eurAmount;
        private final Double goldAmount;
        private final Double sharePrice;

        public Shares(final Double eurAmount, final Double goldAmount, final Double sharePrice) {
            this.eurAmount = eurAmount;
            this.goldAmount = goldAmount;
            this.sharePrice = sharePrice;
        }

        public Double getEurAmount() {
            return eurAmount;
        }

        public Double getGoldAmount() {
            return goldAmount;
        }

        public Double getSharePrice() {
            return sharePrice;
        }

    }

    public static void main(final String[] args) {
        final MgWebReader reader = new MgWebReader();
        reader.login();
        final Newspaper paper = reader.fetchNewspaper(15);
        System.out.println(paper);
        // reader.close();
    }

    public MgWebReader(final MgWebReaderListener... listeners) {
        super(listeners);
    }

    public Double fetchEuroGoldExchangeRate() {
        driver.navigate().to(BASE_URL_ACCOUNT + "/" + MgMarket.FINANCIAL.urlChunk);
        pause();

        final WebElement mt13 = driver.findElement(By.className("mt13"));
        final List<WebElement> td = mt13.findElements(By.tagName("td"));
        final String euroGoldExchangeRate = td.get(1).getText();
        for (final MgWebReaderListener listener : listeners) {
            listener.onFetchEuroGoldExchangeRate(euroGoldExchangeRate);
        }
        return parseDouble(euroGoldExchangeRate);
    }

    public Double fetchGoldRonExchangeRate() {
        driver.navigate().to(BASE_URL_ACCOUNT + "/" + MgMarket.FINANCIAL.urlChunk + "/moneda_gold");
        pause();

        final WebElement mt13 = driver.findElement(By.className("mt13"));
        final List<WebElement> td = mt13.findElements(By.tagName("td"));
        final String goldRonExchangeRate = td.get(1).getText();
        for (final MgWebReaderListener listener : listeners) {
            listener.onFetchGoldRonExchangeRate(goldRonExchangeRate);
        }
        return parseDouble(goldRonExchangeRate);
    }

    public Newspaper fetchNewspaper(final int index) {
        final Newspaper newspaper = new Newspaper(index);

        final StringBuilder url = new StringBuilder(BASE_URL_ACCOUNT + "/special/local/latest/" + index);
        driver.navigate().to(url.toString());
        pause();

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

    public Double fetchPrice(final Goods goods, final MgMarket market) {
        final StringBuilder url = new StringBuilder(BASE_URL_ACCOUNT);
        url.append("/").append(market.urlChunk);
        url.append("/").append(goods.getCategory());
        url.append("/").append(goods.getName());
        url.append("/q").append(goods.getStars());
        driver.navigate().to(url.toString());
        pause();

        final WebElement nd_mkt_table_price_nr = driver.findElement(By.className("nd_mkt_table_price_nr"));
        final String price = nd_mkt_table_price_nr.getText();
        return parseDouble(price);
    }

    public Shares fetchShares() {
        driver.navigate().to(BASE_URL_ACCOUNT + "/partners/sharesmarket");
        pause();

        final List<WebElement> nd_part_amount_big = driver.findElements(By.className("nd_part_amount_big"));
        final List<WebElement> nd_part_amount_small = driver.findElements(By.className("nd_part_amount_small"));
        final String eurAmount = nd_part_amount_big.get(0).getText() + nd_part_amount_small.get(0).getText();
        for (final MgWebReaderListener listener : listeners) {
            listener.onFetchEurAmount(eurAmount);
        }
        final String goldAmount = nd_part_amount_big.get(1).getText() + nd_part_amount_small.get(2).getText();
        for (final MgWebReaderListener listener : listeners) {
            listener.onFetchGoldAmount(goldAmount);
        }
        final List<WebElement> nd_part_td = driver.findElement(By.className("nd_part_table")).findElements(By.className("nd_part_td"));
        final String sharePrice = nd_part_td.get(2).findElement(By.tagName("span")).getText();
        for (final MgWebReaderListener listener : listeners) {
            listener.onFetchSharePrice(sharePrice);
        }

        final Shares shares = new Shares(parseDouble(eurAmount), parseDouble(goldAmount), parseDouble(sharePrice));
        return shares;
    }

    public Double fetchWage() {
        driver.navigate().to(BASE_URL_ACCOUNT + "/work");
        pause();

        final WebElement nd_work_wage = driver.findElement(By.className("nd_work_wage"));
        final WebElement workWageElement = nd_work_wage.findElement(By.tagName("span"));
        final String workWage = workWageElement.getText();
        for (final MgWebReaderListener listener : listeners) {
            listener.onFetchWorkWage(workWage);
        }
        return parseDouble(workWage);
    }

}
