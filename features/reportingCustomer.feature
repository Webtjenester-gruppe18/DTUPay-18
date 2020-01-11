Feature: Create a report with customer transactions

  # A reporting interface generates for a customer the list of his transactions (amount of money transferred, with which merchant, and token used) in a given time period.
  # This forms the bases of a monthly status report sent to the customer.

  Scenario: Customer tries to view his transactions
    Given the customer is registered with an account
    And the customer tries to view his transactions
    Then the customer is shown his transactions