import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class logout extends Default {


    @Test(priority = 1)
    public void verifyLogoutRedirectsToLogin() throws InterruptedException {
        globalLogin();

        driver.findElement(By.id("react-burger-menu-btn")).click();
        Thread.sleep(1000);

        driver.findElement(By.id("logout_sidebar_link")).click();
        Thread.sleep(1000);

        Assert.assertFalse(driver.findElements(By.id("login-button")).isEmpty(), "User was NOT redirected to Login page!");
    }

    @Test(priority = 2)
    public void verifyLogoutClearsCredentials() throws InterruptedException {
        globalLogin();

        driver.findElement(By.id("react-burger-menu-btn")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("logout_sidebar_link")).click();
        Thread.sleep(1000);

        String usernameValue = driver.findElement(By.id("user-name")).getAttribute("value");
        String passwordValue = driver.findElement(By.id("password")).getAttribute("value");

        Assert.assertNotNull(usernameValue);
        Assert.assertTrue(usernameValue.isEmpty(), "Username field was not cleared!");
        Assert.assertNotNull(passwordValue);
        Assert.assertTrue(passwordValue.isEmpty(), "Password field was not cleared!");
    }

    @Test(priority = 3)
    public void verifyBackNavigationBlockedAfterLogout() throws InterruptedException {
        globalLogin();

        driver.findElement(By.id("react-burger-menu-btn")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("logout_sidebar_link")).click();
        Thread.sleep(1000);

        driver.navigate().back();
        Thread.sleep(1000);

        WebElement errorMsg = driver.findElement(By.cssSelector("[data-test='error']"));
        Assert.assertTrue(errorMsg.isDisplayed(), "Security Error message was not shown!");
        Assert.assertTrue(errorMsg.getText().contains("You can only access '/inventory.html' when you are logged in"),
                "Incorrect error message shown!");
    }

}