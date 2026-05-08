package BigMarven;

import org.testng.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class CheckoutOverview extends Default {

    @BeforeClass
    public void preRequisites() throws InterruptedException {
        globalLogin();
        // Add two items and go through customer info to reach overview
        driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
        driver.findElement(By.id("add-to-cart-sauce-labs-bike-light")).click();
        driver.findElement(By.className("shopping_cart_link")).click();
        driver.findElement(By.id("checkout")).click();
        driver.findElement(By.id("first-name")).sendKeys("Abdo");
        driver.findElement(By.id("last-name")).sendKeys("Ali");
        driver.findElement(By.id("postal-code")).sendKeys("14526");
        driver.findElement(By.id("continue")).click();
        Thread.sleep(2000);
    }

    @Test(priority = 1)
    public void testOrderSummaryItems() {
        // Check item names and prices
        WebElement item1 = driver.findElement(By.xpath("//div[text()='Sauce Labs Backpack']"));
        WebElement item2 = driver.findElement(By.xpath("//div[text()='Sauce Labs Bike Light']"));
        Assert.assertTrue(item1.isDisplayed() && item2.isDisplayed(), "Items not listed on overview!");

        // Verify prices (simple existence check, price validation in totals test)
        String price1 = driver.findElement(By.xpath("//div[text()='29.99']")).getText();
        String price2 = driver.findElement(By.xpath("//div[text()='9.99']")).getText();
        Assert.assertEquals(price1, "29.99", "Backpack price mismatch");
        Assert.assertEquals(price2, "9.99", "Bike Light price mismatch");
    }

    @Test(priority = 2)
    public void testPaymentInfoDisplayed() {
        WebElement paymentLabel = driver.findElement(By.xpath("//div[text()='Payment Information']"));
        Assert.assertTrue(paymentLabel.isDisplayed(), "Payment info label missing");
        String sauceCard = driver.findElement(By.xpath("//div[contains(text(),'SauceCard')]")).getText();
        Assert.assertTrue(sauceCard.contains("SauceCard #"), "SauceCard number not shown");
    }

    @Test(priority = 3)
    public void testShippingInfoDisplayed() {
        WebElement shippingLabel = driver.findElement(By.xpath("//div[text()='Shipping Information']"));
        Assert.assertTrue(shippingLabel.isDisplayed(), "Shipping info label missing");
        String shippingMethod = driver.findElement(By.xpath("//div[contains(text(),'Free Pony Express')]")).getText();
        Assert.assertTrue(shippingMethod.contains("Free Pony Express"), "Shipping method not shown");
    }

    @Test(priority = 4)
    public void testPriceTotalsCorrect() {
        // Get numeric values (remove '$')
        String itemTotalStr = driver.findElement(By.className("summary_subtotal_label")).getText();
        String taxStr = driver.findElement(By.className("summary_tax_label")).getText();
        String totalStr = driver.findElement(By.className("summary_total_label")).getText();

        double itemTotal = Double.parseDouble(itemTotalStr.replace("Item total: $", ""));
        double tax = Double.parseDouble(taxStr.replace("Tax: $", ""));
        double total = Double.parseDouble(totalStr.replace("Total: $", ""));

        Assert.assertEquals(itemTotal, 29.99 + 9.99, 0.01, "Item total incorrect");
        Assert.assertEquals(total, itemTotal + tax, 0.01, "Total not equal to Item total + Tax");
    }

    @Test(priority = 5)
    public void testCancelButtonPreservesCart() throws InterruptedException {
        // Click Cancel and verify back to inventory with items still in cart
        driver.findElement(By.id("cancel")).click();
        Thread.sleep(1000);
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory.html"), "Not back on inventory after cancel");
        String badge = driver.findElement(By.className("shopping_cart_badge")).getText();
        Assert.assertEquals(badge, "2", "Cart items not preserved after cancel!");
        // Navigate back to overview for next tests
        driver.findElement(By.className("shopping_cart_link")).click();
        driver.findElement(By.id("checkout")).click();
        driver.findElement(By.id("first-name")).sendKeys("Abdo");
        driver.findElement(By.id("last-name")).sendKeys("Ali");
        driver.findElement(By.id("postal-code")).sendKeys("14526");
        driver.findElement(By.id("continue")).click();
        Thread.sleep(1500);
    }

    @Test(priority = 6)
    public void testFinishButtonEnabled() {
        WebElement finishBtn = driver.findElement(By.id("finish"));
        Assert.assertTrue(finishBtn.isDisplayed() && finishBtn.isEnabled(), "Finish button not enabled");
    }

    @Test(priority = 7)
    public void testFinishRedirectsToComplete() throws InterruptedException {
        driver.findElement(By.id("finish")).click();
        Thread.sleep(2000);
        Assert.assertTrue(driver.getCurrentUrl().contains("checkout-complete.html"),
                "Not redirected to completion page after finish");
    }

    @Test(priority = 8)
    public void testCompletionMessageDisplayed() {
        WebElement thankYouHeader = driver.findElement(By.className("complete-header"));
        Assert.assertEquals(thankYouHeader.getText(), "Thank you for your order!",
                "Completion message incorrect");
    }

    @Test(priority = 9)
    public void testBackHomeClearsCart() throws InterruptedException {
        driver.findElement(By.id("back-to-products")).click();
        Thread.sleep(1000);
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory.html"), "Not on inventory after back home");
        // Cart badge should not exist (cart cleared)
        boolean badgePresent = driver.findElements(By.className("shopping_cart_badge")).size() > 0;
        Assert.assertFalse(badgePresent, "Cart badge still present after order completion!");
    }

    // Integration: performance_glitch_user can complete overview
    @Test(priority = 10)
    public void testPerfGlitchUserCompletion() throws InterruptedException {
        // Logout current user, login as performance_glitch_user
        driver.findElement(By.id("react-burger-menu-btn")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("logout_sidebar_link")).click();
        Thread.sleep(2000);

        driver.findElement(By.id("user-name")).sendKeys("performance_glitch_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        Thread.sleep(3000); // wait for slow inventory

        // Add item and go through checkout
        driver.findElement(By.id("add-to-cart-sauce-labs-onesie")).click();
        driver.findElement(By.className("shopping_cart_link")).click();
        driver.findElement(By.id("checkout")).click();
        driver.findElement(By.id("first-name")).sendKeys("Abdo");
        driver.findElement(By.id("last-name")).sendKeys("Ali");
        driver.findElement(By.id("postal-code")).sendKeys("14526");
        driver.findElement(By.id("continue")).click();
        Thread.sleep(3000); // wait for overview page

        Assert.assertTrue(driver.getCurrentUrl().contains("checkout-step-two.html"),
                "Performance glitch user did not reach overview!");
        driver.findElement(By.id("finish")).click();
        Thread.sleep(3000);
        Assert.assertTrue(driver.getCurrentUrl().contains("checkout-complete.html"),
                "Performance glitch user could not complete order!");
    }
}