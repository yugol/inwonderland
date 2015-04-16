package ess.gt.tactics.players;

import ess.gt.tactics.Player;
import ess.gt.tactics.Pos;

public class AlexandruCsonka extends Player {

    public static Player getInstance() {
        return instance;
    }

    static private final Player instance = new AlexandruCsonka();

    private AlexandruCsonka() {
        super(1, "Alexandru Csonka", 1, true, Pos.GK);
    }

}
