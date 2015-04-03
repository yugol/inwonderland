package ess.mg.agents;

import ess.mg.optimizers.NewspaperOptimizer;

public class MgNewsReporter {

    public static void main(final String... args) {
        new NewspaperOptimizer(PAPER_COUNT, TIMEOUT).createReport();
    }

    public static final int  PAPER_COUNT = 35;
    private static final int TIMEOUT     = 1000 * 60 * 10;

}
