package ess.gt.tactics.players;

import ess.gt.tactics.Player;
import ess.gt.tactics.Pos;

public class AlexandruMititean extends Player {

    public static Player getInstance() {
        return instance;
    }

    static private final Player instance = new AlexandruMititean();

    private AlexandruMititean() {
        super(5, "Alexandru Mititean", 1, true, Pos.CB_);
    }
}
