import org.testng.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Add2Cart extends Default{
    @BeforeClass
    public void preRequisites() throws InterruptedException {
        globalLogin();
    }

    @Test(priority = 1)
    public void ProductsPageAddProduct() throws InterruptedException {
        WebElement e1 = driver.findElement(By.id("add-to-cart-sauce-labs-backpack"));
        Thread.sleep(2000);
        e1.click();

        String badge = driver.findElement(By.className("shopping_cart_badge")).getText();
        Assert.assertEquals(badge, "1", "First item was not added correctly!");
    }

    @Test(priority = 2)
    public void ProductDetails() throws InterruptedException {
        WebElement e1 = driver.findElement(By.xpath("//div[text()='Sauce Labs Bike Light']"));
        Thread.sleep(2000);
        e1.click();

        Assert.assertTrue(driver.getCurrentUrl().contains("inventory-item.html"), "Did not navigate to Details page!");
    }

    @Test(priority = 3)
    public void DetailsPageAddProduct() throws InterruptedException {
        WebElement e1 = driver.findElement(By.id("add-to-cart"));
        Thread.sleep(2000);
        e1.click();

        String badge = driver.findElement(By.className("shopping_cart_badge")).getText();
        Assert.assertEquals(badge, "2", "Second item was not added correctly!");
    }

    @Test(priority = 4)
    public void CartsPage() throws InterruptedException {
        WebElement e1 = driver.findElement(By.className("shopping_cart_link"));
        Thread.sleep(2000);
        e1.click();

        Assert.assertTrue(driver.getCurrentUrl().contains("cart.html"), "Did not reach the Cart page!");
    }

    @Test(priority = 5)
    public void ProductsPageAddProducts() throws InterruptedException {
        driver.findElement(By.id("continue-shopping")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("remove-sauce-labs-backpack")).click();
        driver.findElement(By.id("remove-sauce-labs-bike-light")).click();
        Thread.sleep(1000);

        WebElement e1 = driver.findElement(By.id("add-to-cart-sauce-labs-backpack"));
        Thread.sleep(2000);
        e1.click();

        String cartCount = driver.findElement(By.className("shopping_cart_badge")).getText();
        Assert.assertEquals(cartCount, "1", "Cart count did not update correctly!");

        String buttonText = driver.findElement(By.id("remove-sauce-labs-backpack")).getText();
        Assert.assertEquals(buttonText, "Remove", "Button text did not update to Remove!");
    }

    @Test(priority = 8)
    public void AddProduct() throws InterruptedException {
        driver.findElement(By.id("remove-sauce-labs-backpack")).click();
        driver.findElement(By.xpath("//div[text()='Sauce Labs Backpack']")).click();
        Thread.sleep(2000);

        WebElement e1 = driver.findElement(By.id("add-to-cart"));
        Thread.sleep(2000);
        e1.click();

        String buttonText = driver.findElement(By.id("remove")).getText();
        Assert.assertEquals(buttonText, "Remove", "The button text did not change!");

        String badge = driver.findElement(By.className("shopping_cart_badge")).getText();
        Assert.assertEquals(badge, "1", "Badge did not update on the Details page!");
    }

    @Test(priority = 10)
    public void ProductsPageAdd2Product() throws InterruptedException {
        driver.findElement(By.id("back-to-products")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("remove-sauce-labs-backpack")).click();
        Thread.sleep(1000);

        WebElement e1 = driver.findElement(By.id("add-to-cart-sauce-labs-backpack"));
        WebElement e2 = driver.findElement(By.id("add-to-cart-sauce-labs-bike-light"));
        Thread.sleep(2000);
        e1.click();
        Thread.sleep(2000);
        e2.click();

        String cartCount = driver.findElement(By.className("shopping_cart_badge")).getText();
        Assert.assertEquals(cartCount, "2", "Cart did not display 2 items!");
    }

    @Test(priority = 11)
    public void CartsPageandCheck() throws InterruptedException {
        WebElement e1 = driver.findElement(By.className("shopping_cart_link"));
        Thread.sleep(2000);
        e1.click();
        int actualItemsInCart = driver.findElements(By.className("cart_item")).size();
        Assert.assertEquals(actualItemsInCart, 2, "The number of items in the cart list is incorrect!");
    }
}