package aibroker.model.cloud.sources.tranzactiibursiere;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
    BvbRegsNormDownloaderTest.class,
    BvbRegsOrigDownloaderTest.class,
    SibexFuturesTicksDownloaderTest.class
})
public class ZSuite {

}
