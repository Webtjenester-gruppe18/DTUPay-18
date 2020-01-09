package TokenManagement;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import Control.ControlReg;
import Exception.*;
import Database.IDatabase;
import Exception.TokenValidationException;
import Model.Customer;
import Model.Merchant;
import Model.Token;
import Service.TokenService;
import Service.ValidationService;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.ArrayList;

public class TokenManagementSteps {

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

    @Given("the customer is registered")
    public void theCustomerIsRegistered() {
        this.currentCustomer = new Customer("John Doe");
        this.database.addCustomer(currentCustomer);

        assertThat(this.database.getCustomer(currentCustomer.getId()), is(equalTo(currentCustomer)));
    }

    @Given("the customer have {int} unused token left")
    public void theCustomerHaveUnusedTokenLeft(Integer amountOfTokens) {
        ArrayList<Token> generatedTokens = tokenService.generateTokens(amountOfTokens);
        this.currentCustomer.addTokens(generatedTokens);

        assertThat(this.database.getCustomer(this.currentCustomer.getId()).getTokens().size(), is(equalTo(generatedTokens.size())));
        assertThat(this.database.getCustomer(this.currentCustomer.getId()).getTokens().get(0), is(equalTo(generatedTokens.get(0))));
    }

    @When("the customer request more tokens")
    public void theCustomerRequestMoreTokens() {
        try {
            ArrayList<Token> tokens = this.tokenService.requestNewTokens(this.currentCustomer.getTokens());
            this.requestedTokens = tokens;
        } catch (TooManyTokensException e) {
            this.exceptionContainer.setErrorMessage(e.getMessage());
        }
    }

    @Then("the service create {int} new unused tokens")
    public void theServiceCreateNewUnusedTokens(Integer amountOfTokensReceived) {
        assertThat(this.requestedTokens.size(), is(equalTo(amountOfTokensReceived)));
    }

    @Then("the customer receive the tokens, and have {int} unused tokens")
    public void theCustomerReceiveTheTokensAndHaveUnusedTokens(Integer totalAmountOfTokens) {
        this.currentCustomer.addTokens(this.requestedTokens);

        assertThat(this.currentCustomer.getTokens().size(), is(equalTo(totalAmountOfTokens)));
    }

    @Then("the customer gets a error message saying {string}")
    public void theCustomerGetsAErrorMessageSaying(String errorMessage) {
        assertThat(this.exceptionContainer.getErrorMessage(), is(equalTo(errorMessage)));
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

}
