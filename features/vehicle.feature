Feature: Input a vehicle's details
  Scenario: Input vehicle details
    Given I have connected to the empty WOF database
    And I have already registered as a user
    When I input the details of my vehicle
    Then the database should contains the details of my vehicle

  Scenario: Input vehicle details without registering owner
    Given I have connected to the empty WOF database
    When I input the details of my vehicle with a non registered email
    Then the database should reject my new vehicle

  Scenario: Input identical vehicle details twice
    Given I have connected to the empty WOF database
    And I have already registered as a user
    And I input the details of my vehicle
    When I input the details of my vehicle again
    Then the database should reject my new vehicle