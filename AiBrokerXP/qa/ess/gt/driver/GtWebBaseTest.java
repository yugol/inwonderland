package ess.gt.driver;

import org.junit.Test;
import ess.gt.GtContext;

public class GtWebBaseTest {

    @Test
    public void testLogin() {
        final GtWebBase base = new GtWebDriver();
        final GtContext context = base.login();
        System.out.println(context.getActiveUsersCount());
    }

}
