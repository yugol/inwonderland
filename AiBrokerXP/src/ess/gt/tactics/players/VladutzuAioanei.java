package ess.gt.tactics.players;

import ess.gt.tactics.Player;
import ess.gt.tactics.Pos;

public class VladutzuAioanei extends Player {

    public static Player getInstance() {
        return instance;
    }

    static private final Player instance = new VladutzuAioanei();

    private VladutzuAioanei() {
        super(4, "Vladutzu Aioanei", 1, true, Pos.FRB);
    }
}
