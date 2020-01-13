Feature: merchant management

  Scenario: merchant create bank acount
    Given that the merchant has a name "Johan"
    And A lastname "bob"
    And A cpr "100283-0013"
    When the merchant tries to register
    Then the merchant has created a bank acount with the credentials given

  Scenario: merchant create bank acount with a cpr already in use
    Given that the merchant cpr already in use.
    When the merchant tries to register
    Then the merchant will recive error message "Cpr already in use"
    And the acount shouldn't be created

  Scenario: merchant want to delete bank account
    Given that the merchant has a name "Johan"
    And A lastname "bob"
    And A cpr "100283-0013"
    When the merchant tries to delete
    Then the merchant has delete the bank acount with the credentials given