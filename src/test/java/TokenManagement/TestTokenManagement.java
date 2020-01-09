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
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.ArrayList;

public class TestTokenManagement {

    private IDatabase database;
    private TokenService tokenService;
    private Customer currentCustomer;
    private Merchant currentMerchant;
    private ArrayList<Token> requestedTokens;
    private ExceptionContainer exceptionContainer;
    private Token token;
    private boolean validationResult;

    @Before
    public void setUp() {
        this.database = ControlReg.getDatabase();
        this.tokenService = new TokenService();
        this.requestedTokens = new ArrayList<>();
        this.exceptionContainer = new ExceptionContainer();
    }

    @Given("the customer is registered")
    public void theCustomerIsRegistered() {
        this.currentCustomer = new Customer("John Doe");
        this.database.addCustomer(currentCustomer);

        assertThat(this.database.getCustomer(currentCustomer.getId()), is(equalTo(currentCustomer)));
    }

    @Given("A merchant that is registered")
    public void aMerchantThatIsRegistered() {
        this.currentMerchant = new Merchant("Jane Doe");
        this.database.addMerchant(this.currentMerchant);

        assertThat(this.database.getMerchant(this.currentMerchant.getId()), is(equalTo(this.currentMerchant)));
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

    @Given("A valid token")
    public void aValidToken() {
        ArrayList<Token> generatedTokens = this.tokenService.generateTokens(1);
        this.token = generatedTokens.get(0);
        this.database.addToken(this.token);

        assertThat(this.token, is(equalTo(generatedTokens.get(0))));
    }

    @Given("A invalid token")
    public void aInvalidToken() {
        ArrayList<Token> generatedTokens = this.tokenService.generateTokens(1);
        this.token = generatedTokens.get(0);
        this.token.setValid(false);
        this.database.addToken(this.token);

        assertThat(this.token, is(equalTo(generatedTokens.get(0))));
    }

    @Given("A fake token")
    public void aFakeToken() {
        ArrayList<Token> generatedTokens = this.tokenService.generateTokens(1);
        this.token = generatedTokens.get(0);

        assertThat(this.token, is(equalTo(generatedTokens.get(0))));
    }

    @When("the validation is processing")
    public void theValidationIsProcessing() {

        try {
            this.validationResult = this.tokenService.validateToken(this.token);
        } catch (TokenValidationException e) {
            ControlReg.getExceptionContainer().setErrorMessage(e.getMessage());
        }
    }

    @Then("the result is {string}")
    public void theResultIs(String res) {

        assertThat(this.validationResult, is(equalTo(Boolean.valueOf(res))));
    }

    @Then("a errormessage is presented {string}")
    public void aErrormessageIsPresented(String exceptionMessage) {
        assertThat(ControlReg.getExceptionContainer().getErrorMessage(), is(equalTo(exceptionMessage)));
    }

    @When("the customer use a token")
    public void theCustomerUseAToken() {
        this.token = this.currentCustomer.getTokens().get(0);
        this.database.addToken(this.token);

        try {
            this.tokenService.useToken(this.currentCustomer, this.currentMerchant, this.token);
        } catch (TokenValidationException e) {
            ControlReg.getExceptionContainer().setErrorMessage(e.getMessage());
        }

        assertThat(this.token.isValid(), is(equalTo(false)));
    }

    @Then("the customer gets the token removes")
    public void theCustomerGetsTheTokenRemoves() {
        boolean hasTokenBeenRemoved = true;

        for (Token t : this.currentCustomer.getTokens()) {
            if (t.getValue().equals(this.token.getValue())) {
                hasTokenBeenRemoved = false;
                break;
            }
        }

        assertThat(hasTokenBeenRemoved, is(equalTo(true)));
    }

    @Then("a transaction is added to the merchant")
    public void aTransactionIsAddedToTheMerchant() {
        assertThat(this.currentMerchant.getAccount().getTransactions().size(), is(equalTo(1)));
    }

    @Then("a transaction is added to the customer")
    public void aTransactionIsAddedToTheCustomer() {
        assertThat(this.currentCustomer.getAccount().getTransactions().size(), is(equalTo(1)));
    }

}
