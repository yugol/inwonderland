package aibroker;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
    aibroker.agents.sibex.ZSuite.class,
    aibroker.analysis.ZSuite.class,
    aibroker.util.ZSuite.class
})
public class ZSuite {

}
