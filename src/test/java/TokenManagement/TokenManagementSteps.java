package TokenManagement;
import static org.junit.Assert.assertEquals;

import Control.ControlReg;
import Exception.*;
import Model.Token;
import Service.ITokenManager;
import dtu.ws.fastmoney.User;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.After;
import org.junit.Assert;

import java.util.ArrayList;

public class TokenManagementSteps {

    private User currentCustomer;
    private ITokenManager tokenManager;
    private ArrayList<Token> tokensReceived;

    @Before
    public void setUp() {
        this.tokenManager = ControlReg.getTokenManager();
    }

    @Given("the customer is registered")
    public void theCustomerIsRegistered() {
        User customer = new User();
        customer.setCprNumber("991199-0000");
        customer.setFirstName("Jane");
        customer.setLastName("Doe");

        this.currentCustomer = customer;
    }

    @Given("the customer has no more than {int} unused token left")
    public void theCustomerHasNotMoreThanUnusedTokenLeft(Integer tokensLeft) {
        try {
            this.tokenManager.generateTokens(this.currentCustomer.getCprNumber(), tokensLeft);
        } catch (TooManyTokensException e) {
            ControlReg.getExceptionContainer().setErrorMessage(e.getMessage());
        }
    }

    @When("the customer requests more tokens")
    public void theCustomerRequestsMoreTokens() {
        try {
            this.tokensReceived = this.tokenManager.requestForNewTokens(this.currentCustomer.getCprNumber());
        } catch (TooManyTokensException e) {
            ControlReg.getExceptionContainer().setErrorMessage(e.getMessage());
        }
    }

    @Then("the customer receives {int} new unused tokens")
    public void theCustomerReceivesNewUnusedTokens(Integer amountOfReceivedTokens) {
        assertEquals(Integer.valueOf(this.tokensReceived.size()), amountOfReceivedTokens);
    }

    @Then("then has {int} unused tokens")
    public void thenHasUnusedTokens(Integer amountOfTokensAttachedToTheUserAccount) {
        assertEquals(Integer.valueOf(this.tokenManager.getTokensByCpr(this.currentCustomer.getCprNumber()).size()), amountOfTokensAttachedToTheUserAccount);
    }

    @Given("the customer has atleast {int} unused token left")
    public void theCustomerHasAtleastUnusedTokenLeft(Integer amountOfTokens) {
        try {
            this.tokenManager.requestForNewTokens(this.currentCustomer.getCprNumber());
        } catch (TooManyTokensException e) {
            ControlReg.getExceptionContainer().setErrorMessage(e.getMessage());
        }
    }

    @Then("the customer gets a error message saying {string}")
    public void theCustomerGetsAErrorMessageSaying(String errorMessage) {
        assertEquals(ControlReg.getExceptionContainer().getErrorMessage(), errorMessage);
    }

    @After
    public void tearDown() {
        this.tokenManager.clearUserTokens(this.currentCustomer.getCprNumber());
    }
}
