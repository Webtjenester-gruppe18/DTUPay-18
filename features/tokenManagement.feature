Feature: Token Management Features

  Scenario: A customer request for new tokens
    Given the customer is registered
    And the customer have 1 unused token left
    When the customer request more tokens
    Then the service create 5 new unused tokens
    Then the customer receive the tokens, and have 6 unused tokens

  Scenario: A customer request for new tokens but have more than 1 unused token
    Given the customer is registered
    And the customer have 4 unused token left
    When the customer request more tokens
    Then the customer gets a error message saying "You have too many tokens to get new ones."

  Scenario: Validate a valid token
    Given A valid token
    When the validation is processing
    Then the result is "true"

  Scenario: Validate a invalid token
    Given A invalid token
    When the validation is processing
    Then a errormessage is presented "The token is not valid."

  Scenario: Validate a fake token
    Given A fake token
    When the validation is processing
    Then a errormessage is presented "The token is not valid."

  Scenario: Customer use a token
    Given the customer is registered
    And the customer have 4 unused token left
    When the customer use a token
    Then the customer gets the token removes