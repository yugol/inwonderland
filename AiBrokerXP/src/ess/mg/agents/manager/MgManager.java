package ess.mg.agents.manager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import ess.mg.agents.MgAgent;

public class MgManager extends MgAgent {

    public static void main(final String... args) {
        final MgManager manager = new MgManager();
        final Timer timer = new Timer(LIFE_TIME, new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent e) {
                manager.stop();
                System.exit(1);
            }

        });
        timer.setRepeats(false);
        timer.start();
        manager.start();
    }

    private static final int     LIFE_TIME        = 5 * 60 * 1000;

    private static final boolean MANAGE_COMPANIES = false;
    private static final double  MAX_HOURLY_WAGE  = 3;

    @Override
    public void run() {

        if (MANAGE_COMPANIES) {
            final AManageCompany manageCompany = new AManageCompany(this);
            manageCompany.setCompanyUrl("Vacca-Villa");
            manageCompany.setMaxHourlyWage(MAX_HOURLY_WAGE);
            manageCompany.perform();
        }

    }

}
