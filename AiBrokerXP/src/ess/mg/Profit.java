package ess.mg;

import ess.Price;
import ess.mg.optimizers.ActivityResult;

public class Profit {

    public static ActivityResult fight(final MG mg, final double energy) {
        final Price grossReturn = mg.getFightBonus().multiply(energy / MG.MAX_ENERGY);
        return new ActivityResult(grossReturn, energy * (1 - MG.ENERGY_FIGHT_PENALTY));
    }

    public static ActivityResult fight(final MG mg, final double energy, final int times) {
        double totalGrossReturn = 0;
        double remainingEnergy = energy;
        for (int i = 0; i < times; ++i) {
            final ActivityResult result = fight(mg, remainingEnergy);
            totalGrossReturn += result.getGrossReturn().getAmount();
            remainingEnergy = result.getEnergy();
        }
        return new ActivityResult(mg.getFightBonus().create(totalGrossReturn), remainingEnergy);
    }

    public static ActivityResult fightAll(final MG mg, final double energy) {
        return fight(mg, energy, MG.MAX_FIGHTS_PER_DAY);
    }

}
