Feature: Create a report with merchant transactions

  # A reporting interface generates for a merchant the list of his transactions (amount of money transfered and token used) in a time period.
  # This forms the bases of a monthly statatus report sent to the customer.
  # To model a semi-anonymous payment system, the merchant should not know who the customer was.

  Scenario: Merchant generates list of transactions in a given time
    Given the merchant has a list of transactions
    And the customer has made a transaction
    When the merchant generates a status report
    Then the customer receives a reporting of his transaction

