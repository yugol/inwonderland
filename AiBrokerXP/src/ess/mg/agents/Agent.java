package ess.mg.agents;

import ess.mg.MG;
import ess.mg.actions.Action;
import ess.mg.driver.MgWebDriver;

public abstract class Agent implements Runnable {

    public static long getWaitTime() {
        return waitTime;
    }

    public static void setRepeatAfter(final long waitTime) {
        Agent.waitTime = waitTime;
    }

    private static long waitTime = 0;

    private MgWebDriver driver;
    private final MG    global   = new MG();

    public Agent() {
    }

    public MgWebDriver getDriver() {
        return driver;
    }

    public MG getGlobal() {
        return global;
    }

    public void onActionTimeout(final Action<?> action) {
        waitTime = 0;
        close();
    }

    public void start() {
        initDriver();
        run();
    }

    private void initDriver() {
        close();
        driver = new MgWebDriver();
    }

    protected void close() {
        if (driver != null) {
            driver.close();
        }
        driver = null;
    }

}
