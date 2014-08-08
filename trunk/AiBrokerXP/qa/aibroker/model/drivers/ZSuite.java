package aibroker.model.drivers;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
    aibroker.model.drivers.amibroker.ZSuite.class,
    aibroker.model.drivers.csv.ZSuite.class,
    aibroker.model.drivers.metastock.ZSuite.class,
    aibroker.model.drivers.sql.ZSuite.class
})
public class ZSuite {

}
