package ess.gt.tactics.players;

import ess.gt.tactics.Player;
import ess.gt.tactics.Pos;

public class DaniBolunlon extends Player {

    public static Player getInstance() {
        return instance;
    }

    static private final Player instance = new DaniBolunlon();

    private DaniBolunlon() {
        super(11, "Dani Bolunlon", 1, true, Pos.RW_, Pos.IF_);
    }
}
