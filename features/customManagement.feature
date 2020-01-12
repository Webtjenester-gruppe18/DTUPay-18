Feature: Custom management features

  Scenario: A Customer request to be register
    Given the customer has a mobile device
    And the customer is not already registered
    Then the customer will be registered with an account
    
