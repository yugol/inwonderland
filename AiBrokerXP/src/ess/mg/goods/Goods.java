package ess.mg.goods;

public abstract class Goods {

    private final String category;
    private final String name;
    private final int    stars;

    public Goods(final String category, final String name, final int stars) {
        this.category = category;
        this.name = name;
        this.stars = stars;
    }

    public String getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public int getStars() {
        return stars;
    }

}
