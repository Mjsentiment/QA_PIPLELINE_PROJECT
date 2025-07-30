package tests.selenium;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class GoogleSearchTest {

    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        // Ensure chromedriver is installed & available in PATH or Jenkins node
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void testGoogleSearch() {
        driver.get("https://www.google.com");
        WebElement searchBox = driver.findElement(By.name("q"));
        searchBox.sendKeys("Jenkins Pipeline CI/CD");
        searchBox.submit();
        assertTrue(driver.getTitle().toLowerCase().contains("jenkins"), "Title should contain 'jenkins'");
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
