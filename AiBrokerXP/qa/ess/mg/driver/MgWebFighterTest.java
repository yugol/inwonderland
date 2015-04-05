package ess.mg.driver;

import ess.mg.actions.ActionResult;

public class MgWebFighterTest {

    public static void main(final String... args) {
        final MgWebFighter fighter = new MgWebDriver();
        fighter.login();
        final ActionResult fighting = fighter.referralFight();
        System.out.println("Fighting: " + fighting.isSuccessful());
        // fighter.close();
    }

}