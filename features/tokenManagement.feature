Feature: Token Management Features

  Scenario: A customer request for new tokens
    Given the customer is registered
    And the customer has not more than 1 unused token left
    When the customer requests more tokens
    Then the customer receives 5 new unused tokens
    And then has 6 unused tokens

#  Scenario: A customer request for new tokens but have more than 1 unused token
#    Given the customer is registered
#    And the customer have 4 unused token left
#    When the customer request more tokens
#    Then the customer gets a error message saying "You have too many tokens to get new ones."

