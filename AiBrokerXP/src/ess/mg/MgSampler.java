package ess.mg;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import ess.mg.driver.MgWebReader;
import ess.mg.driver.MgWebRecordLogger;

public class MgSampler {

    public static void main(final String... args) {
        final MgWebReader reader = new MgWebReader(new MgWebRecordLogger());

        final Timer timer = new Timer(EXIT_TIME, new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent e) {
                reader.close();
                System.exit(1);
            }

        });
        timer.setRepeats(false);
        timer.start();

        reader.login();
        reader.fetchWage();
        reader.fetchShares();
        reader.fetchEuroGoldExchangeRate();
        reader.fetchGoldRonExchangeRate();
        reader.close();
    }

    private static final int EXIT_TIME = 1000 * 60 * 3;

}
