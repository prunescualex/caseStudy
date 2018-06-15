package pageObjects;

import javafx.util.Pair;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import utils.Base;
import utils.DataGenerator;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertTrue;

public class SearchPage extends LoadableComponent<SearchPage> {

    private static Logger logger = LogManager.getLogger(SearchPage.class.getName());

    @FindBy(css = "div[class*=header]>div>h1")
    private WebElement searchTitle;

    @FindBy(css = "[id*=filter_price]>[class*=filteroptions]")
    private WebElement priceFilterFragment;

    @FindBy(css = "[id*=filter_review]>[class*=filteroptions]")
    private WebElement scoreFilterFragment;

    //Used for saving the chosen filters
    private int chosenScore;
    private int lowestPriceChosen;
    private int hightPriceChosen;

    /**
     * Sets a filter for prices range
     */
    public void getPricesRange(){

        Base.focusElement(priceFilterFragment);
        getFilterRange(priceFilterFragment,"price");
    }

    /**
     * Sets a filter for score review
     */
    public void getScoresRange(){
        Base.focusElement(scoreFilterFragment);
        Base.FluentWaitForElement(scoreFilterFragment);
        getFilterRange(scoreFilterFragment,"score");
    }

    public void getFilterRange(WebElement fragment, String target)
    {
        List<WebElement> filters = fragment.findElements(By.cssSelector("a>div>span:nth-child(1)"));
        int wantedFilter = DataGenerator.numberRandomGenerator(1,filters.size()-1);
        WebElement filteredElement = filters.get(wantedFilter);

        if(target.equals("price"))
        {
            Pair<Integer,Integer> pair = getNumberFromFilter(filteredElement.getAttribute("innerHTML"));
            lowestPriceChosen = pair.getKey();
            hightPriceChosen = pair.getValue();
            logger.info("The chosen filtering for prices " + lowestPriceChosen + "," + hightPriceChosen);
        }
        else
        {
            chosenScore = getNumberFromScore(filteredElement.getAttribute("innerHTML"));
            logger.info("The chosen filtering for scores " + chosenScore);
        }

        Base.clickToElement(filteredElement);
        try {
            Base.waitForAjax();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean iterateThroughListings(String target)
    {
        //Wait for the ajax calls to end
        try {
            Base.waitForAjax();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //initiate list of accomodations
        List<WebElement> listings = Base.getDriver().findElements(By.cssSelector("div[id*=hotellist_inner]>div[class*=item]"));

        String roomInformation = null;
        switch(target)
        {
            case "budget" :{
                roomInformation = "[class*=totalPrice]";
                return checkPrices(listings,roomInformation);
            }
            case "review" :
            {
               return checkListingScore(chosenScore,listings.get(0));
            }
        }
        return false;
    }

    public boolean checkPrices(List<WebElement> listings, String roomInformation)
    {
        try{
            //To avoid error -> this listing will be skipped
            String soldOutSelector = "[class*=content]>div:nth-child(2)>div[class*=sr]>div";

            for(int i=0;i<3;i++)
            {
                boolean discount = false;
                boolean badFormatting = false;

                if(listings.get(i).findElements(By.cssSelector(soldOutSelector)).size()==0) {
                    Base.highlightElement(listings.get(i));

                    WebElement currentListingPrice;
                    if(listings.get(i).findElements(By.cssSelector(roomInformation+">b")).size()>0)
                    {
                         currentListingPrice = listings.get(i).findElement(By.cssSelector(roomInformation));
                         badFormatting = true;
                    }
                    else if(listings.get(i).findElements(By.cssSelector("strong[class*=price]")).size()>0)
                    {
                        currentListingPrice = listings.get(i).findElement(By.cssSelector("strong[class*=price]"));
                        discount = true;
                    }
                    else
                        currentListingPrice = listings.get(i).findElement(By.cssSelector(roomInformation));

                    Base.focusElement(currentListingPrice);
                    Base.highlightElement(currentListingPrice);
                    String targetValue;
                    if(!discount) {
                        targetValue = currentListingPrice.getAttribute("innerHTML");
                    }
                    else if(badFormatting)
                    {
                        targetValue = currentListingPrice.findElement(By.cssSelector("b")).getAttribute("innerHTML")
                                    + currentListingPrice.findElement(By.cssSelector("div")).getAttribute("innerHTML");
                    }
                    else
                    {
                        targetValue = currentListingPrice.getAttribute("aria-label");
                    }

                    logger.info("Listing number : " + i);
                    int numberOfNights = getNumberOfNights(targetValue);
                    logger.info("Number of nights " + numberOfNights);
                    Pair<Integer,Integer> price = getNumberFromPrice(targetValue);
                    int localPrice = price.getKey();
                    logger.info("Total price " + localPrice);
                    int localPricePerNight = localPrice/numberOfNights;
                    logger.info("Price per night "  + localPricePerNight);

                    if(!checkListingPrice(lowestPriceChosen, hightPriceChosen, localPricePerNight))
                    {
                        logger.info("Price problem at listing " + i);
                        return false;
                    }
                }
                else
                {
                    logger.info("Listing number " + i + " is soldout !");
                }
            }
            return true;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            logger.info("Error at checking prices " + e.getMessage());
            return false;
        }
    }

    /**
     * Method used in extracting number of nights from a listing
     * @param value
     * @return
     */
    public int getNumberOfNights(String value){
        Pattern pattern = Pattern.compile("[0-9]* [nights]");
        Matcher matcher = pattern.matcher(value);
        matcher.find();
        String number1=  matcher.group();

        int stopPos = number1.indexOf("n");
        String finalString = number1.substring(0,stopPos-1);
        return Integer.valueOf(finalString);
    }

    /**
     * Check a given value is between two others
     * @param lowestValue - lowest value to match
     * @param highestValue - highest value to match
     * @param listingValue - value extracted from listing
     * @return true if success
     */
    public boolean checkListingPrice(int lowestValue, int highestValue, int listingValue)
    {
        if(lowestValue <= listingValue && listingValue <= highestValue)
            return true;
        else
            return false;
    }

    /**
     * The same above function overloaded to check just agains a lowest value
     * @param lowestValue - lowest value to match
     * @param element - the current listing
     * @return true if success
     */
    public boolean checkListingScore(int lowestValue, WebElement element)
    {
        String score = element.getAttribute("data-score");
        float listingValue = Float.valueOf(score);
        if(lowestValue <= listingValue)
            return true;
        else
            return false;
    }

    public int getNumberFromScore(String extractedScore){
        Pattern pattern = Pattern.compile("[0-9]");
        Matcher matcher = pattern.matcher(extractedScore);
        matcher.find();

        int number = Integer.valueOf(matcher.group());

        return number;
    }

    public Pair<Integer,Integer> getNumberFromFilter(String extractedPrice)
    {
        try {
            Pattern pattern = Pattern.compile("[0-9]+\\,?[0-9][0-9]+");
            Matcher matcher = pattern.matcher(extractedPrice);
            String number1;
            String number2;
            if (extractedPrice.contains("-")) {
                matcher.find();
                number1 = matcher.group();
                matcher.find();
                number2 = matcher.group();
                int lowest = Integer.valueOf(number1.replace(",", ""));
                int highest = Integer.valueOf(number2.replace(",", ""));
                return new Pair<>(lowest, highest);
            }
            else
                return null;
        }
        catch(Exception e)
        {
            logger.info(e.getMessage());
            return null;
        }

    }

    /**
     * Converts from integer a string extracted from a webelement as price or review score
     * @param extractedPrice integer represented the extracted number
     * @return true if success
     */
    public Pair<Integer,Integer> getNumberFromPrice(String extractedPrice)
    {
        Pattern pattern = Pattern.compile("[0-9]+\\,?[0-9][0-9]+");
        Matcher matcher = pattern.matcher(extractedPrice);
        String number1;

        try{
                matcher.find();
                number1 = matcher.group();
                int number = Integer.valueOf(number1.replace(",",""));
                return new Pair<>(number,0);
        }

        catch(Exception e)
        {
            e.printStackTrace();
            logger.info("Error at extracting int from txt" + e.getMessage());
        }
        return null;

    }


    protected void load() {

    }

    /**
     * Check is the page is properly loaded
     * @throws Error
     */
    protected void isLoaded() throws Error {
        AjaxElementLocatorFactory locatorFactory = new AjaxElementLocatorFactory(Base.getDriver(),10);
        PageFactory.initElements(locatorFactory,this);

        assertTrue("Something is wrong with search title",Base.getDriver().findElements(By.cssSelector("div[class*=header]>div>h1")).size()>0);
    }
}
