import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.util.HashMap;
import java.util.Map;

public class Default {

    protected WebDriver driver;

    @BeforeClass
    public void globalSetup() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        options.addArguments("--disable-features=PasswordLeakDetection");

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        options.setExperimentalOption("prefs", prefs);
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
    }

    public void globalLogin() throws InterruptedException {
        driver.get("https://www.saucedemo.com/");
        Thread.sleep(1000);

        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        Thread.sleep(2000);
    }

    @AfterClass
    public void logout() throws InterruptedException {
        if (driver != null) {

            if(driver.getCurrentUrl().contains("inventory")) {
                driver.findElement(By.id("react-burger-menu-btn")).click();
                Thread.sleep(1000);

                driver.findElement(By.id("logout_sidebar_link")).click();
                Thread.sleep(1000);

                Assert.assertFalse(driver.findElements(By.id("login-button")).isEmpty(), "Logout Failed!");
            }

            driver.quit();
        }
    }
}