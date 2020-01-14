Feature: Token Management Features

  Scenario: A customer request for new tokens
    Given the customer is registered
    And the customer has no more than 1 unused token left
    When the customer requests more tokens
    Then the customer receives 5 new unused tokens
    And then has 6 unused tokens

  Scenario: A customer requests for new tokens but has more than 1 unused token
    Given the customer is registered
    And the customer has atleast 2 unused token left
    When the customer requests more tokens
    Then the customer gets a error message saying "The user has too many token to request for new ones."
