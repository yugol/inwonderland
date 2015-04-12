package ess.gt.driver;

import org.junit.Test;
import ess.gt.GtLogger;
import ess.gt.driver.GtWebPager.DataPoint;

public class GtWebPagerTest {

    @Test
    public void testReadGoldRonRates() {
        final GtLogger logger = new GtLogger();
        final GtWebPager pager = new GtWebPager();
        pager.login();
        for (int i = 0; i < 100000; i += 10) {
            final DataPoint point = pager.readGoldRonRates(i);
            logger.log(point.toString());
        }
    }

}
