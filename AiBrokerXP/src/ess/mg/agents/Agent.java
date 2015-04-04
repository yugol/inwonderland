package ess.mg.agents;

import ess.mg.MG;
import ess.mg.actions.Action;
import ess.mg.driver.MgWebDriver;

public abstract class Agent implements Runnable {

    public static long getWaitTime() {
        return waitTime;
    }

    public static void setWaitTime(final long waitTime) {
        Agent.waitTime = waitTime;
    }

    private static long waitTime = 1;

    private MgWebDriver driver;
    private final MG    global   = new MG();

    public Agent() {
        reset();
    }

    public MgWebDriver getDriver() {
        return driver;
    }

    public MG getGlobal() {
        return global;
    }

    public void onActionTimeout(final Action<?> action) {
        reset();
        run();
    }

    protected void reset() {
        if (driver != null) {
            driver.close();
        }
        driver = new MgWebDriver();
    }

}
