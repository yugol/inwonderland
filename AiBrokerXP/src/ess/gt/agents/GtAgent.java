package ess.gt.agents;

import ess.common.EssLogger;
import ess.common.agents.EssAgent;
import ess.common.driver.EssDriverBase;
import ess.gt.GtContext;
import ess.gt.GtLogger;
import ess.gt.driver.GtWebDriver;

public abstract class GtAgent extends EssAgent {

    @Override
    public GtContext getContext() {
        return (GtContext) super.getContext();
    }

    @Override
    public GtWebDriver getDriver() {
        return (GtWebDriver) super.getDriver();
    }

    @Override
    public GtLogger getLogger() {
        return (GtLogger) super.getLogger();
    }

    @Override
    protected EssDriverBase newDriver() {
        return new GtWebDriver();
    }

    @Override
    protected EssLogger newLogger() {
        return new GtLogger();
    }

}
