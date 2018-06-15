package annotation;

import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import pageObjects.LandingPage;
import pageObjects.SearchPage;
import utils.Base;

import static junit.framework.TestCase.assertTrue;

public class BookingDefinitions {

    private LandingPage landingPage;
    private SearchPage searchPage;


    @Given("^User lands on booking.com page$")
    public void goToLandingPage(){
        landingPage = PageFactory.initElements(Base.getDriver(),LandingPage.class).get();
    }

    @And("^User writes destination \"(.*)\"$")
    public void writeDestination(String arg)
    {
        assertTrue("Cannot write destinatnion",landingPage.writeDestination(arg));
    }

    @And("^User chooses random dates$")
    public void chooseDates()
    {
        landingPage.selectDateCheckin();
        landingPage.selectDateCheckout();
    }

    @And("^User selects \"(.*)\" \"(.*)\"$")
    public void selectPersons(String arg1, String arg2){

        switch(arg2)
        {
            case "adult" : landingPage.selectAdults(arg1);break;
            case "child" :
                landingPage.selectChildren(arg1);
                landingPage.setChildrenAge();
                break;
            case "rooms" : landingPage.selectRooms(arg1);break;
        }
    }

    @And("^User clicks on search$")
    public void doSearch()
    {
        assertTrue("Not possible to click on search",landingPage.clickSearch());
    }

    @Then("^User is redirected to search page$")
    public void goToSearch()
    {
        searchPage = PageFactory.initElements(Base.getDriver(),SearchPage.class).get();
    }

    @And("^User filters by \"(.*)\"$")
    public void filterBy(String arg) {
        switch (arg) {
            case "budget":
                searchPage.getPricesRange();
                break;
            case "review score":
                searchPage.getScoresRange();
                break;
        }
    }

    @And("^User checks first listings by \"(.*)\"$")
    public void checkFirstListings(String arg)
    {

        assertTrue("Something wrong on iterating "+ arg +" listings" , searchPage.iterateThroughListings(arg));

    }
}
