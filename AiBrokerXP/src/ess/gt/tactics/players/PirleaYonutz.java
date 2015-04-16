package ess.gt.tactics.players;

import ess.gt.tactics.Player;
import ess.gt.tactics.Pos;

public class PirleaYonutz extends Player {

    public static Player getInstance() {
        return instance;
    }

    static private final Player instance = new PirleaYonutz();

    private PirleaYonutz() {
        super(6, "Pirlea Yonutz", 1, false, Pos.SLM);
    }
}
