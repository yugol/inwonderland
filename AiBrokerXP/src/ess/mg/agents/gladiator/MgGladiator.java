package ess.mg.agents.gladiator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import aibroker.util.Moment;
import ess.common.Price;
import ess.mg.MgMarket;
import ess.mg.agents.MgAgent;
import ess.mg.agents.actions.ABuyGoods;
import ess.mg.agents.actions.ALogin;
import ess.mg.agents.dto.ArenaFightResult;
import ess.mg.goods.Quality;
import ess.mg.goods.weapons.Glasses;
import ess.mg.goods.weapons.Grenada;
import ess.mg.goods.weapons.Knife;

public class MgGladiator extends MgAgent {

    public static void main(final String... args) {
        final Moment nowTime = Moment.getNow().getTimeMoment();
        boolean active = WEAPON_START_TIME.compareTo(nowTime) <= 0 && nowTime.compareTo(PAUSE_TIME) < 0;
        if (!active) {
            active = FIGHT_ST_TIME.compareTo(nowTime) <= 0 && nowTime.compareTo(STOP_TIME) < 0;
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

    static private final int      LIFE_TIME         = 7 * 60 * 1000;
    static private final MgMarket WEAPONS_MARKET    = MgMarket.LOCAL;
    static private final Quality  WEAPONS_QUALITY   = Quality.LOW;

    static private final Moment   WEAPON_START_TIME = Moment.fromIso("23:30:00");
    static private final Moment   WEAPON_STOP_TIME  = Moment.fromIso("23:45:00");
    static private final Moment   PAUSE_TIME        = Moment.fromIso("23:59:00");

    static private final Moment   FIGHT_ST_TIME     = Moment.fromIso("00:30:00");
    static private final Moment   STOP_TIME         = Moment.fromIso("01:15:00");

    @Override
    public void run() {

        final ALogin login = new ALogin(this);
        login.setReadTransactions(false);
        login.perform();

        final Moment serverTime = getContext().getServerTime();

        if (WEAPON_START_TIME.compareTo(serverTime) <= 0 && serverTime.compareTo(WEAPON_STOP_TIME) < 0) {
            if (getContext().getRonAmount() > 10) {

                final ABuyGoods buyAttack = new ABuyGoods(this);
                buyAttack.setMarket(WEAPONS_MARKET);
                buyAttack.setGoods(new Knife(WEAPONS_QUALITY));
                try {
                    final Price knifePrice = getDriver().fetchPrice(new Knife(WEAPONS_QUALITY), WEAPONS_MARKET);
                    final Price grenadaPrice = getDriver().fetchPrice(new Grenada(WEAPONS_QUALITY), WEAPONS_MARKET);
                    if (grenadaPrice.compareTo(knifePrice) <= 0) {
                        buyAttack.setGoods(new Grenada(WEAPONS_QUALITY));
                    }
                } catch (final Exception e) {
                }
                buyAttack.perform();

                final ABuyGoods buyDefence = new ABuyGoods(this);
                buyDefence.setMarket(WEAPONS_MARKET);
                buyDefence.setGoods(new Glasses(WEAPONS_QUALITY));
                buyDefence.perform();

            }
        }

        if (FIGHT_ST_TIME.compareTo(serverTime) <= 0 && serverTime.compareTo(STOP_TIME) < 0) {
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
