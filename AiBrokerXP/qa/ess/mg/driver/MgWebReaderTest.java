package ess.mg.driver;

import ess.mg.driver.dto.Transactions;

public class MgWebReaderTest {

    public static void main(final String... args) {
        final MgWebReader reader = new MgWebDriver();
        reader.login();
        final Transactions ts = reader.fetchTransactions(40);
        System.out.println(ts.size());
    }

}
