package ess.mg;

import aibroker.util.Moment;
import ess.Price;
import ess.mg.driver.model.Shares;
import ess.mg.driver.model.Transactions;
import ess.mg.goods.Quality;

public class MgContext {

    public static Price netReturn(final Price grossReturn) {
        return grossReturn.multiply(1 - WORK_INCOME_TAX);
    }

    // occasionally updated
    public static final Moment FIRST_WORK_DAY             = Moment.fromIso("2015-04-11");

    // seldom updated
    public static final double MIN_ENERGY                 = 1.0;
    public static final double MAX_ENERGY                 = 100.0;
    public static final double MIN_EXPERIENCE             = 1.0;
    public static final double MAX_EXPERIENCE             = 1900.0;
    public static final double MIN_KNOWKEDGE              = 1.0;
    public static final double MAX_KNOWKEDGE              = 1000.0;
    public static final int    MAX_FIGHTS_PER_DAY         = 10;
    public static final int    MAX_WORK_PER_DAY           = 1;

    public static final double ENERGY_HOURLY_PENALTY      = 0.05;
    public static final double ENERGY_FIGHT_PENALTY       = 0.1;
    public static final double ENERGY_WORK_PENALTY        = 0.5;
    public static final double PRODUCTIVITY_DAILY_PENALTY = 0.003;
    public static final double KNOWKEDGE_DAILY_PENALTY    = 0.003;
    public static final double WORK_INCOME_TAX            = 0.35;
    public static final double EXCHANGE_FEE               = 0.03;

    public static final int    MAX_NEWSPAPRES_PER_DAY     = 10;

    // government data
    private Price              fightBonus                 = Price.ron(6);
    private Price              workBonus                  = Price.ron(300);
    // player data
    private double             energy                     = MIN_ENERGY;
    private double             experience                 = MIN_EXPERIENCE;
    private double             knowledge                  = MIN_KNOWKEDGE;
    private double             euroAmount                 = 0;
    private double             goldAmount                 = 0;
    private double             ronAmount                  = 0;
    // market data
    private Double             wage;
    private Double             euroGoldExchangeRate;
    private Double             goldRonExchangeRate;
    private Shares             shares;
    // player history
    private final Transactions transactions               = new Transactions();
    // server time
    private Moment             serverTime;
    // activity data
    private int                fightCount                 = 0;
    private int                workCount                  = 0;
    private int                fightRemainingMinutes      = 0;
    // inventory
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

    public double getEuroAmount() {
        return euroAmount;
    }

    public Double getEuroGoldExchangeRate() {
        return euroGoldExchangeRate;
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

    public int getFightRemainingMinutes() {
        return fightRemainingMinutes;
    }

    public double getGoldAmount() {
        return goldAmount;
    }

    public Double getGoldRonExchangeRate() {
        return goldRonExchangeRate;
    }

    public double getKnowledge() {
        return knowledge;
    }

    public double getProductivity() {
        return (getEnergy() + getExperience() + getKnowledge()) / 3;
    }

    public double getRonAmount() {
        return ronAmount;
    }

    public Moment getServerTime() {
        return serverTime;
    }

    public Shares getShares() {
        return shares;
    }

    public Transactions getTransactions() {
        return transactions;
    }

    public Double getWage() {
        return wage;
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
        if (this.energy < MgContext.MIN_ENERGY) {
            this.energy = MgContext.MIN_ENERGY;
        }
        if (this.energy > MgContext.MAX_ENERGY) {
            this.energy = MgContext.MAX_ENERGY;
        }
    }

    public void setEuroAmount(final double euroAmount) {
        this.euroAmount = euroAmount;
    }

    public void setEuroGoldExchangeRate(final Double euroGoldExchangeRate) {
        this.euroGoldExchangeRate = euroGoldExchangeRate;
    }

    public void setExperience(final double experience) {
        this.experience = experience;
        if (this.experience < MgContext.MIN_EXPERIENCE) {
            this.experience = MgContext.MIN_EXPERIENCE;
        }
        if (this.experience > MgContext.MAX_EXPERIENCE) {
            this.experience = MgContext.MAX_EXPERIENCE;
        }
    }

    public void setFightBonus(final Price fightBonus) {
        this.fightBonus = fightBonus;
    }

    public void setFightCount(final int fightCount) {
        this.fightCount = fightCount;
    }

    public void setFightRemainingMinutes(final int minutes) {
        fightRemainingMinutes = minutes;
    }

    public void setGoldAmount(final double goldAmount) {
        this.goldAmount = goldAmount;
    }

    public void setGoldRonExchangeRate(final Double goldRonExchangeRate) {
        this.goldRonExchangeRate = goldRonExchangeRate;
    }

    public void setKnowledge(final double knowledge) {
        this.knowledge = knowledge;
        if (this.knowledge < MgContext.MIN_KNOWKEDGE) {
            this.knowledge = MgContext.MIN_KNOWKEDGE;
        }
        if (this.knowledge > MgContext.MAX_KNOWKEDGE) {
            this.knowledge = MgContext.MAX_KNOWKEDGE;
        }
    }

    public void setRonAmount(final double ronAmount) {
        this.ronAmount = ronAmount;
    }

    public void setServerTime(final Moment time) {
        serverTime = time;
    }

    public void setShares(final Shares shares) {
        this.shares = shares;
    }

    public void setWage(final Double wage) {
        this.wage = wage;
    }

    public void setWorkBonus(final Price workBonus) {
        this.workBonus = workBonus;
    }

    public void setWorkCount(final int workCount) {
        this.workCount = workCount;
    }

}
