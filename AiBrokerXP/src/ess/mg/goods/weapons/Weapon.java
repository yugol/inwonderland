package ess.mg.goods.weapons;

import ess.mg.goods.Goods;
import ess.mg.goods.Gradable;
import ess.mg.goods.Quality;

public abstract class Weapon extends Goods implements Gradable {

    protected Weapon(final String name, final Quality quality) {
        super("weapons", name, quality);
    }

    @Override
    public Quality getQuality() {
        return quality;
    }

}
