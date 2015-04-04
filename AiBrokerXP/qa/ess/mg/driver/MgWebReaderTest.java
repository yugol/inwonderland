package ess.mg.driver;

import ess.mg.driver.MgWebReader.Newspaper;

public class MgWebReaderTest {

    public static void main(final String... args) {
        final MgWebReader reader = new MgWebDriver();
        reader.login();
        final Newspaper paper = reader.fetchNewspaper(15);
        System.out.println(paper);
        // reader.close();
    }
}
