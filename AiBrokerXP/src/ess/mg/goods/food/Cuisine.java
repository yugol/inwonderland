package ess.mg.goods.food;

import ess.mg.goods.Gradable;
import ess.mg.goods.Quality;

public class Cuisine extends Food implements Gradable {

    public Cuisine(final Quality quality) {
        super("cuisine", quality);
    }

    @Override
    public double getEnergy() {
        switch (getQuality()) {
            case LOW:
                return 1;
            case NORMAL:
                return 3;
            case HIGH:
                return 5;
        }
        return 0;
    }

    @Override
    public Quality getQuality() {
        return quality;
    }

}
