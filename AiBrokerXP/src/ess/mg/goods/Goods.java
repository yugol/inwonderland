package ess.mg.goods;

public abstract class Goods {

    private final String    category;
    private final String    name;
    protected final Quality quality;

    protected Goods(final String category, final String name) {
        this(category, name, null);
    }

    protected Goods(final String category, final String name, final Quality quality) {
        this.category = category;
        this.name = name;
        this.quality = quality;
    }

    public String getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

}
