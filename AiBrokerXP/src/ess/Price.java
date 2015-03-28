package ess;

public class Price {

    public static Price euro(final double amount) {
        return new Price(amount, Currency.EURO);
    }

    public static Price gold(final double amount) {
        return new Price(amount, Currency.GOLD);
    }

    public static Price ron(final double amount) {
        return new Price(amount, Currency.RON);
    }

    private final double   amount;
    private final Currency currency;

    public Price(final double amount, final Currency currency) {
        super();
        this.amount = amount;
        this.currency = currency;
    }

    public Price add(final Price augend) {
        if (currency == augend.currency) { return new Price(amount + augend.amount, currency); }
        throw new IllegalArgumentException("incompatible currencies " + currency + " and " + augend.currency);
    }

    public double getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public Price multiply(final double multiplicand) {
        return new Price(amount * multiplicand, currency);
    }

}
