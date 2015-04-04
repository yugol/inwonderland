package ess.mg.goods.food;

import ess.mg.goods.Goods;
import ess.mg.goods.Quality;

public abstract class Food extends Goods {

    protected Food(final String name, final Quality quality) {
        super("food", name, quality);
    }

    public abstract double getEnergy();

}
