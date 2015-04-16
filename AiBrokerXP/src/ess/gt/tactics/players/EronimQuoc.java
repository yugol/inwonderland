package ess.gt.tactics.players;

import ess.gt.tactics.Player;
import ess.gt.tactics.Pos;

public class EronimQuoc extends Player {

    public static Player getInstance() {
        return instance;
    }

    static private final Player instance = new EronimQuoc();

    private EronimQuoc() {
        super(15, "Eronim Quoc", 1, false, Pos.LW_, Pos.RW_);
    }
}
