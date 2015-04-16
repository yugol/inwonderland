package ess.gt.tactics.players;

import ess.gt.tactics.Player;
import ess.gt.tactics.Pos;

public class MadalinAlex extends Player {

    public static Player getInstance() {
        return instance;
    }

    static private final Player instance = new MadalinAlex();

    private MadalinAlex() {
        super(3, "Madalin Alex", 1, false, Pos.SW_, Pos.WLB);
    }
}
