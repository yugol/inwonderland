package ess.gt.tactics;

public enum Formation {

    _2_3_5 /*    */(Position.GK, Position.CB_, Position.CB_, Position.CM_, Position.CM_, Position.CM_, Position.LW_, Position.IF_, Position.CF_, Position.IF_, Position.RW_),
    _3_4_3 /*    */(Position.GK, Position.CB_, Position.CB_, Position.CB_, Position.SLM, Position.CM_, Position.CM_, Position.SRM, Position.LW_, Position.CF_, Position.RW_),
    _3_5_2 /*    */(Position.GK, Position.CB_, Position.CB_, Position.CB_, Position.SLM, Position.CM_, Position.DM_, Position.CM_, Position.SRM, Position.CF_, Position.CF_),
    _3_6_1 /*    */(Position.GK, Position.CB_, Position.CB_, Position.CB_, Position.MLB, Position.OM_, Position.DM_, Position.OM_, Position.DM_, Position.MRB, Position.CF_),
    _4_1_2_1_2 /**/(Position.GK, Position.FLB, Position.CB_, Position.CB_, Position.FRB, Position.SLM, Position.OM_, Position.DM_, Position.SRM, Position.CF_, Position.CF_),
    _4_2_3_1 /*  */(Position.GK, Position.FLB, Position.CB_, Position.CB_, Position.FRB, Position.SLM, Position.DM_, Position.OM_, Position.DM_, Position.SRM, Position.CF_),
    _4_2_4 /*    */(Position.GK, Position.FLB, Position.CB_, Position.CB_, Position.FRB, Position.CM_, Position.CM_, Position.LW_, Position.CF_, Position.CF_, Position.LW_),
    _4_3_2_1 /*  */(Position.GK, Position.FLB, Position.CB_, Position.CB_, Position.FRB, Position.CM_, Position.OM_, Position.CM_, Position.OM_, Position.CM_, Position.CF_),
    _4_3_3 /*    */(Position.GK, Position.FLB, Position.CB_, Position.CB_, Position.FRB, Position.CM_, Position.CM_, Position.CM_, Position.LW_, Position.CF_, Position.LW_),
    _4_4_1_1 /*  */(Position.GK, Position.FLB, Position.CB_, Position.CB_, Position.FRB, Position.SLM, Position.CM_, Position.CM_, Position.SRM, Position.HO_, Position.CF_),
    _4_4_2 /*    */(Position.GK, Position.FLB, Position.CB_, Position.CB_, Position.FRB, Position.SLM, Position.CM_, Position.CM_, Position.SRM, Position.CF_, Position.CF_),
    _4_5_1 /*    */(Position.GK, Position.FLB, Position.CB_, Position.CB_, Position.FRB, Position.SLM, Position.CM_, Position.CM_, Position.CM_, Position.SRM, Position.CF_),
    _5_3_2 /*    */(Position.GK, Position.WLB, Position.CB_, Position.SW_, Position.CB_, Position.WRB, Position.CM_, Position.CM_, Position.CM_, Position.CF_, Position.CF_),
    _5_4_1 /*    */(Position.GK, Position.WLB, Position.CB_, Position.CB_, Position.CB_, Position.WRB, Position.SLM, Position.CM_, Position.CM_, Position.SRM, Position.CF_),

    ;

    public final Position[] team;

    private Formation(final Position... team) {
        this.team = team;
    }

}
