package ess.mg.agents.gladiator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import javax.swing.Timer;
import aibroker.util.Moment;
import ess.mg.agents.MgAgent;
import ess.mg.agents.actions.ABuyGoods;
import ess.mg.agents.actions.ALogin;
import ess.mg.agents.dto.PurchaseResult;
import ess.mg.driver.dto.Transactions;
import ess.mg.goods.Quality;
import ess.mg.goods.food.Dairy;
import ess.mg.goods.weapons.Glasses;
import ess.mg.goods.weapons.Grenada;

public class MgGladiator extends MgAgent {

    public static void main(final String... args) {
        final Moment nowTime = Moment.fromIso(Moment.getNow().toIsoTime());
        if (WEAPON_TIME_START.compareTo(nowTime) <= 0 && nowTime.compareTo(ENERGY_TIME_STOP) < 0) {
            final MgGladiator gladiator = new MgGladiator();
            final Timer timer = new Timer(LIFE_TIME, new ActionListener() {

                @Override
                public void actionPerformed(final ActionEvent e) {
                    gladiator.stop();
                    System.exit(1);
                }

            });
            timer.setRepeats(false);
            timer.start();
            gladiator.start();
        }
    }

    private static final int    LIFE_TIME         = 10 * 60 * 1000;

    private static final Moment ARENA_TIME        = Moment.fromIso("23:30:00");
    private static final Moment WEAPON_TIME_START = ARENA_TIME.newAdd(Calendar.MINUTE, 1);
    private static final Moment WEAPON_TIME_STOP  = WEAPON_TIME_START.newAdd(Calendar.MINUTE, 13);
    private static final Moment ENERGY_TIME_START = ARENA_TIME.newAdd(Calendar.MINUTE, 1);
    private static final Moment ENERGY_TIME_STOP  = ENERGY_TIME_START.newAdd(Calendar.MINUTE, 29);

    @Override
    public void run() {

        final ALogin login = new ALogin(this);
        login.setEpoch(Moment.fromIso(Moment.getNow().toIsoDate(), ARENA_TIME.toIsoTime()));
        login.setReadTransactions(true);
        login.perform();

        final Moment serverTime = getContext().getServerTime();
        final Transactions transactions = getContext().getTransactions();

        if (WEAPON_TIME_START.compareTo(serverTime) <= 0 && serverTime.compareTo(WEAPON_TIME_STOP) < 0) {
            final ABuyGoods buyAttack = new ABuyGoods(this);
            buyAttack.setGoods(new Grenada(Quality.LOW));
            buyAttack.perform();

            final ABuyGoods buyDefence = new ABuyGoods(this);
            buyDefence.setGoods(new Glasses(Quality.LOW));
            buyDefence.perform();
        }

        if (ENERGY_TIME_START.compareTo(serverTime) <= 0 && serverTime.compareTo(ENERGY_TIME_STOP) < 0) {
            if (getContext().getEnergy() < 5) {
                if (transactions.getMilkCount() <= 0) {
                    final ABuyGoods buyMilk = new ABuyGoods(this);
                    buyMilk.setGoods(new Dairy(Quality.HIGH));
                    for (int count = 0; count < 10; ++count) {
                        final PurchaseResult purchaseResult = buyMilk.perform();
                        if (purchaseResult.isSuccessful()) {
                            break;
                        }
                        try {
                            Thread.sleep(50 * 1000);
                        } catch (final InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

    }

}
