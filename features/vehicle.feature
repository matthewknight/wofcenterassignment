Feature: Input a vehicle's details
  Scenario: Input vehicle details
    Given I am connected to the WOF database
    When I input the details of my vehicle
    Then the database should contains the details of my vehicle