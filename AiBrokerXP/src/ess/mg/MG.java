package ess.mg;

import aibroker.util.Moment;
import ess.Price;

public class MG {

    public static Price actualWage(final Price wage) {
        final int delta = Moment.getDaysBetween(FIRST_WORK_DAY, Moment.getNow());
        if (delta % 3 == 2) {
            final Price workBonus = DIGIPASS ? WORK_BONUS.multiply(DIGIPASS_BONUS) : WORK_BONUS;
            return wage.add(workBonus);
        }
        return wage;
    }

    // occasionally updated
    public static final boolean DIGIPASS                   = true;
    public static final double  DIGIPASS_BONUS             = 1.25;
    public static final Price   FIGHT_BONUS                = Price.ron(7);
    public static final Price   WORK_BONUS                 = Price.ron(350);
    public static final Moment  FIRST_WORK_DAY             = Moment.fromIso("2015-03-31");

    // seldom updated
    public static final double  MIN_ENERGY                 = 1.0;
    public static final double  MAX_ENERGY                 = 100.0;
    public static final double  MIN_EXPERIENCE             = 1.0;
    public static final double  MAX_EXPERIENCE             = 1900.0;
    public static final double  MIN_KNOWKEDGE              = 1.0;
    public static final double  MAX_KNOWKEDGE              = 1000.0;

    public static final double  MAX_FIGHTS_PER_DAY         = 10;
    public static final double  ENERGY_HOURLY_PENALTY      = 0.05;
    public static final double  ENERGY_FIGHT_PENALTY       = 0.1;
    public static final double  ENERGY_WORK_PENALTY        = 0.5;
    public static final double  PRODUCTIVITY_DAILY_PENALTY = 0.003;
    public static final double  KNOWKEDGE_DAILY_PENALTY    = 0.003;

    public static final double  WORK_INCOME_TAX            = 0.35;
    public static final double  EXCHANGE_FEE               = 0.03;

}
