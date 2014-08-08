package aibroker.model.cloud.sources;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
    aibroker.model.cloud.sources.bvb.ZSuite.class,
    aibroker.model.cloud.sources.sibex.ZSuite.class,
    aibroker.model.cloud.sources.tranzactiibursiere.ZSuite.class,
    aibroker.model.cloud.sources.yahoo.ZSuite.class
})
public class ZSuite {

}
