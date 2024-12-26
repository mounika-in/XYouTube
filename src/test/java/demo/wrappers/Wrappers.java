package demo.wrappers;

import org.apache.xmlbeans.impl.xb.xsdschema.ListDocument.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.Map;

public class Wrappers {

    /*
     * Write your selenium wrappers here
     */
    WebDriver driver;

    public Wrappers(WebDriver driver) {
        this.driver = driver;
    }



    ///// TESTCASE01////
    /*
     * Go to YouTube.com and Assert you are on the correct URL.
     * Click on "About" at the bottom of the sidebar,
     * and print the message on the screen.
     */
    public boolean openUrl(String URL) {
        driver.get(URL);
        if (driver.getCurrentUrl().equals(URL)) {
            return true;
        } else {
            return false;
        }
    }
    public boolean clickAbout() {
        WebElement btnAbout = driver.findElement(By.xpath("//*[@id=\"guide-links-primary\"]/a[1]"));
        btnAbout.click();
        return true;
    }
    public boolean messageDisplay() {
        // WebElement aboutMessage = driver.findElement(By.cssSelector("#content >
        // section > h1"));
        WebElement aboutMessage = driver.findElement(By.xpath("//*[@id=\"content\"]/section/h1"));
        System.out.println("About Message displayed as " + aboutMessage.getText());
        return true;
    }

    ///// TESTCASE02////
    /*
     * Go to the "Films" or "Movies" tab and
     * in the “Top Selling” section,scroll to the extreme right.
     * Apply a Soft Assert on whether the movie is marked “A” for Mature or not.
     * Apply a Soft assert on the movie category to check if it exists ex: "Comedy",
     * "Animation", "Drama".
     */
    public boolean moviesButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement moviesBtn = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@title='Films' or @title='Movies']")));
        moviesBtn.click();
        return true;
    }

    public static void scrollToRight(WebDriver driver, By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        while (true) {
            try {
                WebElement scrollBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
                scrollBtn.click();

                WebElement scrollBtn_updated = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
                if (!scrollBtn_updated.isDisplayed()){
                    break;
                }
                System.out.println("Scroll button has been clicked");
                
            } catch (TimeoutException e) {
                System.out.println(e.getMessage());
                return;
            }
        }
        System.out.println("Reached to the end of the videos ");
    }

    public static String movieRatings(WebDriver driver, By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement ratingElement = wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(locator,
                By.xpath(".//div[contains(@class,'style-type-simple')]/p")));
        return ratingElement.getText();
    }

    public static boolean movieCategory(WebDriver driver, By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(locator, By.xpath(".//h3/span")));
        System.out.println("Category has been detected");
        return true;
    }
    ///TESTCASE03///
    /*
     * testCase03: Go to the "Music" tab and in the 1st section,
     * scroll to the extreme right. Print the name of the playlist.
     * Soft Assert on whether the number of tracks listed is less than or equal to
     * 50.
     */
    public boolean musicButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement moviesElement = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@title='Music']")));
        moviesElement.click();
        return true;
    }

    public static boolean checkSongCount(WebDriver driver, By locator, int limit) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement countElement = wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(locator,
                By.xpath(".//div[@class='badge-shape-wiz__text']")));
        int currentSongCount = Integer.parseInt(countElement.getText().replaceAll("[^0-9]", ""));
        System.out.println("Number of songs = " + currentSongCount);
        if (currentSongCount <= limit) {
            return true;
        } else {
            return false;
        }
    }
    ///TESTCASE04///
   /*testCase04: Go to the "News" tab 
   and print the title and body of the 1st 3 “Latest News Posts” along with the sum of the number
    of likes on all 3 of them. No likes given means 0. */
    public boolean newsButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement newsTab = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@title='News']")));
        newsTab.click();
        return true;
    }
    public static void printTitles(WebDriver driver, By locator){
    
            try {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                WebElement latestNewPosts = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
                
                java.util.List <WebElement> newsLinks = latestNewPosts.findElements(By.xpath(".//div[@role='link']"));
                newsLinks = newsLinks.subList(0, 3);
    
                LinkedHashMap<String,Integer> hMap = new LinkedHashMap<>();
                for(WebElement newsLink : newsLinks){
                    
                    String title = newsLink.findElement(By.xpath(".//*[@id='home-content-text']/span[1]")).getText();
                    int likeCount = 0;
                    if(newsLink.findElements(By.xpath(".//span[@id='vote-count-middle']")).size()!=0){
                        likeCount = Integer.parseInt(
                            newsLink.findElement(By.xpath(".//span[@id='vote-count-middle']")).getText().trim()
                        );
                    }
                    hMap.put(title, likeCount);
                }
                
                int index=0,sum=0;
                for(Map.Entry<String,Integer> entry : hMap.entrySet()){
                    System.out.println("News Link"+(++index));
                    System.out.println("Content = "+entry.getKey()+"\n");
                    sum+=entry.getValue();
                }
                System.out.println("Total number of likes= "+sum);
    
    
            } catch (Exception e) {
                // TODO: handle exception
                System.out.println(e.getMessage());
            }
    

    }
}
