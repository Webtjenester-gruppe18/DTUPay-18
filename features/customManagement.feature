Feature: Custom management features

  Scenario: A Customer request to be register
    Given the customer is not already registered
    And with first name is "John"
    And with last name is "Doe"
    And with CPR number "12345678"
    When the customer registers
    Then the account is created

  Scenario: A customer wants to delete account
    Given the customer has an account
    When the customer has request to delete his/her account
    Then the account will be deleted

  Scenario: A customer want to transfer more money to his/her account
    Given the customer has an account
    When the customer has picked the amount of money to transfer
    Then the money will be transfered
    
