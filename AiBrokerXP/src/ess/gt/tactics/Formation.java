package ess.gt.tactics;

public enum Formation {

    _2_3_5 /*    */(Position.GK, Position.CB, Position.CB, Position.CM, Position.CM, Position.CM, Position.LW, Position.IF, Position.CF, Position.IF, Position.RW),
    _3_4_3 /*    */(Position.GK, Position.CB, Position.CB, Position.CB, Position.SLM, Position.CM, Position.CM, Position.SRM, Position.LW, Position.CF, Position.RW),
    _3_5_2 /*    */(Position.GK, Position.CB, Position.CB, Position.CB, Position.SLM, Position.CM, Position.DM, Position.CM, Position.SRM, Position.CF, Position.CF),
    _3_6_1 /*    */(Position.GK, Position.CB, Position.CB, Position.CB, Position.MLB, Position.OM, Position.DM, Position.OM, Position.DM, Position.MRB, Position.CF),
    _4_1_2_1_2 /**/(Position.GK, Position.FLB, Position.CB, Position.CB, Position.FRB, Position.SLM, Position.OM, Position.DM, Position.SRM, Position.CF, Position.CF),
    _4_2_3_1 /*  */(Position.GK, Position.FLB, Position.CB, Position.CB, Position.FRB, Position.SLM, Position.DM, Position.OM, Position.DM, Position.SRM, Position.CF),
    _4_2_4 /*    */(Position.GK, Position.FLB, Position.CB, Position.CB, Position.FRB, Position.CM, Position.CM, Position.LW, Position.CF, Position.CF, Position.LW),
    _4_3_2_1 /*  */(Position.GK, Position.FLB, Position.CB, Position.CB, Position.FRB, Position.CM, Position.OM, Position.CM, Position.OM, Position.CM, Position.CF),
    _4_3_3 /*    */(Position.GK, Position.FLB, Position.CB, Position.CB, Position.FRB, Position.CM, Position.CM, Position.CM, Position.LW, Position.CF, Position.LW),
    _4_4_1_1 /*  */(Position.GK, Position.FLB, Position.CB, Position.CB, Position.FRB, Position.SLM, Position.CM, Position.CM, Position.SRM, Position.HO, Position.CF),
    _4_4_2 /*    */(Position.GK, Position.FLB, Position.CB, Position.CB, Position.FRB, Position.SLM, Position.CM, Position.CM, Position.SRM, Position.CF, Position.CF),
    _4_5_1 /*    */(Position.GK, Position.FLB, Position.CB, Position.CB, Position.FRB, Position.SLM, Position.CM, Position.CM, Position.CM, Position.SRM, Position.CF),
    _5_3_2 /*    */(Position.GK, Position.WLB, Position.CB, Position.SW, Position.CB, Position.WRB, Position.CM, Position.CM, Position.CM, Position.CF, Position.CF),
    _5_4_1 /*    */(Position.GK, Position.WLB, Position.CB, Position.CB, Position.CB, Position.WRB, Position.SLM, Position.CM, Position.CM, Position.SRM, Position.CF),

    ;

    public final Position[] team;

    private Formation(final Position... team) {
        this.team = team;
    }

}
