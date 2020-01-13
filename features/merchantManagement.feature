Feature: merchant management

  Scenario: merchant transfer token to money
    Given that the merchant has a bank acount
    And that the merchant has a unused token
    When the merchant trade the token
    Then the merchantrecive money on account

  Scenario: merchant create bank acount
    Given that the merchant has a name
    And A lastname
    And A cpr
    When the merchant gives the information
    Then the merchant has created a bank acount