package aibroker;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
    aibroker.agents.ZSuite.class,
    aibroker.analysis.ZSuite.class,
    aibroker.model.ZSuite.class,
    aibroker.util.ZSuite.class
})
public class ZRunAll {

}
