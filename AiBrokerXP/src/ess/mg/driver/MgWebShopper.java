package ess.mg.driver;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import ess.common.Price;
import ess.mg.MgMarket;
import ess.mg.actions.PurchaseResult;
import ess.mg.driver.model.Newspaper;
import ess.mg.goods.Goods;
import ess.mg.goods.food.Dairy;
import ess.mg.goods.food.Wine;

public abstract class MgWebShopper extends MgWebWorker {

    public PurchaseResult buyGoods(final Goods goods, MgMarket market) {
        if (goods instanceof Wine) {
            market = MgMarket.LOCAL;
        }
        final PurchaseResult result = new PurchaseResult();
        navigateTo(buildGoodsUrl(goods, market));

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

    public PurchaseResult buyNewspaper(final int index, final Price maxPrice) {
        final PurchaseResult result = new PurchaseResult();
        final Newspaper newspaper = fetchNewspaper(index);
        if (newspaper.getPrice() != null) {
            result.setPrice(Price.ron(newspaper.getPrice()));
            if (result.getPrice().getAmount() <= maxPrice.getAmount()) {
                final WebElement _mr_buy_btn_big = driver.findElement(By.className("_mr_buy_btn_big"));
                // System.out.println(_mr_buy_btn_big.getText());
                _mr_buy_btn_big.click();
                pauseForSubmit();
                result.setSuccessful(true);
            } else {
                result.setMessage("Too expensive");
            }
        } else {
            result.setMessage("Alreadu bought");
        }
        return result;
    }

}
