package ValidationSteps;
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
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class TokenValidationSteps {
    @Given("a token that is used")
    public void a_token_that_is_used() {


    }

    @When("a merchant scans the token")
    public void a_merchant_scans_the_token() {
        // Write code here that turns the phrase above into concrete actions

    }

    @Then("the token is rejected")
    public void the_token_is_rejected() {
        // Write code here that turns the phrase above into concrete actions

    }

    @Given("a token that is unused")
    public void aTokenThatIsUnused() {
    }

    @And("not fake")
    public void notFake() {
    }

    @Then("the token is valid")
    public void theTokenIsValid() {
    }

    @Given("a token that is fake")
    public void aTokenThatIsFake() {
    }
}
