package ess.mg.driver.dto;

import aibroker.util.Moment;
import ess.common.Currency;
import ess.common.Price;

public abstract class Transaction {

    public static Transaction newInstance(final boolean income, final String when, final String description) {
        if (income) {
            if (description.indexOf("You earned a wage") >= 0) {
                return new TWork(income, Moment.fromIso(when), parseGain(description));
            } else if (description.indexOf("For winning a fight you received a bonus") >= 0) {
                return new TFight(income, Moment.fromIso(when), parseGain(description));
            } else {
            }
        } else {
            if (description.indexOf("Newspapers") >= 0) {
                final Object[] data = parseGoodsPurchase(description);
                return new TBuyNewspaper(income, Moment.fromIso(when), (Price) data[0], (double) data[1]);
            } else if (description.indexOf("Milk") >= 0) {
                final Object[] data = parseGoodsPurchase(description);
                return new TBuyMilk(income, Moment.fromIso(when), (Price) data[0], (double) data[1]);
            } else if (description.indexOf("Wine") >= 0) {
                final Object[] data = parseGoodsPurchase(description);
                return new TBuyWine(income, Moment.fromIso(when), (Price) data[0], (double) data[1]);
            } else if (description.indexOf("Food") >= 0) {
                final Object[] data = parseGoodsPurchase(description);
                return new TBuyFood(income, Moment.fromIso(when), (Price) data[0], (double) data[1]);
            } else {
            }
        }
        return null;
    }

    private static Price parseGain(final String description) {
        final String[] chunks = description.trim().split(" ");
        return new Price(Double.parseDouble(chunks[chunks.length - 2]), Currency.valueOf(chunks[chunks.length - 1]));
    }

    private static Object[] parseGoodsPurchase(final String description) {
        final Object[] data = new Object[2];
        final String[] chunks = description.trim().split(" ");
        data[0] = new Price(Double.parseDouble(chunks[chunks.length - 3]), Currency.valueOf(chunks[chunks.length - 2]));
        data[1] = Double.parseDouble(chunks[2]);
        return data;
    }

    private final boolean income;
    private final Moment  moment;

    protected Transaction(final boolean income, final Moment moment) {
        this.income = income;
        this.moment = moment;
    }

    public Moment getMoment() {
        return moment;
    }

    public boolean isIncome() {
        return income;
    }

}
