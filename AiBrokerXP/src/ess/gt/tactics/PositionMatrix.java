package ess.gt.tactics;

public class PositionMatrix {

    static public Double get(final Position pp, final Position tp) {
        return PPTP[pp.ordinal()][tp.ordinal()];
    }

    static private void put(final Position pp, final Position tp, final double pc) {
        PPTP[pp.ordinal()][tp.ordinal()] = pc;
    }

    static private void put(final Position pp, final Position tp, final Double pc) {
        PPTP[pp.ordinal()][tp.ordinal()] = pc;
    }

    static private final Double[][] PPTP = new Double[Position.values().length][Position.values().length];

    static {

        put(Position.HO, Position.CF, 75);
        put(Position.HO, Position.CM, null);
        put(Position.HO, Position.SLM, null);
        put(Position.HO, Position.SRM, null);
        put(Position.HO, Position.CB, null);
        put(Position.HO, Position.FLB, null);
        put(Position.HO, Position.FRB, null);
        put(Position.HO, Position.GK, null);

        put(Position.IF, Position.CF, null);
        put(Position.IF, Position.CM, null);
        put(Position.IF, Position.SLM, null);
        put(Position.IF, Position.SRM, null);
        put(Position.IF, Position.CB, null);
        put(Position.IF, Position.FLB, null);
        put(Position.IF, Position.FRB, null);
        put(Position.IF, Position.GK, null);

        put(Position.LW, Position.CF, 75);
        put(Position.LW, Position.CM, null);
        put(Position.LW, Position.SLM, null);
        put(Position.LW, Position.SRM, null);
        put(Position.LW, Position.CB, null);
        put(Position.LW, Position.FLB, null);
        put(Position.LW, Position.FRB, null);
        put(Position.LW, Position.GK, null);

        put(Position.RW, Position.CF, 75);
        put(Position.RW, Position.CM, null);
        put(Position.RW, Position.SLM, null);
        put(Position.RW, Position.SRM, null);
        put(Position.RW, Position.CB, null);
        put(Position.RW, Position.FLB, null);
        put(Position.RW, Position.FRB, null);
        put(Position.RW, Position.GK, null);

        put(Position.DM, Position.CF, null);
        put(Position.DM, Position.CM, null);
        put(Position.DM, Position.SLM, null);
        put(Position.DM, Position.SRM, null);
        put(Position.DM, Position.CB, null);
        put(Position.DM, Position.FLB, null);
        put(Position.DM, Position.FRB, null);
        put(Position.DM, Position.GK, null);

        put(Position.MLB, Position.CF, null);
        put(Position.MLB, Position.CM, 75);
        put(Position.MLB, Position.SLM, null);
        put(Position.MLB, Position.SRM, null);
        put(Position.MLB, Position.CB, null);
        put(Position.MLB, Position.FLB, null);
        put(Position.MLB, Position.FRB, null);
        put(Position.MLB, Position.GK, null);

        put(Position.SLM, Position.CF, null);
        put(Position.SLM, Position.CM, 75);
        put(Position.SLM, Position.SLM, 100);
        put(Position.SLM, Position.SRM, null);
        put(Position.SLM, Position.CB, null);
        put(Position.SLM, Position.FLB, null);
        put(Position.SLM, Position.FRB, null);
        put(Position.SLM, Position.GK, null);

        put(Position.SRM, Position.CF, null);
        put(Position.SRM, Position.CM, null);
        put(Position.SRM, Position.SLM, null);
        put(Position.SRM, Position.SRM, 100);
        put(Position.SRM, Position.CB, null);
        put(Position.SRM, Position.FLB, null);
        put(Position.SRM, Position.FRB, null);
        put(Position.SRM, Position.GK, null);

        put(Position.CB, Position.CF, null);
        put(Position.CB, Position.CM, null);
        put(Position.CB, Position.SLM, null);
        put(Position.CB, Position.SRM, null);
        put(Position.CB, Position.CB, 100);
        put(Position.CB, Position.FLB, null);
        put(Position.CB, Position.FRB, null);
        put(Position.CB, Position.GK, null);

        put(Position.FLB, Position.CF, null);
        put(Position.FLB, Position.CM, null);
        put(Position.FLB, Position.SLM, null);
        put(Position.FLB, Position.SRM, null);
        put(Position.FLB, Position.CB, null);
        put(Position.FLB, Position.FLB, 100);
        put(Position.FLB, Position.FRB, null);
        put(Position.FLB, Position.GK, null);

        put(Position.FRB, Position.CF, null);
        put(Position.FRB, Position.CM, null);
        put(Position.FRB, Position.SLM, null);
        put(Position.FRB, Position.SRM, null);
        put(Position.FRB, Position.CB, null);
        put(Position.FRB, Position.FLB, null);
        put(Position.FRB, Position.FRB, 100);
        put(Position.FRB, Position.GK, null);

        put(Position.SW, Position.CF, null);
        put(Position.SW, Position.CM, null);
        put(Position.SW, Position.SLM, null);
        put(Position.SW, Position.SRM, null);
        put(Position.SW, Position.CB, null);
        put(Position.SW, Position.FLB, null);
        put(Position.SW, Position.FRB, null);
        put(Position.SW, Position.GK, null);

        put(Position.WLB, Position.CF, null);
        put(Position.WLB, Position.CM, null);
        put(Position.WLB, Position.SLM, null);
        put(Position.WLB, Position.SRM, null);
        put(Position.WLB, Position.CB, null);
        put(Position.WLB, Position.FLB, null);
        put(Position.WLB, Position.FRB, null);
        put(Position.WLB, Position.GK, null);

        put(Position.WRB, Position.CF, null);
        put(Position.WRB, Position.CM, null);
        put(Position.WRB, Position.SLM, null);
        put(Position.WRB, Position.SRM, null);
        put(Position.WRB, Position.CB, 75);
        put(Position.WRB, Position.FLB, null);
        put(Position.WRB, Position.FRB, null);
        put(Position.WRB, Position.GK, null);

        put(Position.GK, Position.CF, null);
        put(Position.GK, Position.CM, null);
        put(Position.GK, Position.SLM, null);
        put(Position.GK, Position.SRM, null);
        put(Position.GK, Position.CB, null);
        put(Position.GK, Position.FLB, null);
        put(Position.GK, Position.FRB, null);
        put(Position.GK, Position.GK, 100);

    }

}
