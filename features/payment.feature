Feature: Make payment

  Scenario: Customer makes a payment
    Given the customer is registered with an account balance 1000
    And the customer has at least 1 unused token
    And a merchant that is registered with an account balance 1000
    When the customer pays the merchant 100 kr
    Then the customer account balance is 900 kr
    And the merchant account balance is 1100 kr

  Scenario: Customer tries to pay with a used token
    Given the customer is registered with an account balance 1000
    And the customer has at least 1 unused token
    And a merchant that is registered with an account balance 1000
    When the customer pays the merchant 100 kr
    Then the customer account balance is 900 kr
    And the merchant account balance is 1100 kr
    When the customer pays again 100 kr with the same token
    Then the payment is rejected with the error message "The token is not valid."

  Scenario: Customer tries to pay with a fake token
    Given a customer that is registered
    And an token unknown to DTU Pay
    When the merchant uses this token for payment
    Then the payment is rejected with the error message "The token is not valid."

  Scenario: Customer tries to pay but don't have enough money on the account
    Given the customer is registered with an account balance 200
    And the customer has at least 1 unused token
    When the customer pays the merchant 500 kr
    Then the payment is rejected with the error message "You have not enough money."