#### Script Test Plan

### Test Name
* Booking test
-------------------
### Purpose 
* Testing the searching capabilities of the www.booking.com platform
  with different search parameters and checking if the resulting search
  will perform as expected after applying filtering options.
-------------------
### Strategy
* Firstly Exploratory testing was conducted in order to observe the 
  platform's behaviour. Toghether with the provided scenario, and comparing
  with the previous steps an artchitecture was built that would allow fast
  changes and a rapid devlopment of the tests.

-------------------
### Setup
* The test is written in java & selenium suited in a maven build.
  On top of them JUnit and Cucumber were used in order to create a more
  efficient architecture. Also Log4j was used for logging.
  
  
-------------------
### Folders & Structure
* annotations - /src/java/annotation - here are all the step definitions for scenarios defined in the feature file
* pageObjects - /src/java/pageObjects - objects representing the implementation of the web pages used in tests
* utils - /src/java/utils - all the helper classes used by tests.
* tests - /test/java - test runner linking the scenario with the step definitions files
* resources - /test/resources - the feature file where scenarios are written in gherkin syntax

-------------------
### How to run ?
1. Git clone the repository
2. Go to the project location .. cd /framework
3. Build with Maven  mvn clean test -Dtags='@BookingPath'


-------------------
### Recovery
* In the case of test failure then run it again and only if the second test fail 
 consider this test as failure. If it fails to tear down the fixture to it root …


-------------------
### Version Matrix
* This test is built to run on chrome and firefox independent of version. It can
  also support other browsers/ mobile emulation considering the required driver
  implementation is offered


-------------------
### General Parameters
* Just 1 parameter exposed to the user from the maven command line
 -Dtags - representing the tags that will run at the execution fo the command.
 @BookingPath executes the scenario for both Chrome and Firefox
 @Chrome wil run the scenario just on Chrome
 @Firefox will run the scenario just on Firefox


-------------------
### Steps

-------------------
