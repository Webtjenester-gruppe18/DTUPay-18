Feature: Token Validation

  Scenario: Determine if a token is valid
    Given a token that is unused
    And the token is registered to a customer
    When a merchant scans the token
    Then the token is valid

  Scenario: Determine if a token is used
    Given a token that is used
    And the token is registered to a customer
    When a merchant scans the token
    Then the token is rejected with error message "The token is not valid."

  Scenario: Determine if a token is fake
    Given a token that is fake
    And the token is not registered to a customer
    When a merchant scans the token
    Then the token is rejected with error message "The token is not valid."
