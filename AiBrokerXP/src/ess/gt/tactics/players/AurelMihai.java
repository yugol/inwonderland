package ess.gt.tactics.players;

import ess.gt.tactics.Player;
import ess.gt.tactics.Pos;

public class AurelMihai extends Player {

    public static Player getInstance() {
        return instance;
    }

    static private final Player instance = new AurelMihai();

    private AurelMihai() {
        super(20, "Aurel Mihai", 1, true, Pos.HO_);
    }
}
