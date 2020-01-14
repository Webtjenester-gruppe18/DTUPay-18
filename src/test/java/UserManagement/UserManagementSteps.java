package UserManagement;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;


import Service.*;
import Control.ControlReg;
import Exception.*;
import Exception.TokenValidationException;
import Model.Token;
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

public class UserManagementSteps {

    private User currentUser;
    private IUserService userService;

    @Before
    public void setUp() throws Exception {
        this.userService = ControlReg.getUserService();
    }

    @Given("a customer is not already registered")
    public void a_customer_is_not_already_registered() {
        // Write code here that turns the phrase above into concrete actions

        this.currentUser = new User();

    }

    @Given("with first name is {string}")
    public void with_first_name_is(String firstName) {
        // Write code here that turns the phrase above into concrete actions

        this.currentUser.setFirstName(firstName);

    }

    @Given("with last name is {string}")
    public void with_last_name_is(String lastName) {
        // Write code here that turns the phrase above into concrete actions

        this.currentUser.setLastName(lastName);

    }

    @Given("with CPR number {string}")
    public void with_CPR_number(String cprNumber) {
        // Write code here that turns the phrase above into concrete actions

        this.currentUser.setCprNumber(cprNumber);

    }

    @When("the customer registers")
    public void the_customer_registers() {
        // Write code here that turns the phrase above into concrete actions

        assertThat(userService.customerExists(currentUser), equalTo(false));

        try {
            this.userService.saveCustomer(this.currentUser);
        } catch (UserAlreadyExistsException e) {
            ControlReg.getExceptionContainer().setErrorMessage(e.getMessage());
        }
    }

    @Then("the account is created and registered")
    public void the_account_is_created_and_registered() {
        // Write code here that turns the phrase above into concrete actions
        assertThat(userService.customerExists(currentUser), equalTo(true));
    }

    @When("the customer tries to register with the same credentials")
    public void the_customer_tries_to_register_with_the_same_credentials() {
        // Write code here that turns the phrase above into concrete actions

        try {
            this.userService.saveCustomer(this.currentUser);
        } catch (UserAlreadyExistsException e) {
            ControlReg.getExceptionContainer().setErrorMessage(e.getMessage());
        }
    }

    @Then("the customer will be rejected with the error message {string}")
    public void the_customer_will_be_rejected_with_the_error_message(String errorMessage) {
        // Write code here that turns the phrase above into concrete actions

        assertThat(ControlReg.getExceptionContainer().getErrorMessage(), equalTo(errorMessage));

    }

    @Given("a customer has an account")
    public void a_customer_has_an_account() {
        // Write code here that turns the phrase above into concrete actions


    }

    @When("the customer requests to delete the account")
    public void the_customer_requests_to_delete_the_account() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Then("the account will be deleted")
    public void the_account_will_be_deleted() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Given("a customer that has an account with balance of {int} kr")
    public void a_customer_that_has_an_account_with_balance_of_kr(Integer int1) {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @When("the customer adds {int} kr to the account")
    public void the_customer_adds_kr_to_the_account(Integer int1) {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Then("the money will be added to the account")
    public void the_money_will_be_added_to_the_account() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Then("the account balance is {int} kr")
    public void the_account_balance_is_kr(Integer int1) {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Given("a merchant that is not already registered")
    public void a_merchant_that_is_not_already_registered() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @When("the merchant tries to register")
    public void the_merchant_tries_to_register() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Then("the merchant has created a bank account with the credentials given")
    public void the_merchant_has_created_a_bank_account_with_the_credentials_given() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @When("the merchant tries to register with the same credentials")
    public void the_merchant_tries_to_register_with_the_same_credentials() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Then("the merchant will be rejected with the error message {string}")
    public void the_merchant_will_be_rejected_with_the_error_message(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Given("the merchant has an account")
    public void the_merchant_has_an_account() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @When("the merchant requests to delete the account")
    public void the_merchant_requests_to_delete_the_account() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }
}
