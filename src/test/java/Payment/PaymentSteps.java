package Payment;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import Model.Customer;
import Model.Merchant;
import Service.*;
import Control.ControlReg;
import Exception.*;
import Exception.TokenValidationException;
import Model.Token;
import dtu.ws.fastmoney.Account;
import dtu.ws.fastmoney.BankServiceException_Exception;
import io.cucumber.core.api.Scenario;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.math.BigDecimal;
import java.util.ArrayList;

public class PaymentSteps {

    private IBankService bankService;
    private ITokenManager tokenManager;
    private IPaymentService paymentService;
    private IUserService userService;
    private IReportingService reportingService;
    private String customerAccountNumber;
    private String merchantAccountNumber;
    private Customer currentCustomer;
    private Merchant currentMerchant;
    private Token currentToken;

    @Before
    public void setUp() {

        this.bankService = ControlReg.getBankService();
        this.tokenManager = ControlReg.getTokenManager();
        this.paymentService = ControlReg.getPaymentService();
        this.userService = ControlReg.getUserService();
        this.reportingService = ControlReg.getReportingService();
    }

    @Given("a customer is registered with an account balance {int}")
    public void theCustomerIsRegisteredWithAnAccountBalance(Integer balance) {
        Customer customer = new Customer();
        customer.setCprNumber("999999-2200");
        customer.setFirstName("Jane");
        customer.setLastName("Doe");
        customer.setTransactionIds(new ArrayList<>());

        this.currentCustomer = customer;

        try {
            this.customerAccountNumber = this.bankService.createAccountWithBalance(this.currentCustomer, BigDecimal.valueOf(balance));
            this.currentCustomer.setAccountId(this.customerAccountNumber);
        } catch (BankServiceException_Exception e) {
            ControlReg.getExceptionContainer().setErrorMessage(e.getMessage());
        }

        Account customerAccount = null;
        try {
            customerAccount = this.bankService.getAccount(this.customerAccountNumber);
        } catch (BankServiceException_Exception e) {
            ControlReg.getExceptionContainer().setErrorMessage(e.getMessage());
        }

        try {
            this.userService.registerCustomer(customer);
        } catch (UserAlreadyExistsException e) {
            e.printStackTrace();
        }

        assertEquals(customerAccount.getBalance(), BigDecimal.valueOf(balance));
    }

    @Given("the customer has at least {int} unused token")
    public void theCustomerHasAtLeastUnusedToken(Integer amountOfTokens) {
        try {
            this.tokenManager.generateTokens(this.currentCustomer.getCprNumber(), amountOfTokens);
        } catch (TooManyTokensException e) {
            ControlReg.getExceptionContainer().setErrorMessage(e.getMessage());
        }

        assertEquals(Integer.valueOf(this.tokenManager.getTokensByCpr(this.currentCustomer.getCprNumber()).size()), amountOfTokens);
    }

    @Given("a merchant that is registered with an account balance {int}")
    public void aMerchantThatIsRegisteredWithAnAccountBalance(Integer balance) {
        Merchant merchant = new Merchant();
        merchant.setCprNumber("112233-4455");
        merchant.setFirstName("John");
        merchant.setLastName("Doe");
        merchant.setTransactionIds(new ArrayList<>());

        this.currentMerchant = merchant;


        try {
            this.merchantAccountNumber = this.bankService.createAccountWithBalance(this.currentMerchant, BigDecimal.valueOf(balance));
            this.currentMerchant.setAccountId(this.merchantAccountNumber);
        } catch (BankServiceException_Exception e) {
            ControlReg.getExceptionContainer().setErrorMessage(e.getMessage());
        }

        Account merchantAccount = null;
        try {
            merchantAccount = this.bankService.getAccount(this.merchantAccountNumber);
        } catch (BankServiceException_Exception e) {
            ControlReg.getExceptionContainer().setErrorMessage(e.getMessage());
        }

        try {
            this.userService.registerMerchant(this.currentMerchant);
        } catch (UserAlreadyExistsException e) {
            e.printStackTrace();
        }

        assertEquals(merchantAccount.getBalance(), BigDecimal.valueOf(balance));
    }

    @When("the customer pays the merchant {int} kr")
    public void theCustomerPaysTheMerchantKr(Integer price) {
        try {
            this.currentToken = this.tokenManager.getUnusedTokensByCpr(this.currentCustomer.getCprNumber()).get(0);
            this.paymentService.performPayment(
                    this.customerAccountNumber,
                    this.merchantAccountNumber,
                    BigDecimal.valueOf(price),
                    "Testing scenario.",
                    this.currentToken);
        } catch (TokenValidationException e) {
            ControlReg.getExceptionContainer().setErrorMessage(e.getMessage());
        } catch (BankServiceException_Exception e) {
            ControlReg.getExceptionContainer().setErrorMessage(e.getMessage());
        } catch (NotEnoughMoneyException e) {
            ControlReg.getExceptionContainer().setErrorMessage(e.getMessage());
        }
    }

