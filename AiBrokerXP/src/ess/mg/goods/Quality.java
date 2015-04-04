package ess.mg.goods;

public enum Quality {

    LOW (1), NORMAL (2), HIGH (3);

    public final int stars;

    private Quality(final int stars) {
        this.stars = stars;
    }

}
