package ess.mg.goods.food;

public class Wine extends Food {

    public Wine() {
        super("wine", null);
    }

    @Override
    public double getEnergy() {
        return 25;
    }

}
