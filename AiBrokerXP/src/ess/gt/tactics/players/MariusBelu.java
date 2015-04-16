package ess.gt.tactics.players;

import ess.gt.tactics.Player;
import ess.gt.tactics.Pos;

public class MariusBelu extends Player {

    public static Player getInstance() {
        return instance;
    }

    static private final Player instance = new MariusBelu();

    private MariusBelu() {
        super(9, "Marius Belu", 1, false, Pos.SLM);
    }
}
