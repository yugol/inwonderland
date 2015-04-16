package ess.gt.tactics.players;

import ess.gt.tactics.Player;
import ess.gt.tactics.Pos;

public class RomocsaCurelea extends Player {

    public static Player getInstance() {
        return instance;
    }

    static private final Player instance = new RomocsaCurelea();

    private RomocsaCurelea() {
        super(17, "Romocsa Curelea", 1, true, Pos.DM_, Pos.SRM);
    }
}
