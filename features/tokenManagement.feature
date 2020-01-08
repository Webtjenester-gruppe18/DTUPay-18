Feature: Token Management Features

  Scenario: A customer request for new tokens
    Given the customer is registered
    And the customer have 1 unused token left
    When the customer request more tokens
    Then the customer receive 5 unused tokens