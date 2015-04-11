package ess.mg.agents.sampler;

import ess.mg.MgLogger;
import ess.mg.agents.Agent;

public class Sampler extends Agent {

    public static void main(final String... args) {
        new Sampler().start();
    }

    private static final int TIMEOUT = 5 * 60 * 1000;

    @Override
    public void run() {
        new ASample(this, TIMEOUT).perform();
    }

    @Override
    protected void initDriver() {
        super.initDriver();
        getDriver().addRecordLogger(new MgLogger());
    }

}
