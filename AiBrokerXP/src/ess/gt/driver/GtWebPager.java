package ess.gt.driver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import aibroker.util.Moment;
import ess.common.EssLogger;

public class GtWebPager extends GtWebReader {

    public class DataPoint {

        private final Moment moment;
        private final Double price;

        public DataPoint(final Moment moment, final Double price) {
            this.moment = moment;
            this.price = price;
        }

        public Moment getMoment() {
            return moment;
        }

        public Double getPrice() {
            return price;
        }

        @Override
        public String toString() {
            return moment.toIsoDatetime() + EssLogger.SEPARATOR + String.valueOf(price);
        }

    }

    public DataPoint readGoldRonRates(final int index) {
        navigateTo(BASE_URL_ACCOUNT + "/financial/gold2local/" + index);

        final WebElement time = driver.findElement(By.cssSelector("td[class='_mr_table_col1'] span"));
        final String moment = time.getAttribute("title");
        final WebElement value = driver.findElement(By.cssSelector("td[class='_mr_table_col4'] span"));
        final String price = value.getText();
        final DataPoint point = new DataPoint(
                Moment.fromIso(moment),
                parseDouble(normalizeNumberString(price)));
        return point;
    }
}
