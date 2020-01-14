Feature: Create a report with customer transactions

  # A reporting interface generates for a customer the list of his transactions:
    # (amount of money transferred, with which merchant, and token used) in a given time period.
  # This forms the bases of a monthly status report sent to the customer.

  Scenario: Customer request to an overview of his transactions
  Given a registered customer with an account
  And the customer has performed atleast one transaction
  When the customer requests for an overview
  Then an overview is create with one transaction

#  Scenario: Customer tries to view his transactions filtered by merchant
#    Given the customer is registered with an account
#    When the customer views his transactions
#    And the customer filters his transactions by merchant
#    Then the customer is shown his transactions only for the specified merchant
#
#  Scenario: Customer tries to view his transactions filtered by date
#    Given the customer is registered with an account
#    When the customer views his transactions
#    And the customer filters his transactions by date
#    Then the customer is shown his transactions only for the specified date
#
#  Scenario: Customer tries to view his transactions filtered by transaction amount
#    Given the customer is registered with an account
#    When the customer views his transactions
#    And the customer filters his transactions by transaction amount
#    Then the customer is shown his transactions only for the specified transaction amount