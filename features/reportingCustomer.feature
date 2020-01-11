Feature: Create a report with customer transactions

  # A reporting interface generates for a customer the list of his transactions:
    # (amount of money transferred, with which merchant, and token used) in a given time period.
  # This forms the bases of a monthly status report sent to the customer.

  Scenario: Customer tries to view his transactions
    Given the customer is registered with an account
    And the customer tries to view his transactions
    Then the customer is shown his transactions

  # Customer wants to sort/filter by merchant
  Scenario: Customer tries to view his transactions filtered by merchant
    Given the customer is registered with an account
    And the customer tries to view his transactions
    And the customer filters his transactions by merchant
    Then the customer is shown his transactions only for the specified merchant

  # Customer wants to sort/filter by date
  Scenario: Customer tries to view his transactions filtered by date
    Given the customer is registered with an account
    And the customer tries to view his transactions
    And the customer filters his transactions by date
    Then the customer is shown his transactions only for the specified date

  # Customer wants to sort/filter by transaction amount
  Scenario: Customer tries to view his transactions filtered by transaction amount
    Given the customer is registered with an account
    And the customer tries to view his transactions
    And the customer filters his transactions by transaction amount
    Then the customer is shown his transactions only for the specified transaction amount