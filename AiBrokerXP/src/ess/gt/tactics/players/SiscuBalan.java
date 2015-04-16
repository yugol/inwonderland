package ess.gt.tactics.players;

import ess.gt.tactics.Player;
import ess.gt.tactics.Pos;

public class SiscuBalan extends Player {

    public static Player getInstance() {
        return instance;
    }

    static private final Player instance = new SiscuBalan();

    private SiscuBalan() {
        super(14, "Siscu Balan", 1, false, Pos.MLB, Pos.SLM);
    }
}
