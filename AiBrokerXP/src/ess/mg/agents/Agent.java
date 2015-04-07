package ess.mg.agents;

import ess.mg.MG;
import ess.mg.agents.actions.Action;
import ess.mg.driver.MgWebDriver;

public abstract class Agent implements Runnable {

    private MgWebDriver driver;
    private final MG    global   = new MG();
    private long        waitTime = 0;

    public Agent() {
    }

    public MgWebDriver getDriver() {
        return driver;
    }

    public MG getGlobal() {
        return global;
    }

    public long getWaitTime() {
        return waitTime;
    }

    public void onActionTimeout(final Action<?> action) {
        waitTime = 0;
        close();
    }

    public void setRepeatAfter(final long waitTime) {
        this.waitTime = waitTime;
    }

    public void start() {
        do {
            try {
                Thread.sleep(getWaitTime());
                setRepeatAfter(-1);
                initDriver();
                run();
            } catch (final Exception ex) {
                ex.printStackTrace();
            }
        } while (getWaitTime() >= 0);
        close();
    }

    private void close() {
        if (driver != null) {
            driver.close();
        }
        driver = null;
    }

    protected void initDriver() {
        close();
        driver = new MgWebDriver();
    }

}
