package ess.gt.tactics.players;

import ess.gt.tactics.Player;
import ess.gt.tactics.Pos;

public class BogdanCislaru extends Player {

    public static Player getInstance() {
        return instance;
    }

    static private final Player instance = new BogdanCislaru();

    private BogdanCislaru() {
        super(18, "Bogdan Cislaru", 1, true, Pos.IF_, Pos.RW_);
    }
}
