package myPackage;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.Select;
import java.io.File;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.io.FileHandler;
import java.time.Duration;

public class Form_RollCall {

    public static void main(String[] args) {
        // Set up Chrome WebDriver
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");

        // Chrome Driver Initialization
        WebDriver driver = new ChromeDriver();

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
            
            //go to forms page
            driver.get("https://www.phillypolice.com/forms/");
            System.out.println("Current URL: " + driver.getCurrentUrl());
            
            // Create WebDriverWait instance. Wait for 20 seconds for a particular conditon before throwing timeout exception
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

            // Wait until the link for rollcall complain form is clickable
            WebElement rollCall = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h2[text()='Neighborhood Concern (Roll Call Complaint)']")));
            rollCall.click();
            
         // Verify the current URL
            String expectedURL = "https://www.phillypolice.com/forms/neighborhood-concern-roll-call-complaint/";
            String actualURL = driver.getCurrentUrl();
            
            if (actualURL.equals(expectedURL)) {
                System.out.println("The page URL is correct for Roll Call complaint.");
            } else {
                System.out.println("The page URL is wrong for Roll Call complaint! Expected: " + expectedURL + " but found: " + actualURL);
            }
            
            //wait till the dropdown loads fully for Select Police District or Unit
            WebElement dropdowndistrict = wait.until(ExpectedConditions.elementToBeClickable(By.id("input_3_23")));
            Select selectdistr = new Select(dropdowndistrict);
            selectdistr.selectByVisibleText("9th"); // or select.selectByValue("9th");
            
            System.out.println("Successfully selected '9th' from the dropdown!");	
            
            //multi-select checkboxes for What is your concern:
            WebElement checkboxAuto = driver.findElement(By.id("choice_3_2_1"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", checkboxAuto);
            Thread.sleep(1000);  // Small pause to ensure it's fully visible
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkboxAuto);
            
            WebElement checkboxSecurity = driver.findElement(By.id("choice_3_2_7"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", checkboxSecurity);
            Thread.sleep(1000);  // Small pause to ensure it's fully visible
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkboxSecurity);
            
            //fill up the text Enter your concern for detail;
            String textComplain = "Abandoned auto and security issue in neighborhood";
            WebElement textBoxConcern = driver.findElement(By.id("input_3_6"));
            textBoxConcern.sendKeys(textComplain);
            
            //fill up where does this concern occur:
            WebElement testWhere = driver.findElement(By.id("input_3_26"));
            testWhere.sendKeys("26th Avenue, next to Townhall");
            
          //multi-select checkboxes for when does it occur?
            WebElement checkboxMon = driver.findElement(By.id("choice_3_8_1"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", checkboxMon);
            Thread.sleep(1000);  // Small pause to ensure it's fully visible
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkboxMon);
            
            WebElement checkboxFri = driver.findElement(By.id("choice_3_8_5"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", checkboxFri);
            Thread.sleep(1000);  // Small pause to ensure it's fully visible
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkboxFri);
            
          //select checkbox for what time does it occur:
            WebElement checkboxNight = driver.findElement(By.id("choice_3_10_3"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", checkboxNight);
            Thread.sleep(1000);  // Small pause to ensure it's fully visible
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkboxNight);
            
            //type email"
            WebElement emailField = driver.findElement(By.id("input_3_22"));
            emailField.sendKeys("testemail@example.com");
            
         // Scroll to middle of the page
            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight/2);");
            Thread.sleep(2000); // Allow some time for the scroll to take effect
            
         // Take full page screenshot before closing the browser
            try {
                File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                File destination = new File("C:\\screenshots\\Rollcallfullpage.png");
                FileHandler.copy(screenshot, destination);
                System.out.println("Full page screenshot saved at: " + destination.getAbsolutePath());
            } catch (Exception e) {
                System.out.println("Failed to capture screenshot: " + e.getMessage());
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the browser
           driver.quit();
        }
    }
}
