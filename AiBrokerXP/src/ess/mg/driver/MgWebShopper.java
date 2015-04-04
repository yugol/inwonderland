package ess.mg.driver;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import ess.mg.actions.PurchaseResult;
import ess.mg.goods.Goods;
import ess.mg.goods.food.Dairy;
import ess.mg.goods.food.Wine;
import ess.mg.markets.MgMarket;

public abstract class MgWebShopper extends MgWebWorker {

    public PurchaseResult buyGoods(final Goods goods, MgMarket market) {
        if (goods instanceof Wine) {
            market = MgMarket.LOCAL;
        }
        final PurchaseResult result = new PurchaseResult();

        driver.navigate().to(buildGoodsUrl(goods, market));
        pauseForRead();

        result.setPrice(readGoodsPrice(goods, market));

        final WebElement cant = driver.findElement(By.className("cant"));
        cant.sendKeys("\b1");
        final WebElement nd_submit_inv = driver.findElement(By.className("nd_submit_inv"));
        // System.out.println(nd_submit_inv.getAttribute("value"));
        nd_submit_inv.click();
        pauseForSubmit();

        if (goods instanceof Dairy || goods instanceof Wine) {
            try {
                final WebElement nd_mess_error = driver.findElement(By.className("nd_mess_error"));
                result.setSuccessful(false);
                result.setMessage(nd_mess_error.getText());
            } catch (final NoSuchElementException e) {
                result.setSuccessful(true);
            }
        } else {
            result.setSuccessful(true);
        }

        return result;
    }

}
