import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.sun.javafx.PlatformUtil;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

public class HotelBookingTest {

    public WebDriver driver;
    public ChromeOptions options;

    /*Updated xpath by Roshan*/
    @FindBy(xpath = "(//li[normalize-space(@class)='hotelApp']/a[1])[2]")
    private WebElement hotelLink;

    @FindBy(xpath = "//input[@id='Tags']")
    private WebElement localityTextBox;

    @FindBy(id = "SearchHotelsButton")
    private WebElement searchButton;

    @FindBy(id = "travellersOnhome")
    private WebElement travellerSelection;

    @Test 
    public void shouldBeAbleToSearchForHotels() {
        setDriverPath();

        driver.get("https://www.cleartrip.com/");
        
        hotelLink.click();
        localityTextBox.click();
       // driver.switchTo().activeElement().sendKeys("roshan");
        localityTextBox.sendKeys("Indiranagar, Bangalore");
        waitFor(15000);
        
        /* reason to write this code becoz on the time when enter the area name name so
         * list of area is visible not able to saw the search button it was on disable hence
         */
        try{
        	//Updated code by Roshan Reason is sometime the list is not visible.      
        	List<WebElement> originOptions = driver.findElements(By.xpath("//ul[@id='ui-id-1']/li/a")); 
        	
        	originOptions.get(0).click();
        	
        }catch(Exception e){
        	System.out.println("Message -> " + e.getMessage());
        }
        waitFor(3000);
        // reason to escape is it is auto click on calender
        driver.switchTo().activeElement().sendKeys(Keys.ESCAPE);
        waitFor(3000);
        new Select(travellerSelection).selectByVisibleText("1 room, 2 adults");
        searchButton.click();     
        waitFor(15000);
        driver.quit();

    }
    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }
    
    private void waitFor(int durationInMilliSeconds) {
        try {
            Thread.sleep(durationInMilliSeconds);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
    private void setDriverPath() {
        if (PlatformUtil.isMac()) {
            System.setProperty("webdriver.chrome.driver", "chromedriver");
        }
        
        /*Updated isWindow code by Roshan*/
        if (PlatformUtil.isWindows()) {
        	options = new ChromeOptions();
        	options.addArguments("--disable-notifications");
            System.setProperty("webdriver.chrome.driver", "src\\main\\java\\drivers\\chromedriver.exe");
            driver = new ChromeDriver(options);
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            PageFactory.initElements(driver, this);
        }
        if (PlatformUtil.isLinux()) {
            System.setProperty("webdriver.chrome.driver", "chromedriver_linux");
        }
    }

}
