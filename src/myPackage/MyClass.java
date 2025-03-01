package myPackage;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


public class MyClass {
    public static void main(String[] args) {
        // Set up chrome WebDriver
    	System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");


        WebDriver driver = new ChromeDriver();
       WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));


        try {
            driver.manage().window().maximize();
            driver.get("https://www.phillypolice.com/");
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
            System.out.println("Current URL: " + driver.getCurrentUrl());

            // Accept cookies
            WebElement acceptButton = driver.findElement(By.cssSelector("button.cmplz-btn.cmplz-accept"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", acceptButton);
            Thread.sleep(5000);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", acceptButton);
            System.out.println("Clicked 'Accept' button.");

            // Click on "Districts"
            WebElement districtsButton = driver.findElement(By.xpath("//span[text()='Districts']"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", districtsButton);
            Thread.sleep(500);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", districtsButton);
            System.out.println("Inside district menu");

            // Click "Districts Map"
            WebElement districtsMapHeading = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h2[text()='Districts Map']")));
            districtsMapHeading.click();

            // Wait for the GIS page
            wait.until(ExpectedConditions.urlToBe("https://www.phillypolice.com/district/district-gis/"));
            System.out.println("URL Verified for GIS page: " + driver.getCurrentUrl());
            
            wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete'"));
            System.out.println("Page fully loaded!");

            // **Fixing Search Box Interaction Issue**
            WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("search-box")));

            // Remove lazy-loading class (if applicable)
            ((JavascriptExecutor) driver).executeScript("arguments[0].classList.remove('nitro-lazy');", searchBox);

            // Ensure it's clickable
            wait.until(ExpectedConditions.elementToBeClickable(searchBox));

            // Click using JavaScript
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", searchBox);

            // Enter address and submit
            String searchAdd = "Franklin Mall, Franklin Mills Circle, Philadelphia, PA, USA";
            searchBox.sendKeys(searchAdd + Keys.ENTER);
            
            // Scroll back to top
            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);");
            
            //wait for the Close button on the pop-up to be clickable.
            WebElement closeButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("close-info")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", closeButton);
            Thread.sleep(1000);
            
            
            //Click close Button using JavaScript (if normal click fails)
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", closeButton);
            System.out.println("Clicked the close button successfully!");
           Thread.sleep(2000);
            
			// **Click on "Home" Menu**
            WebElement homeElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[@id='menu-item-82068']//span[@class='menu-text'][normalize-space()='Home']")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", homeElement);
            System.out.println("Navigated back to Home!");
            
            Thread.sleep(2000);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the browser
            driver.quit();
            
        }
    }
}
