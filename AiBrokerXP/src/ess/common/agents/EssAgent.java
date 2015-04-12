package ess.common.agents;

import ess.common.EssContext;
import ess.common.EssLogger;
import ess.common.actions.Action;
import ess.common.driver.EssDriverBase;

public abstract class EssAgent implements Runnable {

    private EssDriverBase driver;
    private EssContext    context;
    private EssLogger     logger;
    private long          waitTime = 0;

    public EssContext getContext() {
        return context;
    }

    public EssDriverBase getDriver() {
        return driver;
    }

    public EssLogger getLogger() {
        return logger;
    }

    public long getWaitTime() {
        return waitTime;
    }

    public void onActionTimeout(final Action<?, ?> action) {
        waitTime = 0;
        close();
    }

    public void setContext(final EssContext context) {
        this.context = context;
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

    public void stop() {
        close();
    }

    private void close() {
        if (driver != null) {
            driver.close();
        }
        driver = null;
    }

    private void initDriver() {
        close();
        driver = newDriver();
        logger = newLogger();
    }

    protected abstract EssDriverBase newDriver();

    protected abstract EssLogger newLogger();

}
