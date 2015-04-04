package ess.mg;

import aibroker.util.Moment;
import ess.Price;
import ess.mg.goods.Quality;

public class MG {

    public static Price netReturn(final Price grossReturn) {
        return grossReturn.multiply(1 - WORK_INCOME_TAX);
    }

    // occasionally updated
    public static final Moment FIRST_WORK_DAY             = Moment.fromIso("2015-03-31");

    // seldom updated
    public static final double MIN_ENERGY                 = 1.0;
    public static final double MAX_ENERGY                 = 100.0;
    public static final double MIN_EXPERIENCE             = 1.0;
    public static final double MAX_EXPERIENCE             = 1900.0;
    public static final double MIN_KNOWKEDGE              = 1.0;
    public static final double MAX_KNOWKEDGE              = 1000.0;
    public static final int    MAX_FIGHTS_PER_DAY         = 10;

    public static final double ENERGY_HOURLY_PENALTY      = 0.05;
    public static final double ENERGY_FIGHT_PENALTY       = 0.1;
    public static final double ENERGY_WORK_PENALTY        = 0.5;
    public static final double PRODUCTIVITY_DAILY_PENALTY = 0.003;
    public static final double KNOWKEDGE_DAILY_PENALTY    = 0.003;
    public static final double WORK_INCOME_TAX            = 0.35;
    public static final double EXCHANGE_FEE               = 0.03;

    private Moment             serverTime;
    private int                fightCount                 = 0;
    private int                workCount                  = 0;
    private Price              fightBonus                 = Price.ron(6);
    private Price              workBonus                  = Price.ron(300);
    private double             energy                     = MIN_ENERGY;
    private double             experience                 = MIN_EXPERIENCE;
    private double             knowledge                  = MIN_KNOWKEDGE;
    private final int[]        cuisine                    = { 0, 0, 0 };
    private final int[]        coffe                      = { 0, 0, 0 };
    private int                cheese                     = 0;

    public Price actualWage(final Price wage) {
        final int delta = Moment.getDaysBetween(FIRST_WORK_DAY, Moment.getNow());
        if (delta % 3 == 2) { return wage.add(workBonus); }
        return wage;
    }

    public int getCheese() {
        return cheese;
    }

    public int getCoffe(final Quality quality) {
        return coffe[quality.stars - 1];
    }

    public int getCuisine(final Quality quality) {
        return cuisine[quality.stars - 1];
    }

    public double getEnergy() {
        return energy;
    }

    public double getExperience() {
        return experience;
    }

    public Price getFightBonus() {
        return fightBonus;
    }

    public int getFightCount() {
        return fightCount;
    }

    public double getKnowledge() {
        return knowledge;
    }

    public double getProductivity() {
        return (getEnergy() + getExperience() + getKnowledge()) / 3;
    }

    public Moment getServerTime() {
        return serverTime;
    }

    public Price getWorkBonus() {
        return workBonus;
    }

    public int getWorkCount() {
        return workCount;
    }

    public void setCheese(final int cheese) {
        this.cheese = cheese;
    }

    public void setCoffe(final Quality quality, final int quantity) {
        coffe[quality.stars - 1] = quantity;
    }

    public void setCuisine(final Quality quality, final int quantity) {
        cuisine[quality.stars - 1] = quantity;
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

    public void setFightBonus(final Price fightBonus) {
        this.fightBonus = fightBonus;
    }

    public void setFightCount(final int fightCount) {
        this.fightCount = fightCount;
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

    public void setServerTime(final Moment time) {
        serverTime = time;
    }

    public void setWorkBonus(final Price workBonus) {
        this.workBonus = workBonus;
    }

    public void setWorkCount(final int workCount) {
        this.workCount = workCount;
    }

}
