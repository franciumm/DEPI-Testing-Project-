import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class E2E extends Default {

    @Test
    public void ENDTOENDTEST() throws InterruptedException {
        globalLogin();
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory"), "Failed to land on inventory page!");

        // STEP 1: Add First Item to Cart and Store Name to Validate Transfer
        WebElement firstItem = driver.findElements(By.className("inventory_item")).get(0);
        String expectedProductName = firstItem.findElement(By.className("inventory_item_name")).getText();
        firstItem.findElement(By.className("btn_inventory")).click();
        Thread.sleep(1000);

        Assert.assertFalse(driver.findElements(By.className("shopping_cart_badge")).isEmpty(), "Cart badge did not appear!");

        // STEP 2: Navigate to Cart & Verify Correct Item
        driver.findElement(By.className("shopping_cart_link")).click();
        Thread.sleep(1000);

        Assert.assertTrue(driver.getCurrentUrl().contains("cart"), "Did not navigate to Cart page!");
        String actualProductName = driver.findElement(By.className("inventory_item_name")).getText();
        Assert.assertEquals(actualProductName, expectedProductName, "Bug Detected: Wrong item added to cart!");

        // STEP 3: Start Checkout
        driver.findElement(By.id("checkout")).click();
        Thread.sleep(1000);

        // STEP 4: Fill Checkout Information
        driver.findElement(By.id("first-name")).sendKeys("Menna");
        driver.findElement(By.id("last-name")).sendKeys("Magdy");
        driver.findElement(By.id("postal-code")).sendKeys("11511");
        driver.findElement(By.id("continue")).click();
        Thread.sleep(1000);

        // STEP 5: VERIFY CRITICAL APP MATH (Subtotal + Tax = Total)
        String itemTotalStr = driver.findElement(By.className("summary_subtotal_label")).getText().replaceAll("[^0-9.]", "");
        String taxStr = driver.findElement(By.className("summary_tax_label")).getText().replaceAll("[^0-9.]", "");
        String totalStr = driver.findElement(By.className("summary_total_label")).getText().replaceAll("[^0-9.]", "");

        double itemTotal = Double.parseDouble(itemTotalStr);
        double tax = Double.parseDouble(taxStr);
        double total = Double.parseDouble(totalStr);

        Assert.assertEquals(total, itemTotal + tax, 0.01, "Bug Detected: Checkout Total Calculation is mathematically incorrect!");

        // STEP 6: Confirm and Finish Order
        driver.findElement(By.id("finish")).click();
        Thread.sleep(1000);
        WebElement successMessage = driver.findElement(By.className("complete-header"));
        Assert.assertEquals(successMessage.getText(), "Thank you for your order!", "Incorrect success message text!");
    }
}