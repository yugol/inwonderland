package ess.mg.driver;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import ess.common.Price;
import ess.mg.MgContext;
import ess.mg.actions.WorkResult;

public abstract class MgWebWorker extends MgWebFighter {

    public WorkResult work(final MgContext context, final Price minimumWage) {
        final WorkResult result = new WorkResult();
        fetchWage(context);
        result.setWage(Price.ron(context.getWorkCount()));
        try {
            final WebElement nd_mess_error = driver.findElement(By.className("nd_mess_error"));
            result.setMessage(nd_mess_error.getText());
        } catch (final NoSuchElementException e) {
            final WebElement nd_work_submit = driver.findElement(By.className("nd_work_submit"));
            nd_work_submit.click();
            pauseForSubmit();
            result.setSuccessful(true);
        }
        return result;
    }

}
