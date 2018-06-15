package utils;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class Base {

    private static Logger logger = LogManager.getLogger(Base.class.getName());
    private static WebDriver driver;

    private static Map<Long, WebDriver> webDriverMap = new HashMap<>();


    public static void initDriver(String browser)
    {
        switch(browser)
        {
            case "chrome" : setDriver(launchDriverGoogle());break;
            case "firefox" : setDriver(launchDriverFirefox());break;
            case "mAndroid" : setDriver(launchMobileAndroid());break;
            case "tAndroid" : setDriver(launchTabletAndroid());break;
            case "mIOS" : setDriver(launchMobileIos());break;
            case "tIOS" : setDriver(launchTabletIos());break;
        }
    }

    /**
     * Initializses Google Chrome
     * @return
     */
    public static WebDriver launchDriverGoogle(){
        String driverLocation = System.getProperty("user.dir") + "\\browser_drivers\\chromedriver.exe";
        System.setProperty("webdriver.chrome.driver",driverLocation);

        driver = new ChromeDriver();
        driver.manage().window().maximize();

        return driver;
    }

    /**
     * Initializes Firefox
     * @return
     */
    public static WebDriver launchDriverFirefox(){

        System.out.println("CHECKING 121");

        String driverLocation = System.getProperty("user.dir") + "\\browser_drivers\\geckodriver.exe";
        System.setProperty("webdriver.gecko.driver",driverLocation);

        FirefoxProfile profile = new FirefoxProfile();
        profile.setAcceptUntrustedCertificates(true);

        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
        capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS,true);

        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        return driver;
    }

    public static WebDriver launchMobileAndroid(){
        return driver;
    }

    public static WebDriver launchTabletAndroid(){
        return driver;
    }

    public static WebDriver launchMobileIos(){
        return driver;
    }

    public static WebDriver launchTabletIos(){
        return driver;
    }


    public static void setDriver(WebDriver driver)
    {
        webDriverMap.put(Thread.currentThread().getId(),driver);
    }

    /**
     * @return the driver used by the current thread.
     */
    public static WebDriver getDriver()
    {
        return webDriverMap.get(Thread.currentThread().getId());
    }

    /**
     * Custom method for selecting a value on a dropdown
     * @param selectElement - element to be used
     * @param selectedValue - value to select
     * @return true if success
     */
    public static boolean customSelect(WebElement selectElement, String selectedValue)
    {
        try
        {
            Select select = new Select(selectElement);
            select.selectByValue(selectedValue);
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }

    /**
     * Write to a given field
     * @param element element used
     * @param arg String to be writen
     * @return true if success
     */
    public static boolean writeToField(WebElement element, String arg)
    {
        try
        {
            element.click();
            element.clear();
            element.sendKeys(arg);
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }

    /**
     * Click on an element
     * @param element
     * @return
     */
    public static boolean clickToElement(WebElement element)
    {
        try
        {
            if(element.isDisplayed())
            {
                element.click();
            }
            else
                return false;
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }

    /**
     * Focus the image on a given elements by scrolling
     * @param element
     */
    public static void focusElement(WebElement element)
    {
        ((JavascriptExecutor)getDriver()).executeScript("arguments[0].scrollIntoView(true);",element);
        ((JavascriptExecutor)getDriver()).executeScript("window.scrollBy(0,-250)");
    }

    public static void highlightElement(WebElement element)
    {
        try
        {
            String originalStyle = element.getAttribute("style");
            JavascriptExecutor js = (JavascriptExecutor) getDriver();
            js.executeScript("arguments[0].setAttribute('style', 'border:5px solid red;')",element);
            Thread.sleep(2000);

            js.executeScript("arguments[0].setAttribute('style','"+originalStyle+"');",element);
        }
        catch(Exception e)
        {

        }
    }

    /**
     * Custom wait for element to be available
     * @param element
     * @return
     */
    public static boolean FluentWaitForElement(WebElement element)
    {
        return new FluentWait<>(getDriver())
                .withTimeout(30, TimeUnit.SECONDS)
                .pollingEvery(200, TimeUnit.MILLISECONDS)
                .ignoring(NoSuchElementException.class)
                .until(new Function<WebDriver,Boolean>(){
                    @Override
                    public Boolean apply(WebDriver webDriver) {
                        return element.isDisplayed();
                    }
                });
    }

    /**
     * Custom wait for ajax calls
     * @throws InterruptedException
     */
    public static void waitForAjax() throws InterruptedException
    {
        while (true)
        {
            Boolean ajaxCompleted = (Boolean) ((JavascriptExecutor)getDriver()).executeScript("return jQuery.active == 0");
            if (ajaxCompleted){
                break;
            }
            Thread.sleep(100);
        }
    }


    public static void wwwVisit(String url)
    {
        getDriver().navigate().to(url);
    }
}
