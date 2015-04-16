package ess.gt.tactics.players;

import ess.gt.tactics.Player;
import ess.gt.tactics.Pos;

public class RonaldAttila extends Player {

    public static Player getInstance() {
        return instance;
    }

    static private final Player instance = new RonaldAttila();

    private RonaldAttila() {
        super(13, "Ronald Attila", 1, false, Pos.FLB, Pos.SW_);
    }
}
