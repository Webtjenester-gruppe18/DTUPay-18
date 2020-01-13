Feature: Custom management features

  Scenario: A Customer request to be register
    Given the customer has a mobile device
    And the customer is not already registered
    When the customer has given name "John Doe"
    And CPR number "12345678"
    Then the customer will be registered with an account

    Scenario: A customer wants to delete account
      Given the customer has an account
      When the customer has request to delete his/her account
      Then the account will be deleted

    Scenario: A customer want to transfer more money to his/her account
      Given the customer has an account
      When the customer has picked the amount of money to transfer
      Then the money will be transfered
    
