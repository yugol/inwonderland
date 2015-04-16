package ess.gt.tactics.players;

import ess.gt.tactics.Player;
import ess.gt.tactics.Pos;

public class FlorinelCristian extends Player {

    public static Player getInstance() {
        return instance;
    }

    static private final Player instance = new FlorinelCristian();

    private FlorinelCristian() {
        super(10, "Florinel Cristian", 1, true, Pos.RW_, Pos.HO_);
    }
}
