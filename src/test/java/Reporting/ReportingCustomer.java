package Reporting;

import Bank.IBank;
import Control.ControlReg;
import Model.Token;
import Exception.*;
import Exception.TokenValidationException;
import Service.ITokenManager;
import dtu.ws.fastmoney.*;
import io.cucumber.core.api.Scenario;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class ReportingCustomer {

    private IBank bank;
    private ITokenManager tokenManager;
    private String customerAccountNumber;
    private String merchantAccountNumber;
    private User currentCustomer;
    private User currentMerchant;
    private List<Transaction> customerTransactions;
    private Token currentToken;

    @Before
    public void setUpScenario(Scenario scenario) {
        System.out.println("------------------------------");
        System.out.println("Starting - " + scenario.getName());
        System.out.println("------------------------------");

        this.bank = ControlReg.getBank();
        this.tokenManager = ControlReg.getTokenManager();
    }

    @Before
    public void setUpAccounts() {
        // Create account for customer
        User customer = new User();
        customer.setCprNumber("101097-0202");
        customer.setFirstName("Farshad");
        customer.setLastName("Samir");

        this.currentCustomer = customer;
        this.customerAccountNumber = "e65df606-3854-4115-82b9-9e49e3c8d460";

        // Create tokens for customer account
        Integer amountOfTokens = 6;

        try {
            this.tokenManager.generateTokens(this.currentCustomer, amountOfTokens);
        } catch (TooManyTokensException e) {
            ControlReg.getExceptionContainer().setErrorMessage(e.getMessage());
        }

        // Create account for merchant
        User merchant = new User();
        merchant.setCprNumber("050597-04024");
        merchant.setFirstName("Kebabistan");
        merchant.setLastName("ApS");

        this.currentMerchant = merchant;
        this.merchantAccountNumber = "d6c8f2e1-2134-4002-b01c-0a428510882b";
    }

    /*
    @Before
    public void setUpTransactions() {
        // Create transactions
        Integer transactionA = 50;

        try {
            this.currentToken = this.tokenManager.getUnusedTokensByCpr(this.currentCustomer.getCprNumber()).get(0);
            this.bank.transferMoneyFromTo(
                    this.customerAccountNumber,
                    this.merchantAccountNumber,
                    BigDecimal.valueOf(transactionA),
                    "Menu 13 at Kebabistan",
                    this.currentToken);
        } catch (TokenValidationException e) {
            ControlReg.getExceptionContainer().setErrorMessage(e.getMessage());
        } catch (BankServiceException_Exception e) {
            ControlReg.getExceptionContainer().setErrorMessage(e.getMessage());
        }
    }
    */

    // -- Common

    @Given("the customer is registered with an account")
    public void theCustomerIsRegisteredWithAnAccount() {
        Integer startBalance = 1000;

        /*
        // Create customer account in fastmoney
        try {
            this.customerAccountNumber = this.bank.createAccountWithBalance(this.currentCustomer, BigDecimal.valueOf(startBalance));
        } catch (BankServiceException_Exception e) {
            ControlReg.getExceptionContainer().setErrorMessage(e.getMessage());
        }


        // Create merchant account in fastmoney
        try {
            this.merchantAccountNumber = this.bank.createAccountWithBalance(this.currentMerchant, BigDecimal.valueOf(startBalance));
        } catch (BankServiceException_Exception e) {
            ControlReg.getExceptionContainer().setErrorMessage(e.getMessage());
        }*/

        /*
        Account customerAccountExpected = null;

        try {
            customerAccountExpected = this.bank.getAccount(this.customerAccountNumber);
        } catch (BankServiceException_Exception e) {
            ControlReg.getExceptionContainer().setErrorMessage(e.getMessage());
        }

        assertThat(customerAccountExpected.getBalance(), is(equalTo(BigDecimal.valueOf(startBalance))));
        */
    }

    @When("the customer views his transactions")
    public void the_customer_views_his_transactions() {
        try {
            this.customerTransactions = this.bank.getAccount(this.customerAccountNumber).getTransactions();

        } catch (BankServiceException_Exception e) {
            e.printStackTrace();
        }
    }

    @Then("the customer is shown his transactions")
    public void the_customer_is_shown_his_transactions() {
        for (Transaction transaction : this.customerTransactions) {
            System.out.print("\n" + "Time: " + transaction.getTime() + " | Description: " + transaction.getDescription() + " | Debtor: " + transaction.getDebtor() + " | Creditor: " + transaction.getCreditor());
        }
    }

    // -- Merchant

    @When("the customer filters his transactions by merchant")
    public void the_customer_filters_his_transactions_by_merchant() {
        // Write code here that turns the phrase above into concrete actions
        // throw new cucumber.api.PendingException();
    }

    @Then("the customer is shown his transactions only for the specified merchant")
    public void the_customer_is_shown_his_transactions_only_for_the_specified_merchant() {
        // Write code here that turns the phrase above into concrete actions
        // throw new cucumber.api.PendingException();
    }

    // -- Date

    @When("the customer filters his transactions by date")
    public void the_customer_filters_his_transactions_by_date() {
        // Write code here that turns the phrase above into concrete actions
        // throw new cucumber.api.PendingException();
    }

    @Then("the customer is shown his transactions only for the specified date")
    public void the_customer_is_shown_his_transactions_only_for_the_specified_date() {
        // Write code here that turns the phrase above into concrete actions
        // throw new cucumber.api.PendingException();
    }

    // -- Amount

    @When("the customer filters his transactions by transaction amount")
    public void the_customer_filters_his_transactions_by_transaction_amount() {
        // Write code here that turns the phrase above into concrete actions
        // throw new cucumber.api.PendingException();
    }

    @Then("the customer is shown his transactions only for the specified transaction amount")
    public void the_customer_is_shown_his_transactions_only_for_the_specified_transaction_amount() {
        // Write code here that turns the phrase above into concrete actions
        // throw new cucumber.api.PendingException();
    }

    @After
    public void tearDown(Scenario scenario) throws BankServiceException_Exception {

        System.out.println("------------------------------");
        System.out.println(scenario.getName() + " Status - " + scenario.getStatus());
        System.out.println("------------------------------");

        if (this.customerAccountNumber != null) {
            // this.bank.retireAccount(this.customerAccountNumber);
        }

        if (this.merchantAccountNumber != null) {
            // this.bank.retireAccount(this.merchantAccountNumber);
        }

        if (this.currentCustomer != null) {
            this.tokenManager.clearUserTokens(this.currentCustomer.getCprNumber());
        }

    }

}
