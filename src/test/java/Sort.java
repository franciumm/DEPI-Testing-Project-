import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


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


}