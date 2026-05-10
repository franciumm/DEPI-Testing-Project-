import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class CustomerInfo extends Default {

    @BeforeClass
    public void preRequisites() throws InterruptedException {
        globalLogin();
    }

    public void navigateToStepOne() throws InterruptedException {
        driver.get("https://www.saucedemo.com/cart.html");
        if (driver.findElements(By.className("shopping_cart_badge")).isEmpty()) {
            driver.get("https://www.saucedemo.com/inventory.html");
            driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
            driver.get("https://www.saucedemo.com/cart.html");
        }
        driver.findElement(By.id("checkout")).click();
        Thread.sleep(1000);
    }

    @Test(priority = 1)
    public void VerifyOpeningCheckoutPage() throws InterruptedException {
        navigateToStepOne();
        Assert.assertTrue(driver.getCurrentUrl().contains("checkout-step-one.html"), "Checkout page failed to open!");
    }

    @Test(priority = 2)
    public void VerifyStandardInputAccepted() throws InterruptedException {
        navigateToStepOne();
        driver.findElement(By.id("first-name")).sendKeys("Abdo");
        driver.findElement(By.id("last-name")).sendKeys("Ali");
        driver.findElement(By.id("postal-code")).sendKeys("14526");
        driver.findElement(By.id("continue")).click();

        Assert.assertTrue(driver.getCurrentUrl().contains("checkout-step-two.html"), "Standard valid data was rejected!");
    }

    @Test(priority = 3)
    public void VerifyErrorUserCheckoutBug() throws InterruptedException {
        // Logout and Login as error_user
        driver.findElement(By.id("react-burger-menu-btn")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("logout_sidebar_link")).click();

        driver.findElement(By.id("user-name")).sendKeys("error_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        navigateToStepOne();

        // BUG: error_user often has a bug where the Last Name field cannot be filled or clears itself
        WebElement lastName = driver.findElement(By.id("last-name"));
        lastName.sendKeys("Ali");

        Assert.assertEquals(lastName.getAttribute("value"), "Ali",
                "Bug Detected (TC_003): 'error_user' cannot enter data into the Last Name field correctly!");
    }

    @Test(priority = 4) // TC_004 - BUG DETECTION (Spaces)
    public void VerifySpacesInFirstNameRejected() throws InterruptedException {
        // Reset to standard user if needed (re-login logic)
        driver.get("https://www.saucedemo.com/");
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        navigateToStepOne();
        driver.findElement(By.id("first-name")).sendKeys("   "); // 3 Spaces
        driver.findElement(By.id("last-name")).sendKeys("Ali");
        driver.findElement(By.id("postal-code")).sendKeys("12345");
        driver.findElement(By.id("continue")).click();

        Assert.assertFalse(driver.getCurrentUrl().contains("checkout-step-two.html"),
                "Bug Detected (TC_004): System accepted a name consisting only of spaces!");
    }

    @Test(priority = 5) // TC_005
    public void VerifyEmptyFirstNameRejected() throws InterruptedException {
        navigateToStepOne();
        driver.findElement(By.id("last-name")).sendKeys("Ali");
        driver.findElement(By.id("postal-code")).sendKeys("12345");
        driver.findElement(By.id("continue")).click();

        WebElement error = driver.findElement(By.cssSelector("[data-test='error']"));
        Assert.assertTrue(error.getText().contains("First Name is required"), "Empty first name was not rejected!");
    }

    @Test(priority = 6)
    public void VerifyFieldMaxBoundary() throws InterruptedException {
        navigateToStepOne();
        String longName = "A".repeat(1000);
        driver.findElement(By.id("first-name")).sendKeys(longName);
        driver.findElement(By.id("last-name")).sendKeys("Ali");
        driver.findElement(By.id("postal-code")).sendKeys("12345");
        driver.findElement(By.id("continue")).click();

        Assert.assertFalse(driver.getCurrentUrl().contains("checkout-step-two.html"),
                "Bug Detected (TC_006): System accepted a 1000-character name without validation!");
    }

    @Test(priority = 7)
    public void VerifyMinimumBoundary() throws InterruptedException {
        navigateToStepOne();
        driver.findElement(By.id("first-name")).sendKeys("a");
        driver.findElement(By.id("last-name")).sendKeys("Ali");
        driver.findElement(By.id("postal-code")).sendKeys("12345");
        driver.findElement(By.id("continue")).click();

        Assert.assertTrue(driver.getCurrentUrl().contains("checkout-step-two.html"), "Single character name should be accepted.");
    }

    @Test(priority = 8)
    public void VerifyNumbersInNameRejected() throws InterruptedException {
        navigateToStepOne();
        driver.findElement(By.id("first-name")).sendKeys("a1aa");
        driver.findElement(By.id("last-name")).sendKeys("Ali");
        driver.findElement(By.id("postal-code")).sendKeys("12345");
        driver.findElement(By.id("continue")).click();

        Assert.assertFalse(driver.getCurrentUrl().contains("checkout-step-two.html"),
                "Bug Detected : System allowed numbers inside the First Name field!");
    }
}