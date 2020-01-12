package TokenManagement;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import Bank.IBank;
import Control.ControlReg;
import Exception.*;
import Database.IDatabase;
import Exception.TokenValidationException;
import Model.Customer;
import Model.Merchant;
import Model.Token;
import Service.ITokenManager;
import Service.TokenService;
import Service.ValidationService;
import dtu.ws.fastmoney.Account;
import dtu.ws.fastmoney.BankServiceException_Exception;
import dtu.ws.fastmoney.User;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.After;

import java.math.BigDecimal;
import java.util.ArrayList;

public class TokenPaymentSteps {

    private IBank bank;
    private ITokenManager tokenManager;
    private String customerAccountNumber;
    private String merchantAccountNumber;
    private User currentCustomer;
    private User currentMerchant;
    private Token currentToken;

    @Before
    public void setUp() {

        this.bank = ControlReg.getBank();
        this.tokenManager = ControlReg.getTokenManager();
    }

    @Given("the customer is registered with a account balance {int}")
    public void theCustomerIsRegisteredWithAAccountBalance(Integer balance) {
        User customer = new User();
        customer.setCprNumber("991199-2200");
        customer.setFirstName("Jane");
        customer.setLastName("Doe");

        this.currentCustomer = customer;

        try {
            this.customerAccountNumber = this.bank.createAccountWithBalance(this.currentCustomer, BigDecimal.valueOf(balance));
        } catch (BankServiceException_Exception e) {
            ControlReg.getExceptionContainer().setErrorMessage(e.getMessage());
        }

        Account customerAccount = null;
        try {
            customerAccount = this.bank.getAccount(this.customerAccountNumber);
        } catch (BankServiceException_Exception e) {
            ControlReg.getExceptionContainer().setErrorMessage(e.getMessage());
        }

        assertThat(customerAccount.getBalance(), is(equalTo(BigDecimal.valueOf(balance))));
    }

    @Given("the customer has at least {int} unused token")
    public void theCustomerHasAtLeastUnusedToken(Integer amountOfTokens) {
        try {
            this.tokenManager.generateTokens(this.currentCustomer, amountOfTokens);
        } catch (TooManyTokensException e) {
            ControlReg.getExceptionContainer().setErrorMessage(e.getMessage());
        }

        assertThat(this.tokenManager.getTokensByCpr(this.currentCustomer.getCprNumber()).size(), is(equalTo(amountOfTokens)));
    }

    @Given("A merchant that is registered with an account balance {int}")
    public void aMerchantThatIsRegisteredWithAnAccountBalance(Integer balance) {
        User merchant = new User();
        merchant.setCprNumber("112233-4455");
        merchant.setFirstName("John");
        merchant.setLastName("Doe");

        this.currentMerchant = merchant;

        try {
            this.merchantAccountNumber = this.bank.createAccountWithBalance(this.currentMerchant, BigDecimal.valueOf(balance));
        } catch (BankServiceException_Exception e) {
            ControlReg.getExceptionContainer().setErrorMessage(e.getMessage());
        }

        Account customerAccount = null;
        try {
            customerAccount = this.bank.getAccount(this.merchantAccountNumber);
        } catch (BankServiceException_Exception e) {
            ControlReg.getExceptionContainer().setErrorMessage(e.getMessage());
        }

        assertThat(customerAccount.getBalance(), is(equalTo(BigDecimal.valueOf(balance))));
    }

    @When("the customer pays the merchant {int} kr")
    public void theCustomerPaysTheMerchantKr(Integer price) {
        try {
            this.bank.transferMoneyFromTo(this.customerAccountNumber, this.merchantAccountNumber, BigDecimal.valueOf(price), "Testing scenario.");
        } catch (BankServiceException_Exception e) {
            ControlReg.getExceptionContainer().setErrorMessage(e.getMessage());
        }
    }

    @Then("the customer account balance is {int} kr")
    public void theCustomerAccountBalanceIsKr(Integer customerAccountBalance) {

        Account customerAccount = null;
        try {
            customerAccount = this.bank.getAccount(this.customerAccountNumber);
        } catch (BankServiceException_Exception e) {
            ControlReg.getExceptionContainer().setErrorMessage(e.getMessage());
        }

        assertThat(customerAccount.getBalance(), is(equalTo(BigDecimal.valueOf(customerAccountBalance))));
    }

    @Then("the merchant account balance is {int} kr")
    public void theMerchantAccountBalanceIsKr(Integer merchantAccountBalance) {
        Account merchantAccount = null;
        try {
            merchantAccount = this.bank.getAccount(this.merchantAccountNumber);
        } catch (BankServiceException_Exception e) {
            ControlReg.getExceptionContainer().setErrorMessage(e.getMessage());
        }

        assertThat(merchantAccount.getBalance(), is(equalTo(BigDecimal.valueOf(merchantAccountBalance))));
    }

    @After
    public void tearDown() throws BankServiceException_Exception {
        // clear user account
        Account account = this.bank.getAccountByCpr(this.currentCustomer.getCprNumber());
        this.bank.retireAccount(account.getId());

        // clear user tokens
        this.tokenManager.clearUserTokens(this.currentCustomer.getCprNumber());
    }


    //
    //  Customer tries to pay with a fake token
    //

    @Given("a customer that is registered")
    public void aCustomerThatIsRegistered() {
        User customer = new User();
        customer.setCprNumber("991199-2200");
        customer.setFirstName("Jane");
        customer.setLastName("Doe");

        this.currentCustomer = customer;
    }

    @Given("an token unknown to DTU Pay")
    public void anTokenUnknownToDTUPay() {
        this.currentToken = new Token("000000-0000");
    }

    @When("the merchant uses this token for payment")
    public void theMerchantUsesThisTokenForPayment() {
        try {
            this.tokenManager.validateToken(this.currentCustomer, this.currentToken);
        } catch (TokenValidationException e) {
            ControlReg.getExceptionContainer().setErrorMessage(e.getMessage());
        }
    }

    @Then("the payment is rejected with the error message {string}")
    public void thePaymentIsRejectedWithTheErrorMessage(String errorMessage) {
        assertThat(ControlReg.getExceptionContainer().getErrorMessage(), is(equalTo(errorMessage)));
    }
}
