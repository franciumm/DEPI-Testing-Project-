import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class E2E extends Default {

    @Test
    public void ENDTOENDTEST() throws InterruptedException {

        // STEP 1: Login
        globalLogin();
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory"), "Failed to land on inventory page!");

        // STEP 2: Add First Item to Cart
        driver.findElements(By.className("btn_inventory")).getFirst().click();
        Thread.sleep(1000);

        Assert.assertFalse(driver.findElements(By.className("shopping_cart_badge")).isEmpty(), "Cart badge did not appear!");

        //Step 3: Watch Product details

        driver.findElements(By.className("inventory_item_img")).getFirst().click();
        Thread.sleep(1000);
        // STEP 4: Navigate to Cart
        driver.findElement(By.className("shopping_cart_link")).click();
        Thread.sleep(1000);

        Assert.assertTrue(driver.getCurrentUrl().contains("cart"), "Did not navigate to Cart page!");
        Assert.assertFalse(driver.findElements(By.className("inventory_item_name")).isEmpty(), "Cart is empty!");

        // STEP 5: Start Checkout
        driver.findElement(By.id("checkout")).click();
        Thread.sleep(1000);
        Assert.assertTrue(driver.getCurrentUrl().contains("checkout-step-one"), "Did not navigate to Checkout Step 1!");

        // STEP 6: Fill Checkout Information
        driver.findElement(By.id("first-name")).sendKeys("Menna");
        driver.findElement(By.id("last-name")).sendKeys("Magdy");
        driver.findElement(By.id("postal-code")).sendKeys("11511");

        driver.findElement(By.id("continue")).click();
        Thread.sleep(1000);
        Assert.assertTrue(driver.getCurrentUrl().contains("checkout-step-two"), "Did not navigate to Checkout Step 2!");

        // STEP 7: Confirm and Finish Order
        driver.findElement(By.id("finish")).click();
        Thread.sleep(1000);
        Assert.assertTrue(driver.getCurrentUrl().contains("checkout-complete"), "Did not navigate to Checkout Complete page!");

        // STEP 8: Verify Success Message
        WebElement successMessage = driver.findElement(By.className("complete-header"));
        Assert.assertTrue(successMessage.isDisplayed(), "Success message is not visible!");
        Assert.assertEquals(successMessage.getText(), "Thank you for your order!", "Incorrect success message text!");

        // STEP 9: Return to Products
        driver.findElement(By.id("back-to-products")).click();
        Thread.sleep(1000);
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory"), "Did not successfully return to inventory!");

    }
}