package ess.mg;

import ess.Price;
import ess.mg.actions.ActivityResult;

public class Profit {

    public static ActivityResult fight(final double energy) {
        final Price grossReturn = MG.actualFightBonus().multiply(energy / MG.MAX_ENERGY);
        return new ActivityResult(grossReturn, energy * (1 - MG.ENERGY_FIGHT_PENALTY));
    }

    public static ActivityResult fight(final double energy, final int times) {
        double totalGrossReturn = 0;
        double remainingEnergy = energy;
        for (int i = 0; i < times; ++i) {
            final ActivityResult result = fight(remainingEnergy);
            totalGrossReturn += result.getGrossReturn().getAmount();
            remainingEnergy = result.getEnergy();
        }
        return new ActivityResult(MG.FIGHT_BONUS.create(totalGrossReturn), remainingEnergy);
    }

    public static ActivityResult fightAll(final double energy) {
        return fight(energy, MG.MAX_FIGHTS_PER_DAY);
    }

}