    @Then("the customer account balance is {int} kr")
    public void theCustomerAccountBalanceIsKr(Integer customerAccountBalance) {

        Account customerAccount = null;
        try {
            customerAccount = this.bankService.getAccount(this.customerAccountNumber);
        } catch (BankServiceException_Exception e) {
            ControlReg.getExceptionContainer().setErrorMessage(e.getMessage());
        }

        assertEquals(customerAccount.getBalance(), BigDecimal.valueOf(customerAccountBalance));
    }

    @Then("the merchant account balance is {int} kr")
    public void theMerchantAccountBalanceIsKr(Integer merchantAccountBalance) {
        Account merchantAccount = null;
        try {
            merchantAccount = this.bankService.getAccount(this.merchantAccountNumber);
        } catch (BankServiceException_Exception e) {
            ControlReg.getExceptionContainer().setErrorMessage(e.getMessage());
        }

        assertEquals(merchantAccount.getBalance(), BigDecimal.valueOf(merchantAccountBalance));
    }

    //
    //  Customer tries to pay with a fake token
    //

    @Given("a customer that is registered")
    public void aCustomerThatIsRegistered() {
        Customer customer = new Customer();
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
        assertEquals(ControlReg.getExceptionContainer().getErrorMessage(), errorMessage);
    }

    //
    //  Customer tries to pay with a used token
    //

    @When("the customer pays again {int} kr with the same token")
    public void theCustomerPaysAgainKrWithTheSameToken(Integer price) {
        try {
            this.paymentService.performPayment(
                    this.customerAccountNumber,
                    this.merchantAccountNumber,
                    BigDecimal.valueOf(price),
                    "Testing scenario.",
                    this.currentToken);
        } catch (TokenValidationException e) {
            ControlReg.getExceptionContainer().setErrorMessage(e.getMessage());
        } catch (BankServiceException_Exception e) {
            ControlReg.getExceptionContainer().setErrorMessage(e.getMessage());
        } catch (NotEnoughMoneyException e) {
            ControlReg.getExceptionContainer().setErrorMessage(e.getMessage());
        }
    }

    //
    //  Refund scenario
    //

    @Given("the customer has done a transaction with the merchant")
    public void theCustomerHasDoneATransactionWithTheMerchant() {
        try {
            this.currentToken = this.tokenManager.getUnusedTokensByCpr(this.currentCustomer.getCprNumber()).get(0);
            this.paymentService.performPayment(
                    this.customerAccountNumber,
                    this.merchantAccountNumber,
                    BigDecimal.valueOf(100),
                    "Testing scenario.",
                    this.currentToken);
        } catch (TokenValidationException e) {
            ControlReg.getExceptionContainer().setErrorMessage(e.getMessage());
        } catch (BankServiceException_Exception e) {
            ControlReg.getExceptionContainer().setErrorMessage(e.getMessage());
        } catch (NotEnoughMoneyException e) {
            ControlReg.getExceptionContainer().setErrorMessage(e.getMessage());
        }

        assertEquals(1, this.currentCustomer.getTransactionIds().size());
    }

    @When("the customer requests to refund the transaction")
    public void theCustomerRequestsToRefundTheTransaction() {
        boolean success = false;
        try {
            success = this.paymentService.performRefund(this.reportingService.getTransactionById(this.currentCustomer.getTransactionIds().get(0)));
        } catch (BankServiceException_Exception e) {
            ControlReg.getExceptionContainer().setErrorMessage(e.getMessage());
        }

        assertTrue(success);
    }

    @Then("the money is transferred back to the customer account")
    public void theMoneyIsTransferredBackToTheCustomerAccount() throws BankServiceException_Exception {
        assertEquals(BigDecimal.valueOf(200), this.bankService.getAccount(this.currentCustomer.getAccountId()).getBalance());
        assertEquals(BigDecimal.valueOf(1000), this.bankService.getAccount(this.currentMerchant.getAccountId()).getBalance());
    }

    @After
    public void tearDown(Scenario scenario) throws BankServiceException_Exception {

        System.out.println("------------------------------");
        System.out.println(scenario.getName() + " Status - " + scenario.getStatus());
        System.out.println("------------------------------");

        if (this.customerAccountNumber != null) {
            this.bankService.retireAccount(this.customerAccountNumber);
        }

        if (this.merchantAccountNumber != null) {
            this.bankService.retireAccount(this.merchantAccountNumber);
        }

        if (this.currentCustomer != null) {
            this.tokenManager.clearUserTokens(this.currentCustomer.getCprNumber());
        }
    }
}
