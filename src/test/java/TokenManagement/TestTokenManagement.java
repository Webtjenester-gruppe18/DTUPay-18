package TokenManagement;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import Exception.TooManyTokensException;
import Exception.ExceptionContainer;
import Database.IDatabase;
import Database.InMemoryDatabase;
import Model.Customer;
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
    private ArrayList<Token> requestedTokens;
    private ExceptionContainer exceptionContainer;

    @Before
    public void setUp() {
        this.database = new InMemoryDatabase();
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

    @Then("the customer receive {int} unused tokens")
    public void theCustomerReceiveUnusedTokens(Integer amountOfTokensReceived) {
        assertThat(this.requestedTokens.size(), is(equalTo(amountOfTokensReceived)));
    }
}
