package ess.gt.tactics;

public enum Pos {

    CF_ ("center forward", Role.FORWARD),
    HO_ ("hole", Role.FORWARD),
    IF_ ("inside forward", Role.FORWARD),
    LW_ ("left winger", Role.FORWARD),
    RW_ ("right winger", Role.FORWARD),

    CM_ ("center midfielder", Role.MIDFIELDER),
    DM_ ("defensive midfielder", Role.MIDFIELDER),
    MLB ("midfield left back", Role.MIDFIELDER),
    MRB ("midfield right back", Role.MIDFIELDER),
    OM_ ("offensive midfielder", Role.MIDFIELDER),
    SLM ("side left midfielder", Role.MIDFIELDER),
    SRM ("side right midfielder", Role.MIDFIELDER),

    CB_ ("center back", Role.DEFENDER),
    FLB ("full left back", Role.DEFENDER),
    FRB ("full right back", Role.DEFENDER),
    SW_ ("sweeper", Role.DEFENDER),
    WLB ("wing left back", Role.DEFENDER),
    WRB ("wing right back", Role.DEFENDER),

    GK ("goalkeeper", Role.GOALKEEPER),

    ;

    public final String name;
    public final Role   role;

    private Pos(final String name, final Role role) {
        this.name = name;
        this.role = role;
    }

}
