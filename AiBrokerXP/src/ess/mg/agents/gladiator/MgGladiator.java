package ess.mg.agents.gladiator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import javax.swing.Timer;
import aibroker.util.Moment;
import ess.mg.agents.MgAgent;
import ess.mg.agents.actions.ABuyGoods;
import ess.mg.agents.actions.ALogin;
import ess.mg.agents.dto.ArenaFightResult;
import ess.mg.agents.dto.PurchaseResult;
import ess.mg.goods.Quality;
import ess.mg.goods.food.Dairy;
import ess.mg.goods.weapons.Glasses;
import ess.mg.goods.weapons.Grenada;

public class MgGladiator extends MgAgent {

    public static void main(final String... args) {
        final Moment nowTime = Moment.getNow().getTimeMoment();
        boolean active = WEAPON_START_TIME.compareTo(nowTime) <= 0 && nowTime.compareTo(PAUSE_TIME) < 0;
        if (!active) {
            active = FIGHT_TIME.compareTo(nowTime) <= 0 && nowTime.compareTo(STOP_TIME) < 0;
        }
        // active = true;
        if (active) {
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

    private static final int    LIFE_TIME         = 7 * 60 * 1000;

    private static final Moment WEAPON_START_TIME = Moment.fromIso("23:30:00");
    private static final Moment WEAPON_STOP_TIME  = Moment.fromIso("23:45:00");
    private static final Moment PAUSE_TIME        = Moment.fromIso("23:59:00");

    private static final Moment FIGHT_TIME        = Moment.fromIso("00:30:00");
    private static final Moment STOP_TIME         = Moment.fromIso("01:15:00");

    @Override
    public void run() {

        final ALogin login = new ALogin(this);
        login.setEpoch(Moment.fromIso(Moment.getNow().toIsoDate(), WEAPON_START_TIME.toIsoTime()));
        login.setReadTransactions(false);
        login.perform();

        final Moment serverTime = getContext().getServerTime();

        if (WEAPON_START_TIME.compareTo(serverTime) <= 0 && serverTime.compareTo(WEAPON_STOP_TIME) < 0) {
            final ABuyGoods buyAttack = new ABuyGoods(this);
            buyAttack.setGoods(new Grenada(Quality.LOW));
            buyAttack.perform();

            final ABuyGoods buyDefence = new ABuyGoods(this);
            buyDefence.setGoods(new Glasses(Quality.LOW));
            buyDefence.perform();
        }

        if (getContext().getEnergy() < 5) {
            final ABuyGoods buyMilk = new ABuyGoods(this);
            buyMilk.setGoods(new Dairy(Quality.HIGH));
            for (int count = 0; count < 10; ++count) {
                final PurchaseResult purchaseResult = buyMilk.perform();
                if (purchaseResult.isSuccessful()) {
                    break;
                } else {
                    final Moment lastBuyTime = purchaseResult.getMessageTime().getTimeMoment();
                    final long delay = serverTime.getDelta(lastBuyTime, Calendar.MILLISECOND);
                    if (0 < delay && delay < LIFE_TIME) {
                        try {
                            Thread.sleep(delay);
                        } catch (final InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        break;
                    }
                }
            }
        }

        if (FIGHT_TIME.compareTo(serverTime) <= 0 && serverTime.compareTo(STOP_TIME) < 0) {
            for (int opponentIndex = 50; opponentIndex < 1000;) {
                try {
                    final ArenaFightResult result = getDriver().searchArenaOpponent(opponentIndex);
                    if (result.isOpponentFound() && !result.isSuccessful()) {
                        break;
                    }
                    opponentIndex = result.getOpponentIndex();
                } catch (final Exception e) {
                    opponentIndex += 10;
                }
            }
        }

    }

}
