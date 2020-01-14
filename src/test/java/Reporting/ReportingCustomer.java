package Reporting;

import Bank.IBank;
import Control.ControlReg;
import Helpers.AccountHelper;
import Helpers.DateHelper;
import Model.Token;
import Exception.*;
import Service.ITokenManager;
import dtu.ws.fastmoney.*;
import io.cucumber.core.api.Scenario;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class ReportingCustomer {

    private IBank bank;
    private ITokenManager tokenManager;
    private String customerAccountNumber;
    private String merchantAccountNumberA;
    private String merchantAccountNumberB;
    private User currentCustomer;
    private User currentMerchantA;
    private User currentMerchantB;
    private List<Transaction> customerTransactions;
    private Token currentToken;

    Integer startBalance = 1000;

    @Before
    public void setUpScenario(Scenario scenario) {
        System.out.println("------------------------------");
        System.out.println("Starting - " + scenario.getName());
        System.out.println("------------------------------");

        this.bank = ControlReg.getBank();
        this.tokenManager = ControlReg.getTokenManager();
    }

    @Before
    public void setUpCustomerAccounts() {
        // Create account for customer
        User customer = new User();
        customer.setCprNumber("101097-0202");
        customer.setFirstName("Farshad");
        customer.setLastName("Samir");

        this.currentCustomer = customer;

        /*
        // Remove cusotmer account in case it exists
        try {
            this.bank.retireAccount(this.currentCustomer.getCprNumber());
        } catch (BankServiceException_Exception e) {
            e.printStackTrace();
        }*/

        // Create customer account in fastmoney
        try {
            this.customerAccountNumber = this.bank.createAccountWithBalance(this.currentCustomer, BigDecimal.valueOf(startBalance));
        } catch (BankServiceException_Exception e) {
            ControlReg.getExceptionContainer().setErrorMessage(e.getMessage());
        }

        // Create tokens for customer account
        Integer amountOfTokens = 6;

        try {
            this.tokenManager.generateTokens(this.currentCustomer, amountOfTokens);
        } catch (TooManyTokensException e) {
            ControlReg.getExceptionContainer().setErrorMessage(e.getMessage());
        }
    }

    @Before
    public void setUpMerchantAccounts() {
        // Create account for merchant
        User merchantA = new User();
        merchantA.setCprNumber("050597-04024");
        merchantA.setFirstName("Kebabistan");
        merchantA.setLastName("ApS");

        User merchantB = new User();
        merchantB.setCprNumber("020297-06026");
        merchantB.setFirstName("Alis Bageri");
        merchantB.setLastName("ApS");

        this.currentMerchantA = merchantA;
        this.currentMerchantB = merchantB;

        /*
        // Remove merchant A and B account in case it exists
        try {
            this.bank.retireAccount(this.currentMerchantA.getCprNumber());
            this.bank.retireAccount(this.currentMerchantB.getCprNumber());
        } catch (BankServiceException_Exception e) {
            e.printStackTrace();
        }*/

        // Create merchant A account in fastmoney
        try {
            this.merchantAccountNumberA = this.bank.createAccountWithBalance(this.currentMerchantA, BigDecimal.valueOf(startBalance));
        } catch (BankServiceException_Exception e) {
            ControlReg.getExceptionContainer().setErrorMessage(e.getMessage());
        }

        // Create merchant B account in fastmoney
        try {
            this.merchantAccountNumberB = this.bank.createAccountWithBalance(this.currentMerchantB, BigDecimal.valueOf(startBalance));
        } catch (BankServiceException_Exception e) {
            ControlReg.getExceptionContainer().setErrorMessage(e.getMessage());
        }
    }

    @Before
    public void setUpTransactions() {
        // Create transaction for Merchant A
        Integer transactionA = 50;

        try {
            this.currentToken = this.tokenManager.getUnusedTokensByCpr(this.currentCustomer.getCprNumber()).get(0);
            this.bank.transferMoneyFromTo(
                    this.customerAccountNumber,
                    this.merchantAccountNumberA,
                    BigDecimal.valueOf(transactionA),
                    "Menu 13 at Kebabistan",
                    this.currentToken);
        } catch (TokenValidationException e) {
            ControlReg.getExceptionContainer().setErrorMessage(e.getMessage());
        } catch (BankServiceException_Exception e) {
            ControlReg.getExceptionContainer().setErrorMessage(e.getMessage());
        }

        // Create transaction for Merchant B
        Integer transactionB = 20;

        try {
            this.currentToken = this.tokenManager.getUnusedTokensByCpr(this.currentCustomer.getCprNumber()).get(0);
            this.bank.transferMoneyFromTo(
                    this.customerAccountNumber,
                    this.merchantAccountNumberB,
                    BigDecimal.valueOf(transactionB),
                    "2x Arabisk bread at Alis Bageri",
                    this.currentToken);
        } catch (TokenValidationException e) {
            ControlReg.getExceptionContainer().setErrorMessage(e.getMessage());
        } catch (BankServiceException_Exception e) {
            ControlReg.getExceptionContainer().setErrorMessage(e.getMessage());
        }
    }

    // -- Common

    @Given("the customer is registered with an account")
    public void theCustomerIsRegisteredWithAnAccount() {
        Integer startBalance = 1000;

        Account customerAccountExpected = null;

        try {
            customerAccountExpected = this.bank.getAccount(this.customerAccountNumber);
        } catch (BankServiceException_Exception e) {
            ControlReg.getExceptionContainer().setErrorMessage(e.getMessage());
        }

        //assertThat(customerAccountExpected.getBalance(), is(equalTo(BigDecimal.valueOf(startBalance))));
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
            System.out.print("Time: " + DateHelper.formatUniversalDate(transaction.getTime()) + " | Amount: " + transaction.getAmount() + " | Description: " + transaction.getDescription() + " | Debtor: " + transaction.getDebtor() + " | Creditor: " + AccountHelper.getAccountFirstName(transaction.getCreditor(), this.bank) + "\n");
        }
    }

    // -- Merchant

    @When("the customer filters his transactions by merchant")
    public void the_customer_filters_his_transactions_by_merchant() {
        String filterByMerchant = "Kebabistan"; // Kebabistan accountNumber: d6c8f2e1-2134-4002-b01c-0a428510882b

        //assertThat(this.customerTransactions, containsInAnyOrder(filterByMerchant));
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
        Integer filterByAmount = 50;

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
            this.bank.retireAccount(this.customerAccountNumber);
        }

        if (this.merchantAccountNumberA != null) {
            this.bank.retireAccount(this.merchantAccountNumberA);
        }

        if (this.merchantAccountNumberB != null) {
            this.bank.retireAccount(this.merchantAccountNumberB);
        }

        if (this.currentCustomer != null) {
            this.tokenManager.clearUserTokens(this.currentCustomer.getCprNumber());
        }

    }

}
