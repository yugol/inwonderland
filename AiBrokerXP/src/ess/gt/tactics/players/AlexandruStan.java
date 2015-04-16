package ess.gt.tactics.players;

import ess.gt.tactics.Player;
import ess.gt.tactics.Pos;

public class AlexandruStan extends Player {

    public static Player getInstance() {
        return instance;
    }

    static private final Player instance = new AlexandruStan();

    private AlexandruStan() {
        super(16, "Alexandru Stan", 1, true, Pos.WRB);
    }
}
