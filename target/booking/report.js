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
      "name": "@MobileAndroid"
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
