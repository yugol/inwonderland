package ess.gt.tactics;

public class Player {

    private Boolean    health;
    private Integer    number;
    private String     name;
    private Integer    age;
    private Integer    talent;

    private Position   terrainPosition;
    private Position[] preferredPositions;
    private Double     positionCoefficient;

    private Integer    moral;
    private Integer    energy;
    private Integer    form;
    private Integer    experience;
    private Double     efficiencyCoefficient;

    private Integer    speed;
    private Integer    stamina;
    private Integer    goalkeeper;
    private Integer    tackling;
    private Integer    dribling;
    private Integer    longShot;
    private Integer    headShot;
    private Integer    shot;
    private Integer    passing;
    private Double     generalScore;

    private Double     actualScore;

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

    public Integer getNumber() {
        return number;
    }

    public Integer getPassing() {
        return passing;
    }

    public Double getPositionCoefficient() {
        return positionCoefficient;
    }

    public Position[] getPreferredPosition() {
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

    public Position getTerrainPosition() {
        return terrainPosition;
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

    public void setName(final String name) {
        this.name = name;
    }

    public void setNumber(final Integer number) {
        this.number = number;
    }

    public void setPassing(final Integer passing) {
        this.passing = passing;
    }

    public void setPositionCoefficient(final Double positionCoefficient) {
        this.positionCoefficient = positionCoefficient;
    }

    public void setPreferredPosition(final Position... preferredPositions) {
        this.preferredPositions = preferredPositions;
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

    public void setTalent(final Integer talent) {
        this.talent = talent;
    }

    public void setTerrainPosition(final Position terrainPosition) {
        this.terrainPosition = terrainPosition;
    }

}
