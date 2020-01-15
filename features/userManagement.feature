Feature: User management feature

  Scenario: A customer request to be register
    Given a customer is not already registered
    And with first name is "John"
    And with last name is "Doe"
    And with CPR number "12345678"
    When the customer registers
    Then the account is created and registered

  Scenario: A customer try to make an extra account
    Given a customer is not already registered
    And with first name is "John"
    And with last name is "Doe"
    And with CPR number "12345678"
    When the customer registers
    Then the account is created and registered
    When the customer tries to register with the same credentials
    Then the customer will be rejected with the error message "This user already exists in the database."

  Scenario: A customer wants to delete account
    Given a customer has an account
    When the customer requests to delete the account
    Then the customer account is deleted

  Scenario: A customer adds money to the account
    Given a customer that has an account with balance of 100 kr
    When the customer adds 200 kr to the account
    Then the money will be added to the account
    And the account balance is 300 kr

  Scenario: merchant create bank account
    Given a merchant that is not already registered
    And with first name is "Johan"
    And with last name is "Bob"
    And with CPR number "100283-0013"
    When the merchant tries to register
    Then the merchant has created a bank account with the credentials given

  Scenario: a merchant try to make an extra account
    Given a merchant that is not already registered
    And with first name is "Johan"
    And with last name is "Bob"
    And with CPR number "100283-0013"
    When the merchant tries to register
    Then the merchant has created a bank account with the credentials given
    When the merchant tries to register with the same credentials
    Then the merchant will be rejected with the error message "This user already exists in the database."

  Scenario: A merchant wants to delete account
    Given the merchant has an account
    When the merchant requests to delete the account
    Then the merchant account is deleted

