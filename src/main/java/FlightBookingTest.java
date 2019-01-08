import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import com.sun.javafx.PlatformUtil;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;



import java.util.List;
import java.util.concurrent.TimeUnit;

public class FlightBookingTest {

	
    public  WebDriver driver;//Updated code by Roshan
    public ChromeOptions options;


    @Test
    public void testThatResultsAppearForAOneWayJourney() {

    	
        setDriverPath();
        driver.get("https://www.cleartrip.com/");
        waitFor(2000);
        driver.findElement(By.id("OneWay")).click();
      
        driver.findElement(By.xpath("//*[@id='FromTag']")).click();     
        driver.findElement(By.xpath("//*[@id='FromTag']")).sendKeys("Bangalore");
    	
        //wait for the auto complete options to appear for the origin
        try{
        	//Updated code by Roshan Reason is sometime the list is not visible.
        waitFor(5000);
        List<WebElement> originOptions = driver.findElement(By.id("ui-id-1")).findElements(By.tagName("li"));
        originOptions.get(0).click();
        }catch(Exception e){
        	System.out.println("Message -> " + e.getMessage());
        }

        driver.findElement(By.xpath("//*[@id='ToTag']")).click();
        driver.findElement(By.xpath("//*[@id='ToTag']")).sendKeys("Delhi");     

        //wait for the auto complete options to appear for the destination

        try{
        	//Updated code by Roshan Reason is sometime the list is not visible.
        waitFor(5000);
        //select the first item from the destination auto complete list
        List<WebElement> destinationOptions = driver.findElement(By.id("ui-id-2")).findElements(By.tagName("li"));
        destinationOptions.get(0).click();
        }catch(Exception e){
        	System.out.println("Message -> " + e.getMessage());
        }
        driver.findElement(By.xpath("//*[@id='DepartDate']")).click();
        driver.findElement(By.xpath("//*[@id='ui-datepicker-div']/div[1]/table/tbody/tr[3]/td[7]/a")).click();

        //all fields filled in. Now click on search
        driver.findElement(By.id("SearchBtn")).click();

        
        /*Updated code by Roshan*/
        waitFor(15000);
        WebElement myDynamicElement = (new WebDriverWait(driver, 20))
        		  .until(ExpectedConditions.presenceOfElementLocated(By.className("searchSummary")));
        //verify that result appears for the provided journey search
        AssertJUnit.assertTrue(isElementPresent(By.className("searchSummary")));

        //close the browser
        driver.quit();

    }


    private void waitFor(int durationInMilliSeconds) {
        try {
            Thread.sleep(durationInMilliSeconds);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }


    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private void setDriverPath() {
        if (PlatformUtil.isMac()) {
            System.setProperty("webdriver.chrome.driver", "chromedriver");
            
        }
        /*Updated isWindow code by Roshan*/
        if (PlatformUtil.isWindows()) {
        	/*Updated code by Roshan*/
        	options = new ChromeOptions();
        	options.addArguments("--disable-notifications");
            System.setProperty("webdriver.chrome.driver", "src\\main\\java\\drivers\\chromedriver.exe");
            driver = new ChromeDriver(options);
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        }
        if (PlatformUtil.isLinux()) {
            System.setProperty("webdriver.chrome.driver", "chromedriver_linux");
        }
    }
}
