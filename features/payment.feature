Feature: Make payment

  Scenario: Customer makes a payment
    Given the customer is registered with a account balance 1000
    And the customer has at least 1 unused token
    And A merchant that is registered with an account balance 1000
    When the customer pays the merchant 100 kr
    Then the money is transferred from the customer to merchant

  Scenario: Customer tries to pay with a used token
    Given the customer is registered with a account balance 1000
    And the customer has at least 1 unused token
    And a merchant that is registered with an account balance 1000
    When the customer pays the merchant 100 kr
    Then the money is transferred from the customer to merchant
    When the customer pays again 100kr with the same token
    Then the payment is rejected with the error message "The token is not valid."

  Scenario: Customer tries to pay with a fake token
    Given an token unknown to DTU Pay
    When the merchant uses this token for payment
    Then the payment is rejected with the error message "The token is not valid."