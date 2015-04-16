package ess.gt.tactics;

public abstract class Player {

    public static Double getPositionCoefficient(final Pos terrain, final boolean rightLeg, final Pos... preffered) {
        if (preffered.length == 2) { return Math.max(getPositionCoefficient(terrain, rightLeg, preffered[0]), getPositionCoefficient(terrain, rightLeg, preffered[1])); }
        if (preffered.length == 1) {
            double positionCoefficient = 100;
            if (terrain != preffered[0]) {
                final int distance = Math.abs(terrain.role.ordinal() - preffered[0].role.ordinal());
                switch (distance) {
                    case 0:
                        positionCoefficient = 75;
                        break;
                    case 1:
                        positionCoefficient = 50;
                        break;
                    default:
                        positionCoefficient = 25;
                        break;
                }
                if (rightLeg && terrain.toString().contains("L")) {
                    positionCoefficient -= 12;
                }
                if (!rightLeg && terrain.toString().contains("R")) {
                    positionCoefficient -= 12;
                }
            }
            return positionCoefficient;
        }
        return null;
    }

    private Boolean       health;
    private final int     number;
    private final String  name;
    private Integer       age;
    private final Integer talent;
    private final boolean rightLeg;

    private Pos           terrainPosition;
    private final Pos[]   preferredPositions;

    private Integer       moral;
    private Integer       energy;
    private Integer       form;
    private Integer       experience;
    private Double        efficiencyCoefficient;

    private Integer       speed;
    private Integer       stamina;
    private Integer       goalkeeper;
    private Integer       tackling;
    private Integer       dribling;
    private Integer       longShot;
    private Integer       headShot;
    private Integer       shot;
    private Integer       passing;
    private Double        generalScore;

    private Double        actualScore;

    protected Player(final int number, final String name, final Integer talent, final boolean rightLeg,
            final Pos... preferredPositions) {
        super();
        this.number = number;
        this.name = name;
        this.talent = talent;
        this.rightLeg = rightLeg;
        this.preferredPositions = preferredPositions;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) { return true; }
        if (obj == null) { return false; }
        if (getClass() != obj.getClass()) { return false; }
        final Player other = (Player) obj;
        if (number != other.number) { return false; }
        return true;
    }

    public Double getActualScore() {
        return actualScore;
    }

    public Integer getAge() {
        return age;
    }

    public Integer getDribling() {
        return dribling;
    }

    public Double getEfficiencyCoefficient() {
        return efficiencyCoefficient;
    }

    public Integer getEnergy() {
        return energy;
    }

    public Integer getExperience() {
        return experience;
    }

    public Integer getForm() {
        return form;
    }

    public Double getGeneralScore() {
        return generalScore;
    }

    public Integer getGoalkeeper() {
        return goalkeeper;
    }

    public Integer getHeadShot() {
        return headShot;
    }

    public Boolean getHealth() {
        return health;
    }

    public Integer getLongShot() {
        return longShot;
    }

    public Integer getMoral() {
        return moral;
    }

    public String getName() {
        return name;
    }

    public int getNumber() {
        return number;
    }

    public Integer getPassing() {
        return passing;
    }

    public Double getPositionCoefficient() {
        return getPositionCoefficient(getTerrainPosition(), isRightLeg(), getPreferredPosition());
    }

    public Pos[] getPreferredPosition() {
        return preferredPositions;
    }

    public Integer getShot() {
        return shot;
    }

    public Integer getSpeed() {
        return speed;
    }

    public Integer getStamina() {
        return stamina;
    }

    public Integer getTackling() {
        return tackling;
    }

    public Integer getTalent() {
        return talent;
    }

    public Pos getTerrainPosition() {
        return terrainPosition;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + number;
        return result;
    }

    public boolean isRightLeg() {
        return rightLeg;
    }

    public void setActualScore(final Double actualScore) {
        this.actualScore = actualScore;
    }

    public void setAge(final Integer age) {
        this.age = age;
    }

    public void setDribling(final Integer dribling) {
        this.dribling = dribling;
    }

    public void setEfficiencyCoefficient(final Double efficiencyCoefficient) {
        this.efficiencyCoefficient = efficiencyCoefficient;
    }

    public void setEnergy(final Integer energy) {
        this.energy = energy;
    }

    public void setExperience(final Integer experience) {
        this.experience = experience;
    }

    public void setForm(final Integer form) {
        this.form = form;
    }

    public void setGeneralScore(final Double generalScore) {
        this.generalScore = generalScore;
    }

    public void setGoalkeeper(final Integer goalkeeper) {
        this.goalkeeper = goalkeeper;
    }

    public void setHeadShot(final Integer headShot) {
        this.headShot = headShot;
    }

    public void setHealth(final Boolean health) {
        this.health = health;
    }

    public void setLongShot(final Integer longShot) {
        this.longShot = longShot;
    }

    public void setMoral(final Integer moral) {
        this.moral = moral;
    }

    public void setPassing(final Integer passing) {
        this.passing = passing;
    }

    public void setShot(final Integer shot) {
        this.shot = shot;
    }

    public void setSpeed(final Integer speed) {
        this.speed = speed;
    }

    public void setStamina(final Integer stamina) {
        this.stamina = stamina;
    }

    public void setTackling(final Integer tackling) {
        this.tackling = tackling;
    }

    public void setTerrainPosition(final Pos terrainPosition) {
        this.terrainPosition = terrainPosition;
    }

}
