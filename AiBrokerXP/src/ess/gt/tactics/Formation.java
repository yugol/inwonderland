package ess.gt.tactics;

public enum Formation {

    _2_3_5 /*    */(Pos.GK, Pos.CB_, Pos.CB_, Pos.CM_, Pos.CM_, Pos.CM_, Pos.LW_, Pos.IF_, Pos.CF_, Pos.IF_, Pos.RW_),
    _3_4_3 /*    */(Pos.GK, Pos.CB_, Pos.CB_, Pos.CB_, Pos.SLM, Pos.CM_, Pos.CM_, Pos.SRM, Pos.LW_, Pos.CF_, Pos.RW_),
    _3_5_2 /*    */(Pos.GK, Pos.CB_, Pos.CB_, Pos.CB_, Pos.SLM, Pos.CM_, Pos.DM_, Pos.CM_, Pos.SRM, Pos.CF_, Pos.CF_),
    _3_6_1 /*    */(Pos.GK, Pos.CB_, Pos.CB_, Pos.CB_, Pos.MLB, Pos.OM_, Pos.DM_, Pos.OM_, Pos.DM_, Pos.MRB, Pos.CF_),
    _4_1_2_1_2 /**/(Pos.GK, Pos.FLB, Pos.CB_, Pos.CB_, Pos.FRB, Pos.SLM, Pos.OM_, Pos.DM_, Pos.SRM, Pos.CF_, Pos.CF_),
    _4_2_3_1 /*  */(Pos.GK, Pos.FLB, Pos.CB_, Pos.CB_, Pos.FRB, Pos.SLM, Pos.DM_, Pos.OM_, Pos.DM_, Pos.SRM, Pos.CF_),
    _4_2_4 /*    */(Pos.GK, Pos.FLB, Pos.CB_, Pos.CB_, Pos.FRB, Pos.CM_, Pos.CM_, Pos.LW_, Pos.CF_, Pos.CF_, Pos.LW_),
    _4_3_2_1 /*  */(Pos.GK, Pos.FLB, Pos.CB_, Pos.CB_, Pos.FRB, Pos.CM_, Pos.OM_, Pos.CM_, Pos.OM_, Pos.CM_, Pos.CF_),
    _4_3_3 /*    */(Pos.GK, Pos.FLB, Pos.CB_, Pos.CB_, Pos.FRB, Pos.CM_, Pos.CM_, Pos.CM_, Pos.LW_, Pos.CF_, Pos.LW_),
    _4_4_1_1 /*  */(Pos.GK, Pos.FLB, Pos.CB_, Pos.CB_, Pos.FRB, Pos.SLM, Pos.CM_, Pos.CM_, Pos.SRM, Pos.HO_, Pos.CF_),
    _4_4_2 /*    */(Pos.GK, Pos.FLB, Pos.CB_, Pos.CB_, Pos.FRB, Pos.SLM, Pos.CM_, Pos.CM_, Pos.SRM, Pos.CF_, Pos.CF_),
    _4_5_1 /*    */(Pos.GK, Pos.FLB, Pos.CB_, Pos.CB_, Pos.FRB, Pos.SLM, Pos.CM_, Pos.CM_, Pos.CM_, Pos.SRM, Pos.CF_),
    _5_3_2 /*    */(Pos.GK, Pos.WLB, Pos.CB_, Pos.SW_, Pos.CB_, Pos.WRB, Pos.CM_, Pos.CM_, Pos.CM_, Pos.CF_, Pos.CF_),
    _5_4_1 /*    */(Pos.GK, Pos.WLB, Pos.CB_, Pos.CB_, Pos.CB_, Pos.WRB, Pos.SLM, Pos.CM_, Pos.CM_, Pos.SRM, Pos.CF_),

    ;

    public final Pos[] team;

    private Formation(final Pos... team) {
        this.team = team;
    }

}
