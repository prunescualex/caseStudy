
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)

@CucumberOptions(
        features = {"src/test/resources/booking.feature"},
        format = {"pretty","html:target/booking","json:target/booking.json"},
        glue = {"annotation"}, tags = {"@BookingPath"}
)

public class TestBooking {

}
