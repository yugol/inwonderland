package ess.mg.driver;

import org.junit.Test;
import ess.common.Price;
import ess.mg.actions.WorkResult;

public class MgWebWorkerTest {

    @Test
    public void test() {
        final MgWebWorker worker = new MgWebDriver();
        worker.login();
        final WorkResult result = worker.work(Price.ron(160));
        System.out.println(result.getWage());
        System.out.println(result.getMessage());
    }

}
