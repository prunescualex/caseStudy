package pageObjects;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocator;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

import utils.Base;
import utils.DataGenerator;

import java.util.List;

import static junit.framework.TestCase.assertTrue;

public class LandingPage extends LoadableComponent<LandingPage> {

    private static int checkinSelectedMonth = 0;
    private static int checkinSelectedWeek;
    private static int checkinSelectedDay;

    public LandingPage()
    {
        Base.getDriver().navigate().to("https://www.booking.com");
    }

    private Logger logger = LogManager.getLogger(LandingPage.class.getName());

    @FindBy(xpath = "//*[@id=\"ss\"]")
    private WebElement locationSearch;

    @FindBy(xpath = "//*[@id=\"frm\"]/div[1]/div[2]/div/div[2]/div/div/div/div[1]/div")
    private WebElement checkinWidget;

    @FindBy(xpath = "//*[@id=\"frm\"]/div[1]/div[2]/div/div[3]/div/div/div/div[1]/div")
    private WebElement checkoutWidget;

    @FindBy(css = "[class*=c2-button-inner]")
    private List<WebElement> calendarArrow;

    //
    @FindBy(xpath = "//*[@id=\"frm\"]/div[1]/div[2]/div/div[2]/div/div/div/div[1]/div/div[1]/div[1]/select")
    private WebElement checkinMonthYear;

    @FindBy(xpath = "//*[@id=\"frm\"]/div[1]/div[2]/div/div[2]/div/div/div/div[1]/div/div[1]/div[2]/select")
    private WebElement checkoutMonthYear;

    @FindBy(css = "select[name*=checkin_monthday]")
    private WebElement checkinDay;

    @FindBy(css = "select[name*=checkout_monthday]")
    private WebElement checkoutDay;

    //not used
    @FindBy(css = "label>span[class*=xp__guests]")
    private WebElement selectPeople;

    @FindBy(css = "select[name*=no_rooms]")
    private WebElement numberOfRooms;

    @FindBy(css = "select[name*=group_adults")
    private WebElement numberOfAdults;

    @FindBy(css = "select[name*=group_children]")
    private WebElement numberOfChildren;

    @FindBy(css = "button[class*=searchbox__button]")
    private WebElement searchButton;


    public boolean selectDateCheckin()
    {
        try
        {
            Thread.sleep(1500);
            Base.highlightElement(checkinWidget);
            Actions actions = new Actions(Base.getDriver());
            actions.moveToElement(checkinWidget).click().build().perform();
            chooseCalendarDates(1,0,0,1);
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }

    public boolean selectDateCheckout(){
        try{
            Base.highlightElement(checkoutWidget);
            Actions actions = new Actions(Base.getDriver());
            actions.moveToElement(checkoutWidget).click().build().perform();
            chooseCalendarDates(checkinSelectedDay,checkinSelectedWeek,checkinSelectedDay,3);

            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }

    public void chooseCalendarDates(int lowestDay,int lowestWeek, int lowestMonth,int calendarArrowNumber)
    {
        Actions actions = new Actions(Base.getDriver());
        List<WebElement> months = Base.getDriver().findElements(By.cssSelector("table[class*=c2-month]"));
        int month;
        WebElement currentMonth;
        //if selecting checking allowin multiple months
        if(checkinSelectedMonth == 0)
        {
            month = DataGenerator.numberRandomGenerator(lowestMonth,12);
            currentMonth = months.get(month);
        }
        else// for checkout max 30 nights allowed
        {
            month = DataGenerator.numberRandomGenerator(0,1);
            currentMonth = months.get(16 + checkinSelectedMonth + month );
        }
        checkinSelectedMonth = month;

        WebElement rightArrow =calendarArrow.get(calendarArrowNumber);
        for(int i=0;i<month;i++)
        {
            actions.moveToElement(rightArrow).click().build().perform();
        }

        List<WebElement> weeks = currentMonth.findElements(By.cssSelector("tbody>tr"));
        int randomRow;
        if(month == 1)
        {
            randomRow = DataGenerator.numberRandomGenerator(0,DataGenerator.getCurrentWeek());
        }
        else
        {
            randomRow = DataGenerator.numberRandomGenerator(DataGenerator.getCurrentWeek()+1, weeks.size());
        }

        checkinSelectedWeek = randomRow;
        WebElement currentWeek = weeks.get(randomRow);
        Base.highlightElement(currentWeek);
        List<WebElement> days = currentWeek.findElements(By.cssSelector("td[class*=c2-day"));

        int randomDay;
        if(month == 1)
        {
            randomDay  = DataGenerator.numberRandomGenerator(lowestDay,days.size()-lowestDay);
        }
        else
        {
            if(lowestDay == 0)
                randomDay = DataGenerator.numberRandomGenerator(DataGenerator.getCurrentDate(),days.size());
            else
                randomDay = DataGenerator.numberRandomGenerator(lowestDay,days.size());
        }

        checkinSelectedDay = randomDay;
        WebElement selectedDay = days.get(randomDay);
        Base.highlightElement(selectedDay);
        actions.moveToElement(selectedDay).click().build().perform();
    }


    /**
     * Writes a given text to the destination field
     * @param arg - text to be writen
     * @return true if success
     */
    public boolean writeDestination(String arg)
    {
        try {
            Base.FluentWaitForElement(locationSearch);
            Base.writeToField(locationSearch, arg);
            return true;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Chooses number of adults from the dropwdown
     * @param arg - number represented as String
     * @return true if success
     */
    public boolean selectAdults(String arg)
    {

        Base.highlightElement(selectPeople);
        Actions actions = new Actions(Base.getDriver());
        actions.moveToElement(selectPeople).click().build().perform();
        Base.highlightElement(numberOfAdults);
        return Base.customSelect(numberOfAdults,arg);
    }

    /**
     * Chooses number of children from the dropdown
     * @param arg - number represented as String
     * @return true if success
     */
    public boolean selectChildren(String arg)
    {
        Base.highlightElement(numberOfChildren);
        return Base.customSelect(numberOfChildren,arg);
    }

    /**
     * Set children age at checkout - random
     * @return true if success
     */
    public boolean setChildrenAge()
    {
        int age = DataGenerator.numberRandomGenerator(1,15);
        WebElement ageOfChildren = Base.getDriver().findElement(By.cssSelector("select[name*=age]"));
        return Base.customSelect(ageOfChildren,String.valueOf(age));
    }

    /**
     * Set the number of rooms
     * @param arg int
     * @return true if success
     */
    public boolean selectRooms(String arg)
    {
        Base.highlightElement(numberOfRooms);
        return Base.customSelect(numberOfRooms,arg);
    }

    public boolean clickSearch()
    {
        return Base.clickToElement(searchButton);
    }

    @Override
    protected void load() {

    }

    @Override
    protected void isLoaded() throws Error {

        AjaxElementLocatorFactory locatorFactory = new AjaxElementLocatorFactory(Base.getDriver(),20);
        PageFactory.initElements(locatorFactory,this);

        assertTrue("Landing page not loaded", Base.getDriver().findElements(By.cssSelector("input[type*=search]")).size()>0);
    }

    /**
     * Inherited from LoadableComponent()
     */
}
