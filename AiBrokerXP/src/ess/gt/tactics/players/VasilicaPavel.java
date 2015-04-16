package ess.gt.tactics.players;

import ess.gt.tactics.Player;
import ess.gt.tactics.Pos;

public class VasilicaPavel extends Player {

    public static Player getInstance() {
        return instance;
    }

    static private final Player instance = new VasilicaPavel();

    private VasilicaPavel() {
        super(7, "Vasilica Pavel", 1, false, Pos.SLM);
    }
}
