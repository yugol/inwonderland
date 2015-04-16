package ess.gt.tactics.players;

import ess.gt.tactics.Player;
import ess.gt.tactics.Pos;

public class BocuBasca extends Player {

    public static Player getInstance() {
        return instance;
    }

    static private final Player instance = new BocuBasca();

    private BocuBasca() {
        super(8, "Bocu Basca", 1, true, Pos.DM_);
    }
}
