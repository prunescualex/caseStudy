package annotation;

import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.After;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import utils.Base;

public class TestBehaviours {

    @Before("@Chrome")
    public void initChromeDriver()
    {
        System.out.println("working test");
        Base.initDriver("chrome");
    }

    @Before("@Firefox")
    public void initFirefoxDriver()
    {
        System.out.println("CHECKING");
        Base.initDriver("firefox");
    }

    @Before("@MobileAndroid")
    public void initMobileAndroid()
    {
        Base.initDriver("mAndroid");
    }

    @Before("@TabletAndroid")
    public void tabletAndroid()
    {
        Base.initDriver("tAndroid");
    }

    @Before("@MobileIos")
    public void initMobileIos()
    {
        Base.initDriver("mIOS");
    }

    @Before("@TabletIOS")
    public void initTabletIos()
    {
        Base.initDriver("tIOS");
    }

    @After
    public void tearDown(Scenario scenario)
    {
        byte[] screenshot = ((TakesScreenshot) Base.getDriver()).getScreenshotAs(OutputType.BYTES);
        scenario.embed(screenshot,"image/png");
        Base.getDriver().close();
    }
}
