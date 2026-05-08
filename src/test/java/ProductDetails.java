
import org.testng.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ProductDetails extends Default {

    @BeforeClass
    public void preRequisites() throws InterruptedException {
        globalLogin();
    }

    @Test(priority = 1)
    public void PageElementsForBackpack() throws InterruptedException {
        driver.findElement(By.xpath("//div[text()='Sauce Labs Backpack']")).click();
        Thread.sleep(2000);

        WebElement img = driver.findElement(By.className("inventory_details_img"));
        Assert.assertTrue(img.isDisplayed(), "Product image is not displayed!");

        WebElement name = driver.findElement(By.className("inventory_details_name"));
        Assert.assertEquals(name.getText(), "Sauce Labs Backpack", "Product name mismatch!");

        WebElement desc = driver.findElement(By.className("inventory_details_desc"));
        Assert.assertFalse(desc.getText().isEmpty(), "Product description is empty!");

        WebElement price = driver.findElement(By.className("inventory_details_price"));
        Assert.assertEquals(price.getText(), "$29.99", "Price mismatch!");

        WebElement addToCartBtn = driver.findElement(By.id("add-to-cart"));
        Assert.assertTrue(addToCartBtn.isDisplayed(), "Add to Cart button is not visible!");
        Assert.assertTrue(addToCartBtn.isEnabled(), "Add to Cart button is disabled!");

        WebElement backBtn = driver.findElement(By.id("back-to-products"));
        Assert.assertTrue(backBtn.isDisplayed(), "Back to products button is not visible!");
    }

    @Test(priority = 2)
    public void AddBackpackToCart() throws InterruptedException {
        driver.findElement(By.id("add-to-cart")).click();
        Thread.sleep(2000);

        String removeText = driver.findElement(By.id("remove")).getText();
        Assert.assertEquals(removeText, "Remove", "Button text did not change to Remove!");

        String badge = driver.findElement(By.className("shopping_cart_badge")).getText();
        Assert.assertEquals(badge, "1", "Cart badge did not update to 1!");
    }

    @Test(priority = 3)
    public void BackToProductsAndNavigateToBikeLight() throws InterruptedException {
        driver.findElement(By.id("back-to-products")).click();
        Thread.sleep(1000);

        Assert.assertTrue(driver.getCurrentUrl().contains("inventory.html"), "Not on inventory page after back navigation!");

        String badge = driver.findElement(By.className("shopping_cart_badge")).getText();
        Assert.assertEquals(badge, "1", "Cart state not preserved after navigation!");

        driver.findElement(By.xpath("//div[text()='Sauce Labs Bike Light']")).click();
        Thread.sleep(2000);

        String name = driver.findElement(By.className("inventory_details_name")).getText();
        Assert.assertEquals(name, "Sauce Labs Bike Light", "Wrong product name on detail page!");

        Assert.assertTrue(driver.findElement(By.className("inventory_details_img")).isDisplayed(),
                "Product image not displayed on Bike Light detail page!");

        Assert.assertTrue(driver.getCurrentUrl().contains("inventory-item.html"),
                "Did not navigate to a product detail page!");
    }

    @Test(priority = 4)
    public void AddBikeLightAndVerifyBadge() throws InterruptedException {
        driver.findElement(By.id("add-to-cart")).click();
        Thread.sleep(2000);

        String badge = driver.findElement(By.className("shopping_cart_badge")).getText();
        Assert.assertEquals(badge, "2", "Cart badge did not increment to 2!");

        String removeText = driver.findElement(By.id("remove")).getText();
        Assert.assertEquals(removeText, "Remove", "Button did not change to Remove for second product!");
    }

    @Test(priority = 5)
    public void CartContainsBothItems() throws InterruptedException {
        driver.findElement(By.className("shopping_cart_link")).click();
        Thread.sleep(2000);

        Assert.assertTrue(driver.getCurrentUrl().contains("cart.html"), "Did not reach Cart page!");

        int itemCount = driver.findElements(By.className("cart_item")).size();
        Assert.assertEquals(itemCount, 2, "Cart does not contain exactly 2 items!");

        driver.findElement(By.id("continue-shopping")).click();
        Thread.sleep(1000);
    }

    @Test(priority = 6)
    public void RemoveBackpackAndVerifyBadge() throws InterruptedException {
        driver.findElement(By.xpath("//div[text()='Sauce Labs Backpack']")).click();
        Thread.sleep(2000);

        String btnText = driver.findElement(By.id("remove")).getText();
        Assert.assertEquals(btnText, "Remove", "Button does not show Remove when opening the item!");

        driver.findElement(By.id("remove")).click();
        Thread.sleep(1000);

        String addText = driver.findElement(By.id("add-to-cart")).getText();
        Assert.assertEquals(addText, "Add to cart", "Button did not revert to Add to cart!");

        String badge = driver.findElement(By.className("shopping_cart_badge")).getText();
        Assert.assertEquals(badge, "1", "Cart badge did not decrement to 1!");
    }

    @Test(priority = 7)
    public void NavigateToProductViaImage() throws InterruptedException {
        driver.findElement(By.id("back-to-products")).click();
        Thread.sleep(1000);

        driver.findElement(By.xpath("//img[@alt='Sauce Labs Bike Light']")).click();
        Thread.sleep(2000);

        String name = driver.findElement(By.className("inventory_details_name")).getText();
        Assert.assertEquals(name, "Sauce Labs Bike Light", "Wrong product after image click!");

        Assert.assertTrue(driver.findElement(By.className("inventory_details_img")).isDisplayed(),
                "Product image not displayed after image click navigation!");
    }
}