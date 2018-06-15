package annotation;

import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.After;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import pageObjects.LandingPage;
import utils.Base;

public class TestBehaviours {
    private Logger logger = LogManager.getLogger(TestBehaviours.class.getName());

    @Before("@Chrome")
    public void initChromeDriver()
    {
        logger.info("Launching chrome driver");
        Base.initDriver("chrome");

    }

    @Before("@Firefox")
    public void initFirefoxDriver()
    {
        logger.info("Launching firefox driver");
        Base.initDriver("firefox");
    }

    @After
    public void tearDown(Scenario scenario)
    {
        byte[] screenshot = ((TakesScreenshot) Base.getDriver()).getScreenshotAs(OutputType.BYTES);
        scenario.embed(screenshot,"image/png");
        Base.getDriver().close();
    }
}
