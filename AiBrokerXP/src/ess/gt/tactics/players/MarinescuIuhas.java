package ess.gt.tactics.players;

import ess.gt.tactics.Player;
import ess.gt.tactics.Pos;

public class MarinescuIuhas extends Player {

    public static Player getInstance() {
        return instance;
    }

    static private final Player instance = new MarinescuIuhas();

    private MarinescuIuhas() {
        super(2, "Marinescu Iuhas", 1, false, Pos.FLB, Pos.CB_);
    }

}
