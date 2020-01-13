Feature: User management feature

  Scenario: A customer request to be register
    Given the customer is not already registered
    And with first name is "John"
    And with last name is "Doe"
    And with CPR number "12345678"
    When the customer registers
    Then the account is created

  Scenario: a customer try to make an extra account
    Given the customer is not already registered
    And with first name is "John"
    And with last name is "Doe"
    And with CPR number "12345678"
    When the customer registers
    Then the account is created
    When the customer tries to register with the same credentials
    Then the customer will be rejected with the error message "only one account pr. customer"

  Scenario: A customer wants to delete account
    Given the customer has an account
    When the customer has request to delete the account
    Then the account will be deleted

  Scenario: A customer want to add more money to the account
    Given the customer has an account with balance of 100 kr
    When the customer adds 200 kr to the account
    Then the money will be added to the account
    And the account balance is 300 kr

  Scenario: merchant create bank account
    Given that the merchant has a name "Johan"
    And A last name "bob"
    And A cpr "100283-0013"
    When the merchant tries to register
    Then the merchant has created a bank account with the credentials given

  Scenario: a merchant try to make an extra account
    Given the merchant is not already registered
    And with first name is "John"
    And with last name is "Doe"
    And with CPR number "12345678"
    When the merchant registers
    Then the account is created
    When the merchant tries to register with the same credentials
    Then the merchant will be rejected with the error message "only one account pr. merchant"

  Scenario: A merchant wants to delete account
    Given the merchant has an account
    When the merchant has request to delete the account
    Then the account will be deleted

