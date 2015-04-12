package ess.mg.driver.dto;

import java.util.ArrayList;

@SuppressWarnings("serial")
public class Transactions extends ArrayList<Transaction> {

    public int getFightCount() {
        int count = 0;
        for (final Transaction t : this) {
            if (t instanceof TFight) {
                count += 1;
            }
        }
        return count;
    }

    public int getFoodCount() {
        int count = 0;
        for (final Transaction t : this) {
            if (t instanceof TBuyFood) {
                count += ((TBuyFood) t).getAmount();
            }
        }
        return count;
    }

    public int getMilkCount() {
        int count = 0;
        for (final Transaction t : this) {
            if (t instanceof TBuyMilk) {
                count += ((TBuyMilk) t).getAmount();
            }
        }
        return count;
    }

    public int getNewspaperCount() {
        int count = 0;
        for (final Transaction t : this) {
            if (t instanceof TBuyNewspaper) {
                count += ((TBuyNewspaper) t).getAmount();
            }
        }
        return count;
    }

    public int getWineCount() {
        int count = 0;
        for (final Transaction t : this) {
            if (t instanceof TBuyWine) {
                count += ((TBuyWine) t).getAmount();
            }
        }
        return count;
    }

    public int getWorkCount() {
        int count = 0;
        for (final Transaction t : this) {
            if (t instanceof TWork) {
                count += 1;
            }
        }
        return count;
    }

}
