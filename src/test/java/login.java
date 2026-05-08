import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class login extends Default {


    @Test(priority = 1)
    public void valid_login() throws InterruptedException {
        driver.get("https://www.saucedemo.com/");
        Thread.sleep(1000);

        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        Thread.sleep(2000);

        Assert.assertTrue(driver.getCurrentUrl().contains("inventory"), "Valid login failed! URL: " + driver.getCurrentUrl());
    }

    @Test(priority = 2)
    public void invalid_login() throws InterruptedException {
        driver.get("https://www.saucedemo.com/");
        Thread.sleep(1000);

        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("Menna magdy");
        driver.findElement(By.id("login-button")).click();
        Thread.sleep(1000);

        WebElement errorMsg = driver.findElement(By.cssSelector("[data-test='error']"));
        Assert.assertTrue(errorMsg.isDisplayed(), "Error message not shown!");
        Assert.assertEquals(errorMsg.getText(), "Epic sadface: Username and password do not match any user in this service");
    }

    @Test(priority = 3)
    public void Locked_login() throws InterruptedException {
        driver.get("https://www.saucedemo.com/");
        Thread.sleep(1000);

        driver.findElement(By.id("user-name")).sendKeys("locked_out_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        Thread.sleep(1000);

        WebElement errorMsg = driver.findElement(By.cssSelector("[data-test='error']"));
        Assert.assertTrue(errorMsg.getText().contains("locked out"), "Expected locked out error message!");
    }
}