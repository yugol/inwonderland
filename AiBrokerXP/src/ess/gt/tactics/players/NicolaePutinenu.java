package ess.gt.tactics.players;

import ess.gt.tactics.Player;
import ess.gt.tactics.Pos;

public class NicolaePutinenu extends Player {

    public static Player getInstance() {
        return instance;
    }

    static private final Player instance = new NicolaePutinenu();

    private NicolaePutinenu() {
        super(12, "Nicolae Putinenu", 1, false, Pos.GK);
    }
}
