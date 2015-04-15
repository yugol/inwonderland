package ess.gt.tactics;

public enum Position {

    CF ("center forward", Role.FORWARD),
    HO ("hole", Role.FORWARD),
    IF ("inside forward", Role.FORWARD),
    LW ("left winger", Role.FORWARD),
    RW ("right winger", Role.FORWARD),

    CM ("center midfielder", Role.MIDFIELDER),
    DM ("defensive midfielder", Role.MIDFIELDER),
    MLB ("midfield left back", Role.MIDFIELDER),
    MRB ("midfield right back", Role.MIDFIELDER),
    OM ("offensive midfielder", Role.MIDFIELDER),
    SLM ("side left midfielder", Role.MIDFIELDER),
    SRM ("side right midfielder", Role.MIDFIELDER),

    CB ("center back", Role.DEFENDER),
    FLB ("full left back", Role.DEFENDER),
    FRB ("full right back", Role.DEFENDER),
    SW ("sweeper", Role.DEFENDER),
    WLB ("wing left back", Role.DEFENDER),
    WRB ("wing right back", Role.DEFENDER),

    GK ("goalkeeper", Role.GOALKEEPER),

    ;

    public final String name;
    public final Role   role;

    private Position(final String name, final Role role) {
        this.name = name;
        this.role = role;
    }

}
