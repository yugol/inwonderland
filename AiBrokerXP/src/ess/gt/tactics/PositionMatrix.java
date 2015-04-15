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

        put(Position.HO_, Position.CF_, 75);
        put(Position.HO_, Position.CM_, null);
        put(Position.HO_, Position.SLM, null);
        put(Position.HO_, Position.SRM, null);
        put(Position.HO_, Position.CB_, null);
        put(Position.HO_, Position.FLB, null);
        put(Position.HO_, Position.FRB, null);
        put(Position.HO_, Position.GK, null);

        put(Position.IF_, Position.CF_, null);
        put(Position.IF_, Position.CM_, null);
        put(Position.IF_, Position.SLM, null);
        put(Position.IF_, Position.SRM, null);
        put(Position.IF_, Position.CB_, null);
        put(Position.IF_, Position.FLB, null);
        put(Position.IF_, Position.FRB, null);
        put(Position.IF_, Position.GK, null);

        put(Position.LW_, Position.CF_, 75);
        put(Position.LW_, Position.CM_, null);
        put(Position.LW_, Position.SLM, null);
        put(Position.LW_, Position.SRM, null);
        put(Position.LW_, Position.CB_, null);
        put(Position.LW_, Position.FLB, null);
        put(Position.LW_, Position.FRB, null);
        put(Position.LW_, Position.GK, null);

        put(Position.RW_, Position.CF_, 75);
        put(Position.RW_, Position.CM_, null);
        put(Position.RW_, Position.SLM, null);
        put(Position.RW_, Position.SRM, null);
        put(Position.RW_, Position.CB_, null);
        put(Position.RW_, Position.FLB, null);
        put(Position.RW_, Position.FRB, null);
        put(Position.RW_, Position.GK, null);

        put(Position.DM_, Position.CF_, null);
        put(Position.DM_, Position.CM_, null);
        put(Position.DM_, Position.SLM, null);
        put(Position.DM_, Position.SRM, null);
        put(Position.DM_, Position.CB_, null);
        put(Position.DM_, Position.FLB, null);
        put(Position.DM_, Position.FRB, null);
        put(Position.DM_, Position.GK, null);

        put(Position.MLB, Position.CF_, null);
        put(Position.MLB, Position.CM_, 75);
        put(Position.MLB, Position.SLM, null);
        put(Position.MLB, Position.SRM, null);
        put(Position.MLB, Position.CB_, null);
        put(Position.MLB, Position.FLB, null);
        put(Position.MLB, Position.FRB, null);
        put(Position.MLB, Position.GK, null);

        put(Position.SLM, Position.CF_, null);
        put(Position.SLM, Position.CM_, 75);
        put(Position.SLM, Position.SLM, 100);
        put(Position.SLM, Position.SRM, null);
        put(Position.SLM, Position.CB_, null);
        put(Position.SLM, Position.FLB, null);
        put(Position.SLM, Position.FRB, null);
        put(Position.SLM, Position.GK, null);

        put(Position.SRM, Position.CF_, null);
        put(Position.SRM, Position.CM_, null);
        put(Position.SRM, Position.SLM, null);
        put(Position.SRM, Position.SRM, 100);
        put(Position.SRM, Position.CB_, null);
        put(Position.SRM, Position.FLB, null);
        put(Position.SRM, Position.FRB, null);
        put(Position.SRM, Position.GK, null);

        put(Position.CB_, Position.CF_, null);
        put(Position.CB_, Position.CM_, null);
        put(Position.CB_, Position.SLM, null);
        put(Position.CB_, Position.SRM, null);
        put(Position.CB_, Position.CB_, 100);
        put(Position.CB_, Position.FLB, null);
        put(Position.CB_, Position.FRB, null);
        put(Position.CB_, Position.GK, null);

        put(Position.FLB, Position.CF_, null);
        put(Position.FLB, Position.CM_, null);
        put(Position.FLB, Position.SLM, null);
        put(Position.FLB, Position.SRM, null);
        put(Position.FLB, Position.CB_, null);
        put(Position.FLB, Position.FLB, 100);
        put(Position.FLB, Position.FRB, null);
        put(Position.FLB, Position.GK, null);

        put(Position.FRB, Position.CF_, null);
        put(Position.FRB, Position.CM_, null);
        put(Position.FRB, Position.SLM, null);
        put(Position.FRB, Position.SRM, null);
        put(Position.FRB, Position.CB_, null);
        put(Position.FRB, Position.FLB, null);
        put(Position.FRB, Position.FRB, 100);
        put(Position.FRB, Position.GK, null);

        put(Position.SW_, Position.CF_, null);
        put(Position.SW_, Position.CM_, null);
        put(Position.SW_, Position.SLM, null);
        put(Position.SW_, Position.SRM, null);
        put(Position.SW_, Position.CB_, null);
        put(Position.SW_, Position.FLB, null);
        put(Position.SW_, Position.FRB, null);
        put(Position.SW_, Position.GK, null);

        put(Position.WLB, Position.CF_, null);
        put(Position.WLB, Position.CM_, null);
        put(Position.WLB, Position.SLM, null);
        put(Position.WLB, Position.SRM, null);
        put(Position.WLB, Position.CB_, null);
        put(Position.WLB, Position.FLB, null);
        put(Position.WLB, Position.FRB, null);
        put(Position.WLB, Position.GK, null);

        put(Position.WRB, Position.CF_, null);
        put(Position.WRB, Position.CM_, null);
        put(Position.WRB, Position.SLM, null);
        put(Position.WRB, Position.SRM, null);
        put(Position.WRB, Position.CB_, 75);
        put(Position.WRB, Position.FLB, null);
        put(Position.WRB, Position.FRB, null);
        put(Position.WRB, Position.GK, null);

        put(Position.GK, Position.CF_, null);
        put(Position.GK, Position.CM_, null);
        put(Position.GK, Position.SLM, null);
        put(Position.GK, Position.SRM, null);
        put(Position.GK, Position.CB_, null);
        put(Position.GK, Position.FLB, null);
        put(Position.GK, Position.FRB, null);
        put(Position.GK, Position.GK, 100);

    }

}
