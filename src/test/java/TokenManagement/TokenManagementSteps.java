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

}
