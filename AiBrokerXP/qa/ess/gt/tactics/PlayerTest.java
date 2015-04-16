package ess.gt.tactics;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class PlayerTest {

    @Test
    public void testGetPositionCoefficientPositionBooleanPositionArray() {
        assertEquals(100d, Player.getPositionCoefficient(Pos.GK, true, Pos.GK, Pos.GK), 0);
        assertEquals(100d, Player.getPositionCoefficient(Pos.CB_, false, Pos.FLB, Pos.CB_), 0);
        assertEquals(63d, Player.getPositionCoefficient(Pos.FRB, false, Pos.SW_, Pos.WLB), 0);
    }

}
