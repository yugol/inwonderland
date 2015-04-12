package ess.gt.agents.operator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import ess.gt.GtContext;
import ess.gt.agents.GtAgent;

public class Operator extends GtAgent {

    public static void main(final String... args) {
        final Operator operator = new Operator();
        final Timer timer = new Timer(LIFE_TIME, new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent e) {
                operator.stop();
                System.exit(1);
            }

        });
        timer.setRepeats(false);
        timer.start();
        operator.start();
    }

    private static final int LIFE_TIME = 5 * 60 * 1000;

    @Override
    public void run() {
        final GtContext context = getDriver().login();
        getLogger().logActiveUsersCount(context.getActiveUsersCount());

        getDriver().fetchShares(context);
        getLogger().logShares(context.getShares());

        getDriver().fetchGoldRonExchangeRate(context);
        getLogger().logGoldRonRate(context.getGoldRonExchangeRate());

        getDriver().fetchEuroGoldExchangeRate(context);
        getLogger().logEuroGoldRate(context.getEuroGoldExchangeRate());
    }

}
