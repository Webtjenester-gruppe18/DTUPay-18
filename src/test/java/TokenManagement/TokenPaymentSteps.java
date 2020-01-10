package TokenManagement;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import Control.ControlReg;
import Database.InMemoryDatabase;
import Exception.*;
import Database.IDatabase;
import Exception.TokenValidationException;
import Model.Customer;
import Model.Merchant;
import Model.Token;
import Service.TokenService;
import Service.ValidationService;
import cucumber.api.PendingException;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.ArrayList;

public class TokenPaymentSteps {

    private IDatabase database;
    private TokenService tokenService;
    private Customer currentCustomer;
    private Merchant currentMerchant;
    private ArrayList<Token> requestedTokens;
    private ExceptionContainer exceptionContainer;
    private Token paymentToken;
    private ValidationService validationService;
    private double paymentPrice;
    private double customerPreviousBalance;
    private double merchantPreviousBalance;
    @Before
    public void setUp() {
        this.database = ControlReg.getDatabase();
        this.tokenService = ControlReg.getTokenService();
        this.validationService = ControlReg.getValidationService();
        this.requestedTokens = new ArrayList<>();
        this.exceptionContainer = new ExceptionContainer();
    }

    @Given("the customer is registered with a account balance {int}")
    public void theCustomerIsRegisteredWithAAccountBalance(Integer balance) {
        this.currentCustomer = new Customer("John Doe");
        this.customerPreviousBalance = balance;
        this.currentCustomer.getAccount().setBalance(balance);
        this.database.addCustomer(this.currentCustomer);

        assertThat(this.database.getCustomer(this.currentCustomer.getId()), is(equalTo(this.currentCustomer)));
    }

    @Given("A merchant that is registered with a account balance {int}")
    public void aMerchantThatIsRegisteredWithAAccountBalance(Integer balance) {
        this.currentMerchant = new Merchant("Jane Doe");
        this.merchantPreviousBalance = balance;
        this.currentMerchant.getAccount().setBalance(balance);
        this.database.addMerchant(this.currentMerchant);

        assertThat(this.database.getMerchant(this.currentMerchant.getId()), is(equalTo(this.currentMerchant)));
    }

    @Given("the customer have at least {int} unused token")
    public void theCustomerHaveAtLeastUnusedToken(Integer amountOfTokens) {
        ArrayList<Token> tokens = this.tokenService.generateTokens(amountOfTokens);
        this.currentCustomer.getTokens().addAll(tokens);
    }

    @When("the customer pays the merchant {int} kr")
    public void theCustomerPaysTheMerchantKr(Integer amount) {
        try {
            this.paymentToken = this.currentCustomer.getTokens().get(0);
            this.paymentPrice = amount;
            this.tokenService.makePayment(amount, this.currentCustomer, this.currentMerchant, this.currentCustomer.getTokens().get(0));
        } catch (TokenValidationException e) {
            ControlReg.getExceptionContainer().setErrorMessage(e.getMessage());
        }
    }

    @Then("the money is transferred from the customer to merchant")
    public void theMoneyIsTransferredFromTheCustomerToMerchant() {
        assertThat(this.currentCustomer.getAccount().getBalance(), is(equalTo(this.customerPreviousBalance - this.paymentPrice)));
        assertThat(this.currentMerchant.getAccount().getBalance(), is(equalTo(this.merchantPreviousBalance + this.paymentPrice)));
    }

    @Given("the customer have at least {int} used token")
    public void theCustomerHaveAtLeastUsedToken(Integer amountOfTokens) {
        ArrayList<Token> tokens = this.tokenService.generateTokens(amountOfTokens);
        this.currentCustomer.getTokens().addAll(tokens);

        for (Token token : this.currentCustomer.getTokens()) {
            token.setValid(false);
        }
    }

    @When("the customer pay {int} with a used token")
    public void theCustomerPayWithAUsedToken(Integer amount) {
        try {
            this.paymentToken = this.currentCustomer.getTokens().get(0);
            this.paymentPrice = amount;
            this.tokenService.makePayment(amount, this.currentCustomer, this.currentMerchant, this.currentCustomer.getTokens().get(0));
        } catch (TokenValidationException e) {
            ControlReg.getExceptionContainer().setErrorMessage(e.getMessage());
        }
    }

    @Then("the payment is rejected with the error message {string}")
    public void thePaymentIsRejectedWithTheErrorMessage(String errorMessage) {
        assertThat(ControlReg.getExceptionContainer().getErrorMessage(), is(equalTo(errorMessage)));
    }

    @Given("a token not attached to the customer")
    public void aTokenNotAttachedToTheCustomer() {
        this.paymentToken = new Token();

        assertThat(this.validationService.isTokenFake(this.currentCustomer, this.paymentToken), is(equalTo(true)));
    }

    @When("the customer pay {int} with a fake token")
    public void theCustomerPayWithAFakeToken(Integer amount) {
        try {
            this.paymentPrice = amount;
            this.tokenService.makePayment(amount, this.currentCustomer, this.currentMerchant, this.paymentToken);
        } catch (TokenValidationException e) {
            ControlReg.getExceptionContainer().setErrorMessage(e.getMessage());
        }
    }
}
