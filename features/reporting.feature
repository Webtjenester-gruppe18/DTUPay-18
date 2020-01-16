Feature: Reporting feature

  # A reporting interface generates for a customer the list of his transactions:
    # (amount of money transferred, with which merchant, and token used) in a given time period.
  # This forms the bases of a monthly status report sent to the customer.

  Scenario: Customer requests an overview of his transactions
    Given a registered customer with an account
    And the customer has performed atleast one transaction
    When the customer requests for an overview
    Then an overview is create with one transaction

  Scenario: Customer requests an overview of his transactions the last month
    Given a registered customer with an account
    And the customer has performed atleast one transaction in the last month
    When the customer requests for an monthly overview
    Then an overview is create with one transaction

  Scenario: Merchant requests an overview of his transaction
    Given a registered merchant with an account
    And the merchant has performed atleast one transaction
    When the merchant requests for an transaction overview
    Then an merchant transaction overview is created

  Scenario: Merchant request an monthly transaction report
    Given a registered merchant with an account
    And the merchant has performed one transaction in the last month
    When the merchant requests for an monthly overview
    Then an monthly merchant transaction report is created
