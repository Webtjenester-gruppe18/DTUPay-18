Feature: Token Validation

  Scenario: Determine if a token is valid
    Given a token that is unused
    And not fake
    When a merchant scans the token
    Then the token is valid

  Scenario: Determine if a token is used
    Given a token that is used
    When a merchant scans the token
    Then the token is rejected

  Scenario: Determine if a token is fake
    Given a token that is fake
    When a merchant scans the token
    Then the token is rejected
