package demo;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.logging.Level;

import demo.utils.ExcelDataProvider;
// import io.github.bonigarcia.wdm.WebDriverManager;
import demo.wrappers.Wrappers;

public class TestCases extends ExcelDataProvider { // Lets us read the data
        ChromeDriver driver;
        // SoftAssert sAssert = new SoftAssert();
        boolean status = false;

        /*
         * TODO: Write your tests here with testng @Test annotation.
         * Follow `testCase01` `testCase02`... format or what is provided in
         * instructions
         */

        @Test(enabled = true)
        public void testCase01() {
                System.out.println("Start testCase01");
                Wrappers wrappers = new Wrappers(driver);
                wrappers.openUrl("https://www.youtube.com/");
                SoftAssert sAssert = new SoftAssert();
                sAssert.assertTrue(status, "Cannot able to open");
                wrappers.clickAbout();
                sAssert.assertTrue(status, "Cannot able to click");
                wrappers.messageDisplay();
                sAssert.assertTrue(status, "Message is not displayed");
                System.out.println("End of testCase01");

        }

        @Test(enabled = true)
        public void testCase02() {
                System.out.println("Start testCase02");
                driver.get("https://www.youtube.com/");
                Wrappers wrappers = new Wrappers(driver);
                wrappers.moviesButton();
                Wrappers.scrollToRight(driver, By.xpath("//div[@id='right-arrow']/ytd-button-renderer"));

                SoftAssert sAssert = new SoftAssert();
                sAssert.assertEquals(Wrappers.movieRatings(driver,
                        By.xpath("//div[@id='items' and @class='style-scope yt-horizontal-list-renderer']/*[last()]")),
                                "U", "Rating is not U");

                sAssert.assertTrue(Wrappers.movieCategory(driver,
                        By.xpath("//div[@id='items' and @class='style-scope yt-horizontal-list-renderer']/*[last()]")),
                                "Category does not exist for the movie");
                System.out.println("End of testCase02");

        }

        @Test(enabled = true)
        public void testCase03() {
                System.out.println("Start testCase03");
                driver.get("https://www.youtube.com/");
                Wrappers wrappers = new Wrappers(driver);
                wrappers.musicButton();
                Wrappers.scrollToRight(driver, By.xpath("(//div[@id='right-arrow']//button)[1]"));
                SoftAssert sAssert = new SoftAssert();
                
                sAssert.assertTrue(Wrappers.checkSongCount(driver, 
                By.xpath("(//div[@id='items' and @class='style-scope yt-horizontal-list-renderer'])[1]/*[last()]"),
                 50),
                 "Song count is not lessthan or equal to fifty");
                 System.out.println("End of testCase03");

        }

        @Test(enabled = true)
        public void testCase04() {
                System.out.println("Start testCase04");
                driver.get("https://www.youtube.com/");
                Wrappers wrappers = new Wrappers(driver);
                wrappers.newsButton();
                Wrappers.printTitles(driver, 
                By.xpath("//ytd-rich-section-renderer[@class='style-scope ytd-rich-grid-renderer'][descendant::span[text()='Latest news posts']]"));
                System.out.println("End of testCase04");


        }


        /*
         * Do not change the provided methods unless necessary, they will help in
         * automation and assessment
         */
        @BeforeTest
        public void startBrowser() {
                System.setProperty("java.util.logging.config.file", "logging.properties");

                // NOT NEEDED FOR SELENIUM MANAGER
                // WebDriverManager.chromedriver().timeout(30).setup();

                ChromeOptions options = new ChromeOptions();
                LoggingPreferences logs = new LoggingPreferences();

                logs.enable(LogType.BROWSER, Level.ALL);
                logs.enable(LogType.DRIVER, Level.ALL);
                options.setCapability("goog:loggingPrefs", logs);
                options.addArguments("--remote-allow-origins=*");

                System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log");

                driver = new ChromeDriver(options);

                driver.manage().window().maximize();
        }

        @AfterTest
        public void endTest() {
                driver.close();
                driver.quit();

        }
}