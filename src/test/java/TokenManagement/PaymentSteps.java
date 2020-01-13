package TokenManagement;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import Bank.IBank;
import Control.ControlReg;
import Exception.*;
import Exception.TokenValidationException;
import Model.Token;
import Service.ITokenManager;
import dtu.ws.fastmoney.Account;
import dtu.ws.fastmoney.BankServiceException_Exception;
import dtu.ws.fastmoney.User;
import io.cucumber.core.api.Scenario;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.math.BigDecimal;

public class PaymentSteps {

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

    @Given("the customer is registered with an account balance {int}")
    public void theCustomerIsRegisteredWithAnAccountBalance(Integer balance) {
        User customer = new User();
        customer.setCprNumber("999999-2200");
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

    @Given("a merchant that is registered with an account balance {int}")
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
            this.currentToken = this.tokenManager.getUnusedTokensByCpr(this.currentCustomer.getCprNumber()).get(0);
            this.bank.transferMoneyFromTo(
                    this.customerAccountNumber,
                    this.merchantAccountNumber,
                    BigDecimal.valueOf(price),
                    "Testing scenario.",
                    this.currentToken);
        } catch (TokenValidationException e) {
            ControlReg.getExceptionContainer().setErrorMessage(e.getMessage());
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
            this.tokenManager.validateToken(this.currentCustomer.getCprNumber(), this.currentToken);
        } catch (TokenValidationException e) {
            ControlReg.getExceptionContainer().setErrorMessage(e.getMessage());
        }
    }

    @Then("the payment is rejected with the error message {string}")
    public void thePaymentIsRejectedWithTheErrorMessage(String errorMessage) {
        assertThat(ControlReg.getExceptionContainer().getErrorMessage(), is(equalTo(errorMessage)));
    }

    //
    //  Customer tries to pay with a used token
    //

    @When("the customer pays again {int} kr with the same token")
    public void theCustomerPaysAgainKrWithTheSameToken(Integer price) {
        try {
            this.bank.transferMoneyFromTo(
                    this.customerAccountNumber,
                    this.merchantAccountNumber,
                    BigDecimal.valueOf(price),
                    "Testing scenario.",
                    this.currentToken);
        } catch (TokenValidationException e) {
            ControlReg.getExceptionContainer().setErrorMessage(e.getMessage());
        } catch (BankServiceException_Exception e) {
            ControlReg.getExceptionContainer().setErrorMessage(e.getMessage());
        }
    }


    @After
    public void tearDown(Scenario scenario) throws BankServiceException_Exception {

        System.out.println("------------------------------");
        System.out.println(scenario.getName() + " Status - " + scenario.getStatus());
        System.out.println("------------------------------");

        if (this.customerAccountNumber != null) {
            this.bank.retireAccount(this.customerAccountNumber);
        }

        if (this.merchantAccountNumber != null) {
            this.bank.retireAccount(this.merchantAccountNumber);
        }

        if (this.currentCustomer != null) {
            this.tokenManager.clearUserTokens(this.currentCustomer.getCprNumber());
        }
    }
}
