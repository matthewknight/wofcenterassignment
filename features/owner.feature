Feature: Owner can submit their own details
  Scenario: Input owner details
    Given I am connected to the WOF database
    When I input the details of myself
    Then the database should contains my inputted details