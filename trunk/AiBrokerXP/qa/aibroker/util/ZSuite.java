package aibroker.util;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
    DbUtilTest.class,
    MergeUtilTest.class,
    MomentTest.class,
    SamplingUtilTest.class,
    aibroker.util.convenience.ZSuite.class
})
public class ZSuite {

}
