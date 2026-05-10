import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.openqa.selenium.Alert;

public class Sort extends Default {


    @BeforeClass
    public void preRequisites() throws InterruptedException {
        globalLogin();
    }

    @Test(priority = 1)
    public void SORT_product_Name_ZtoA() throws InterruptedException {
        driver.findElement(By.className("product_sort_container")).click();
        driver.findElement(By.xpath("//option[text()='Name (Z to A)']")).click();
        Thread.sleep(1000);
        Assert.assertFalse(driver.findElements(By.className("inventory_item_name")).isEmpty(), "Products not loaded!");
    }

    @Test(priority = 2)
    public void SORT_product_Price_HighToLow() throws InterruptedException {
        driver.findElement(By.className("product_sort_container")).click();
        driver.findElement(By.xpath("//option[text()='Price (high to low)']")).click();
        Thread.sleep(1000);

        Assert.assertFalse(driver.findElements(By.className("inventory_item_price")).isEmpty(), "Prices not loaded!");
    }

    @Test(priority = 3)
    public void SORT_product_Price_LowToHigh() throws InterruptedException {
        driver.findElement(By.className("product_sort_container")).click();
        driver.findElement(By.xpath("//option[text()='Price (low to high)']")).click();
        Thread.sleep(1000);

        Assert.assertFalse(driver.findElements(By.className("inventory_item_price")).isEmpty(), "Prices not loaded!");
    }

    @Test(priority = 4)
    public void verifyProductDetailsPageOpens() throws InterruptedException {
        driver.findElement(By.className("product_sort_container")).click();
        driver.findElement(By.xpath("//option[text()='Name (Z to A)']")).click();
        Thread.sleep(1000);

        driver.findElements(By.className("inventory_item_name")).get(0).click();
        Thread.sleep(1000);

        Assert.assertFalse(driver.findElements(By.className("inventory_details_name")).isEmpty(), "Product details page did NOT open!");

        driver.navigate().back();
        Thread.sleep(1000);
    }

    @Test(priority = 5)
    public void verifyAddFirstSortedProductToCart() throws InterruptedException {
        driver.findElement(By.className("product_sort_container")).click();
        driver.findElement(By.xpath("//option[text()='Name (Z to A)']")).click();
        Thread.sleep(1000);

        driver.findElements(By.className("btn_inventory")).getFirst().click();
        Thread.sleep(1000);

        Assert.assertFalse(driver.findElements(By.className("shopping_cart_badge")).isEmpty(), "Product was NOT added to cart!");
    }

    @Test(priority = 6)
    public void verifyCorrectItemAddedFromSortedList() throws InterruptedException {
        driver.findElement(By.className("shopping_cart_link")).click();
        Thread.sleep(1000);

        Assert.assertFalse(driver.findElements(By.className("inventory_item_name")).isEmpty(), "Cart is empty or wrong item!");
    }

    @Test(priority = 7)
    public void error_User_SortingBug() throws InterruptedException {
        // 1. Return to inventory and Log out the standard_user
        driver.findElement(By.id("react-burger-menu-btn")).click();
        Thread.sleep(1000);

        driver.findElement(By.id("logout_sidebar_link")).click();
        Thread.sleep(1000);

        Assert.assertFalse(driver.findElements(By.id("login-button")).isEmpty(), "Logout Failed!");

        // 2. Log in as error_user
        driver.findElement(By.id("user-name")).sendKeys("error_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        Thread.sleep(2000);

        // 3. Attempt to use the Sorting Dropdown (Select Z to A)
        driver.findElement(By.className("product_sort_container")).click();
        driver.findElement(By.xpath("//option[text()='Name (Z to A)']")).click();
        Thread.sleep(1000);


            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            alert.accept();
            Assert.fail("Bug Detected : Application crashed and threw a JavaScript alert! Alert text: [" + alertText + "]");


    }

}