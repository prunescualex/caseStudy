$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("src/test/resources/booking.feature");
formatter.feature({
  "name": "Script for case study",
  "description": "",
  "keyword": "Feature",
  "tags": [
    {
      "name": "@BookingPath"
    }
  ]
});
formatter.scenario({
  "name": "User makes a booking on google chrome",
  "description": "",
  "keyword": "Scenario",
  "tags": [
    {
      "name": "@BookingPath"
    },
    {
      "name": "@Chrome"
    },
    {
      "name": "@Booking"
    }
  ]
});
formatter.before({
  "status": "passed"
});
formatter.step({
  "name": "User lands on booking.com page",
  "keyword": "Given "
});
formatter.match({
  "location": "BookingDefinitions.goToLandingPage()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "User writes destination \"Munich\"",
  "keyword": "And "
});
formatter.match({
  "location": "BookingDefinitions.writeDestination(String)"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "User chooses random dates",
  "keyword": "And "
});
formatter.match({
  "location": "BookingDefinitions.chooseDates()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "User selects \"1\" \"adult\"",
  "keyword": "And "
});
formatter.match({
  "location": "BookingDefinitions.selectPersons(String,String)"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "User selects \"1\" \"child\"",
  "keyword": "And "
});
formatter.match({
  "location": "BookingDefinitions.selectPersons(String,String)"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "User selects \"2\" \"rooms\"",
  "keyword": "And "
});
formatter.match({
  "location": "BookingDefinitions.selectPersons(String,String)"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "User clicks on search",
  "keyword": "And "
});
formatter.match({
  "location": "BookingDefinitions.doSearch()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "User is redirected to search page",
  "keyword": "Then "
});
formatter.match({
  "location": "BookingDefinitions.goToSearch()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "User filters by \"budget\"",
  "keyword": "And "
});
formatter.match({
  "location": "BookingDefinitions.filterBy(String)"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "User checks first listings by \"budget\"",
  "keyword": "Then "
});
formatter.match({
  "location": "BookingDefinitions.checkFirstListings(String)"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "User filters by \"review score\"",
  "keyword": "And "
});
formatter.match({
  "location": "BookingDefinitions.filterBy(String)"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "User checks first listings by \"review\"",
  "keyword": "Then "
});
formatter.match({
  "location": "BookingDefinitions.checkFirstListings(String)"
});
formatter.result({
  "status": "passed"
});
formatter.embedding("image/png", "embedded0.png");
formatter.after({
  "status": "passed"
});
});