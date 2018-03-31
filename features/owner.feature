Feature: Owner can submit their own details
  Scenario: Input new owner details
    Given I am connected to the empty WOF database
    When I input the details of myself
    Then the database should contains my inputted details

  Scenario: Input existing owner details
    Given I am connected to the empty WOF database
    And I input the details of myself
    When I input my details with the same email again
    Then the system should reject my repeated details