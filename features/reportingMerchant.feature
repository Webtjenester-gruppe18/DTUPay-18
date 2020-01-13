Feature: Merchant management

  Scenario: Merchant requests an overview of his transactions
    Given a merchant that is registered in DTU Pay
    And the merchant has performed at least 1 transaction
    When the merchant requests an overview of his transactions
    Then the merchant gets an overview with 1 transaction

  Scenario: Merchant does not have any transactions, and requests an overview
    Given a merchant that is registered in DTU Pay
    And the merchant has not performed a transaction
    When the merchant requests an overview of his transactions
    Then the merchants request is rejected with a error message "You have not performed any transactions."

