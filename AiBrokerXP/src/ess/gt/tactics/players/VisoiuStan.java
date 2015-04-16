package ess.gt.tactics.players;

import ess.gt.tactics.Player;
import ess.gt.tactics.Pos;

public class VisoiuStan extends Player {

    public static Player getInstance() {
        return instance;
    }

    static private final Player instance = new VisoiuStan();

    private VisoiuStan() {
        super(19, "Visoiu Stan", 1, true, Pos.GK);
    }
}
