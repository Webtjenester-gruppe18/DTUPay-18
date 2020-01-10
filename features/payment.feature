Feature: Make payment

  Scenario: Customer makes a payment
    Given the customer is registered with a account balance 1000
    And the customer have at least 1 unused token
    And A merchant that is registered with a account balance 1000
    When the customer pays the merchant 100 kr
    Then the money is transferred from the customer to merchant

  Scenario: Customer tries to pay with a used token
    Given the customer is registered with a account balance 1000
    And the customer have at least 1 used token
    And A merchant that is registered with a account balance 1000
    When the customer pay 100 with a used token
    Then the payment is rejected with the error message "The token is not valid."

  Scenario: Customer tries to pay with a fake token
    Given the customer is registered with a account balance 1000
    And a token not attached to the customer
    And A merchant that is registered with a account balance 1000
    When the customer pay 100 with a fake token
    Then the payment is rejected with the error message "The token is not valid."