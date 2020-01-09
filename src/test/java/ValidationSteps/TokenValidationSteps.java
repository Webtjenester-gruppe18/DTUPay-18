package ValidationSteps;
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

public class TokenValidationSteps {

    private IDatabase database;
    private TokenService tokenService;
    private ValidationService validationService;
    private Customer currentCustomer;
    private ArrayList<Token> requestedTokens;
    private ExceptionContainer exceptionContainer;
    private Token currentToken;
    private Token validationResult;

    @Before
    public void setUp() {
        this.database = ControlReg.getDatabase();
        this.tokenService = ControlReg.getTokenService();
        this.validationService = ControlReg.getValidationService();
        this.requestedTokens = new ArrayList<>();
        this.exceptionContainer = new ExceptionContainer();
    }

    @Given("a token that is unused")
    public void aTokenThatIsUnused() {
        ArrayList<Token> tokens = this.tokenService.generateTokens(1);
        this.currentToken = tokens.get(0);
    }

    @Given("the token is registered to a customer")
    public void theIsRegisteredToACustomer() {
        this.currentCustomer = new Customer("Jane Doe");
        this.currentCustomer.getTokens().add(this.currentToken);
    }

    @Given("the token is not registered to a customer")
    public void theIsNotRegisteredToACustomer() {
        this.currentCustomer = new Customer("Jane Doe");
    }

    @Given("a token that is used")
    public void aTokenThatIsUsed() {
        ArrayList<Token> tokens = this.tokenService.generateTokens(1);
        this.currentToken = tokens.get(0);
        this.currentToken.setValid(false);
    }

    @Given("a token that is fake")
    public void aTokenThatIsFake() {
        ArrayList<Token> tokens = this.tokenService.generateTokens(1);
        this.currentToken = tokens.get(0);
    }

    @When("a merchant scans the token")
    public void aMerchantScansTheToken() {
        try {
            this.validationResult = this.validationService.validateToken(this.currentCustomer, this.currentToken);
        } catch (TokenValidationException e) {
            ControlReg.getExceptionContainer().setErrorMessage(e.getMessage());
        }
    }

    @Then("^the token is rejected with error message \"([^\"]*)\"$")
    public void theTokenIsRejectedWithErrorMessage(String errorMessage) {
        assertThat(ControlReg.getExceptionContainer().getErrorMessage(), is(equalTo(errorMessage)));
    }

    @Then("the token is valid")
    public void theTokenIsValid() {
        assertThat(this.currentToken, is(equalTo(this.validationResult)));
    }
}
