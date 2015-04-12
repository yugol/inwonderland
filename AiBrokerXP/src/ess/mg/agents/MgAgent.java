package ess.mg.agents;

import ess.common.EssLogger;
import ess.common.agents.EssAgent;
import ess.common.driver.EssDriverBase;
import ess.mg.MgContext;
import ess.mg.MgLogger;
import ess.mg.driver.MgWebDriver;

public abstract class MgAgent extends EssAgent {

    @Override
    public MgContext getContext() {
        return (MgContext) super.getContext();
    }

    @Override
    public MgWebDriver getDriver() {
        return (MgWebDriver) super.getDriver();
    }

    @Override
    public MgLogger getLogger() {
        return (MgLogger) super.getLogger();
    }

    @Override
    protected EssDriverBase newDriver() {
        return new MgWebDriver();
    }

    @Override
    protected EssLogger newLogger() {
        return new MgLogger();
    }

}
