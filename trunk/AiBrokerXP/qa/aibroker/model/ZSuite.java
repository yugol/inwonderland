package aibroker.model;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
    QuotesTest.class,
    SeqSelTest.class,
    aibroker.model.cloud.ZSuite.class,
    aibroker.model.domains.ZSuite.class,
    aibroker.model.drivers.ZSuite.class
})
public class ZSuite {

}
