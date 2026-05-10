
import org.testng.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class CustomerInfo extends Default {

    @BeforeClass
    public void preRequisites() throws InterruptedException {
        globalLogin();
    }

    @Test(priority = 1)
    public void testOpenCheckoutPage() throws InterruptedException {
        // Add item and navigate to checkout page
        driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
        Thread.sleep(1000);
        driver.findElement(By.className("shopping_cart_link")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("checkout")).click();
        Thread.sleep(2000);

        Assert.assertTrue(driver.getCurrentUrl().contains("checkout-step-one.html"),
                "Not on checkout page!");
    }

    @Test(priority = 2)
    public void testFirstNameEmptyError() throws InterruptedException {
        // Leave first name empty, fill others, click continue
        driver.findElement(By.id("first-name")).clear();
        driver.findElement(By.id("last-name")).sendKeys("Ali");
        driver.findElement(By.id("postal-code")).sendKeys("14526");
        driver.findElement(By.id("continue")).click();
        Thread.sleep(1000);

        WebElement error = driver.findElement(By.cssSelector("[data-test='error']"));
        Assert.assertTrue(error.isDisplayed(), "Error not displayed for empty first name!");
    }

    @Test(priority = 3)
    public void testLastNameEmptyError() throws InterruptedException {
        // Refresh/reset by manually clearing fields
        driver.findElement(By.id("first-name")).clear();
        driver.findElement(By.id("first-name")).sendKeys("Abdo");
        driver.findElement(By.id("last-name")).clear();
        driver.findElement(By.id("postal-code")).clear();
        driver.findElement(By.id("postal-code")).sendKeys("14526");
        driver.findElement(By.id("continue")).click();
        Thread.sleep(1000);

        WebElement error = driver.findElement(By.cssSelector("[data-test='error']"));
        Assert.assertTrue(error.isDisplayed(), "Error not displayed for empty last name!");
    }

    @Test(priority = 4)
    public void testPostalCodeEmptyError() throws InterruptedException {
        driver.findElement(By.id("first-name")).clear();
        driver.findElement(By.id("first-name")).sendKeys("Abdo");
        driver.findElement(By.id("last-name")).clear();
        driver.findElement(By.id("last-name")).sendKeys("Ali");
        driver.findElement(By.id("postal-code")).clear();
        driver.findElement(By.id("continue")).click();
        Thread.sleep(1000);

        WebElement error = driver.findElement(By.cssSelector("[data-test='error']"));
        Assert.assertTrue(error.isDisplayed(), "Error not displayed for empty postal code!");
    }

    @Test(priority = 5)
    public void testFirstNameSpacesError() throws InterruptedException {
        driver.findElement(By.id("first-name")).clear();
        driver.findElement(By.id("first-name")).sendKeys("   ");
        driver.findElement(By.id("last-name")).clear();
        driver.findElement(By.id("last-name")).sendKeys("Ali");
        driver.findElement(By.id("postal-code")).clear();
        driver.findElement(By.id("postal-code")).sendKeys("14526");
        driver.findElement(By.id("continue")).click();
        Thread.sleep(1000);

        WebElement error = driver.findElement(By.cssSelector("[data-test='error']"));
        Assert.assertTrue(error.isDisplayed(), "Error not displayed for spaces in first name!");
    }

    @Test(priority = 6)
    public void testValidPostalCodeNumeric() throws InterruptedException {
        // Correct all fields with numeric postal code (valid)
        driver.findElement(By.id("first-name")).clear();
        driver.findElement(By.id("first-name")).sendKeys("Abdo");
        driver.findElement(By.id("last-name")).clear();
        driver.findElement(By.id("last-name")).sendKeys("Ali");
        driver.findElement(By.id("postal-code")).clear();
        driver.findElement(By.id("postal-code")).sendKeys("90210");
        driver.findElement(By.id("continue")).click();
        Thread.sleep(2000);

        // After valid submission we should arrive at overview page
        Assert.assertTrue(driver.getCurrentUrl().contains("checkout-step-two.html"),
                "Did not reach overview page with valid numeric postal code!");
    }

    @Test(priority = 7)
    public void testAllFieldsEmptyError() throws InterruptedException {
        // Go back to customer info page to test empty fields scenario
        driver.navigate().back();  // back to step one
        Thread.sleep(1000);
        // Clear all fields and submit
        driver.findElement(By.id("first-name")).clear();
        driver.findElement(By.id("last-name")).clear();
        driver.findElement(By.id("postal-code")).clear();
        driver.findElement(By.id("continue")).click();
        Thread.sleep(1000);

        WebElement error = driver.findElement(By.cssSelector("[data-test='error']"));
        Assert.assertTrue(error.isDisplayed(), "Error not displayed when all fields empty!");
    }

    @Test(priority = 8)
    public void testSuccessfulSubmissionToOverview() throws InterruptedException {
        // Fill with correct data and continue
        driver.findElement(By.id("first-name")).sendKeys("Abdo");
        driver.findElement(By.id("last-name")).sendKeys("Ali");
        driver.findElement(By.id("postal-code")).sendKeys("14526");
        driver.findElement(By.id("continue")).click();
        Thread.sleep(2000);

        Assert.assertTrue(driver.getCurrentUrl().contains("checkout-step-two.html"),
                "Successful submission did not navigate to Overview page!");
    }
}