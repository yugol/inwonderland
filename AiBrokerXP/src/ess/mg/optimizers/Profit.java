package ess.mg.optimizers;

import ess.common.Price;
import ess.mg.MgContext;

public class Profit {

    public static ActivityResult fight(final MgContext mg, final double energy) {
        final Price grossReturn = mg.getFightBonus().multiply(energy / MgContext.MAX_ENERGY);
        return new ActivityResult(grossReturn, energy * (1 - MgContext.ENERGY_FIGHT_PENALTY));
    }

    public static ActivityResult fight(final MgContext mg, final double energy, final int times) {
        double totalGrossReturn = 0;
        double remainingEnergy = energy;
        for (int i = 0; i < times; ++i) {
            final ActivityResult result = fight(mg, remainingEnergy);
            totalGrossReturn += result.getGrossReturn().getAmount();
            remainingEnergy = result.getEnergy();
        }
        return new ActivityResult(mg.getFightBonus().create(totalGrossReturn), remainingEnergy);
    }

    public static ActivityResult fightAll(final MgContext mg, final double energy) {
        return fight(mg, energy, MgContext.MAX_FIGHTS_PER_DAY);
    }

}
