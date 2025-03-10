package myPackage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.time.Duration;
import java.util.List;

public class CheckAllLinks {
    public static void main(String[] args) {
        // Set path for GeckoDriver
    	System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");

    	 // Set ChromeOptions for Headless Mode
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // Enable headless mode

        // Initialize WebDriver for Chrome(headless)
        WebDriver driver = new ChromeDriver(options);

        // Open the website
        String websiteURL = "https://www.phillypolice.com/";
        driver.get(websiteURL);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // Get all links on the page
        List<WebElement> links = driver.findElements(By.tagName("a"));
        System.out.println("Total Links Found: " + links.size());

        // Check each link
        for (WebElement link : links) {
            String url = link.getDomProperty("href"); // Use getDomProperty for absolute URLs

            if (url != null && !url.isEmpty() && (url.startsWith("http") || url.startsWith("https"))) {
                checkBrokenLink(url);
            }
        }

        // Close the browser
        driver.quit();
        
        // Display final message
        System.out.println("All links have been checked!");
    }

    // Function to check broken links with timeout handling
    public static void checkBrokenLink(String url) {
        try {
            URL link = new URL(url);
            HttpURLConnection httpConn = (HttpURLConnection) link.openConnection();
            httpConn.setRequestMethod("HEAD");
            httpConn.setConnectTimeout(5000);  // Set timeout (5 seconds)
            httpConn.setReadTimeout(5000);
            httpConn.connect();

            int responseCode = httpConn.getResponseCode();

            if (responseCode >= 400) {
                System.out.println("BROKEN LINK: " + url + " | Status Code: " + responseCode);
            } else {
                System.out.println("Valid Link: " + url + " | Status Code: " + responseCode);
            }

        } catch (SocketTimeoutException e) {
            System.out.println("⚠️ TIMEOUT ERROR: " + url + " | Connection timed out");
        } catch (IOException e) {
            System.out.println("❌ BROKEN LINK (Exception): " + url + " | " + e.getMessage());
        }
    }
}
