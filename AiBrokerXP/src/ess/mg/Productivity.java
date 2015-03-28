package ess.mg;

public class Productivity {

    public static double ENERGY     = 1.42;
    public static double EXPERIENCE = 5.15;
    public static double KNOWLEDGE  = 1.0;

    private double       energy;
    private double       experience;
    private double       knowledge;

    public Productivity() {
        this(ENERGY, EXPERIENCE, KNOWLEDGE);
    }

    public Productivity(final double energy, final double experience, final double knowkedge) {
        setEnergy(energy);
        setExperience(experience);
        setKnowledge(knowkedge);
    }

    public double getEnergy() {
        return energy;
    }

    public double getExperience() {
        return experience;
    }

    public double getKnowledge() {
        return knowledge;
    }

    public double getProductivity() {
        return (getEnergy() + getExperience() + getKnowledge()) / 3;
    }

    public void setEnergy(final double energy) {
        this.energy = energy;
        if (this.energy < MG.MIN_ENERGY) {
            this.energy = MG.MIN_ENERGY;
        }
        if (this.energy > MG.MAX_ENERGY) {
            this.energy = MG.MAX_ENERGY;
        }
    }

    public void setExperience(final double experience) {
        this.experience = experience;
        if (this.experience < MG.MIN_EXPERIENCE) {
            this.experience = MG.MIN_EXPERIENCE;
        }
        if (this.experience > MG.MAX_EXPERIENCE) {
            this.experience = MG.MAX_EXPERIENCE;
        }
    }

    public void setKnowledge(final double knowledge) {
        this.knowledge = knowledge;
        if (this.knowledge < MG.MIN_KNOWKEDGE) {
            this.knowledge = MG.MIN_KNOWKEDGE;
        }
        if (this.knowledge > MG.MAX_KNOWKEDGE) {
            this.knowledge = MG.MAX_KNOWKEDGE;
        }
    }

}
