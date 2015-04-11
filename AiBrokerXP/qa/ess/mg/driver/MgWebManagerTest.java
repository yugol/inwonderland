package ess.mg.driver;

import org.junit.Test;

public class MgWebManagerTest {

    @Test
    public void testActivateJob() {
        final MgWebManager manager = new MgWebDriver();
        manager.login();
        manager.activateJob("Vacca-Villa", 0, 2.33);
    }

}
