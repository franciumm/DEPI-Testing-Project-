
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class RemoveItemsIntegration extends Default {

    @BeforeClass
    public void preRequisites() throws InterruptedException {

        globalLogin();
    }


    @Test(priority = 1)
    public void ProductsPageAddAllItems() throws InterruptedException {
        driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("add-to-cart-sauce-labs-bike-light")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("add-to-cart-sauce-labs-bolt-t-shirt")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("add-to-cart-sauce-labs-fleece-jacket")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("add-to-cart-sauce-labs-onesie")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("add-to-cart-test.allthethings()-t-shirt-(red)")).click();
        Thread.sleep(2000);

        String badge = driver.findElement(By.className("shopping_cart_badge")).getText();
        Assert.assertEquals(badge, "6", "Cart does not contain 6 items!");
    }

    @Test(priority = 2)
    public void InventoryPageRemoveItem() throws InterruptedException {
        driver.findElement(By.id("remove-sauce-labs-bolt-t-shirt")).click();
        Thread.sleep(2000);

        String badge = driver.findElement(By.className("shopping_cart_badge")).getText();
        Assert.assertEquals(badge, "5", "Item was not successfully removed from the inventory page!");
    }

    @Test(priority = 3)
    public void DetailsPageRemoveItem() throws InterruptedException {
        driver.findElement(By.xpath("//div[text()='Sauce Labs Backpack']")).click();
        Thread.sleep(2000);

        String detailName = driver.findElement(By.className("inventory_details_name")).getText();
        Assert.assertEquals(detailName, "Sauce Labs Backpack", "Navigated to the wrong product details page!");

        driver.findElement(By.id("remove")).click();
        Thread.sleep(2000);

        String badge = driver.findElement(By.className("shopping_cart_badge")).getText();
        Assert.assertEquals(badge, "4", "Item was not successfully removed from the details page!");
    }

    @Test(priority = 4)
    public void CartsPageNavigation() throws InterruptedException {
        driver.findElement(By.className("shopping_cart_link")).click();
        Thread.sleep(2000);
        Assert.assertTrue(driver.getCurrentUrl().contains("cart.html"), "Did not navigate to Cart page!");
    }

    @Test(priority = 5)
    public void CartsPageRemoveItem() throws InterruptedException {
        driver.findElement(By.id("remove-sauce-labs-bike-light")).click();
        Thread.sleep(2000);
        
        int itemsLeft = driver.findElements(By.className("cart_item")).size();
        Assert.assertEquals(itemsLeft, 3, "Cart list did not update to 3 items!");

        String badge = driver.findElement(By.className("shopping_cart_badge")).getText();
        Assert.assertEquals(badge, "3", "Badge did not update to 3!");
    }
}