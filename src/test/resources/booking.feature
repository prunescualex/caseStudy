@BookingPath
Feature: Script for case study

  @Firefox @Booking
  Scenario: User makes a booking on google chrome
    Given User lands on booking.com page
    And User writes destination "Munich"
    And User chooses random dates
    And User selects "1" "adult"
    And User selects "1" "child"
    And User selects "2" "rooms"
    And User clicks on search
    Then User is redirected to search page
    And User filters by "budget"
    Then User checks first listings by "budget"
    And User filters by "review score"
    Then User checks first listings by "review"


  @Chrome @Booking
  Scenario: User makes a booking on google chrome
    Given User lands on booking.com page
    And User writes destination "Munich"
    And User chooses random dates
    And User selects "1" "adult"
    And User selects "1" "child"
    And User selects "2" "rooms"
    And User clicks on search
    Then User is redirected to search page
    And User filters by "budget"
    Then User checks first listings by "budget"
    And User filters by "review score"
    Then User checks first listings by "review"