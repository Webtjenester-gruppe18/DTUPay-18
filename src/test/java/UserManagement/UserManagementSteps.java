package UserManagement;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


import Model.Customer;
import Model.Merchant;
import Service.*;
import Control.ControlReg;
import Exception.*;
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

    private Customer currentCustomer;
    private Merchant currentMerchant;
    private IUserService userService;
    private IBankService bankService;
    private String currentAccountId;

    @Before
    public void setUp() throws Exception {
        this.userService = ControlReg.getUserService();
        this.bankService = ControlReg.getBankService();
    }

    @Given("a customer is not already registered")
    public void a_customer_is_not_already_registered() {
        // Write code here that turns the phrase above into concrete actions

        this.currentCustomer = new Customer();

    }

    @Given("the customers first name is {string}")
    public void with_first_name_is(String firstName) {
        // Write code here that turns the phrase above into concrete actions

        this.currentCustomer.setFirstName(firstName);

    }

    @Given("the customers last name is {string}")
    public void with_last_name_is(String lastName) {
        // Write code here that turns the phrase above into concrete actions

        this.currentCustomer.setLastName(lastName);

    }

    @Given("the customers CPR number {string}")
    public void with_CPR_number(String cprNumber) {
        // Write code here that turns the phrase above into concrete actions

        this.currentCustomer.setCprNumber(cprNumber);

    }

    @When("the customer registers")
    public void the_customer_registers() {
        // Write code here that turns the phrase above into concrete actions

        assertFalse(userService.customerExists(this.currentCustomer));

        try {
            this.userService.registerCustomer(this.currentCustomer);
        } catch (UserAlreadyExistsException e) {
            ControlReg.getExceptionContainer().setErrorMessage(e.getMessage());
        }
    }

    @Then("the account is created and registered")
    public void the_account_is_created_and_registered() {
        // Write code here that turns the phrase above into concrete actions
        assertTrue(userService.customerExists(this.currentCustomer));
    }

    @When("the customer tries to register with the same credentials")
    public void the_customer_tries_to_register_with_the_same_credentials() {
        // Write code here that turns the phrase above into concrete actions

        try {
            this.userService.registerCustomer(this.currentCustomer);
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
        this.currentCustomer = new Customer();

        this.currentCustomer.setFirstName("John");
        this.currentCustomer.setLastName("Doe");
        this.currentCustomer.setCprNumber("12345678");

        try {
            this.userService.registerCustomer(this.currentCustomer);
        } catch (UserAlreadyExistsException e) {
            ControlReg.getExceptionContainer().setErrorMessage(e.getMessage());
        }
        assertTrue(userService.customerExists(this.currentCustomer));
    }

    @When("the customer requests to delete the account")
    public void the_customer_requests_to_delete_the_account() {
        // Write code here that turns the phrase above into concrete actions
        this.userService.deleteCustomer(this.currentCustomer);
    }

    @Then("the customer account is deleted")
    public void theCustomerAccountIsDeleted() {
        // Write code here that turns the phrase above into concrete actions
        assertFalse(userService.customerExists(this.currentCustomer));
    }

    @Given("a merchant that is not already registered")
    public void a_merchant_that_is_not_already_registered() {
        // Write code here that turns the phrase above into concrete actions

        this.currentMerchant = new Merchant();
    }

    @Given("the merchants first name is {string}")
    public void theMerchantsFirstNameIs(String string) {
        // Write code here that turns the phrase above into concrete actions
        this.currentMerchant.setFirstName("Mikkel");
    }

    @Given("the merchants last name is {string}")
    public void theMerchantsLastNameIs(String string) {
        // Write code here that turns the phrase above into concrete actions
        this.currentMerchant.setLastName("Hansen");
    }

    @Given("the merchants CPR number {string}")
    public void theMerchantsCPRNumber(String string) {
        // Write code here that turns the phrase above into concrete actions
        this.currentMerchant.setCprNumber("87654321");
    }

    @When("the merchant tries to register")
    public void the_merchant_tries_to_register() {
        // Write code here that turns the phrase above into concrete actions

        assertFalse(userService.merchantExists(this.currentMerchant));

        try {
            this.userService.registerMerchant(this.currentMerchant);
        } catch (UserAlreadyExistsException e) {
            ControlReg.getExceptionContainer().setErrorMessage(e.getMessage());
        }
    }

    @Then("the merchant has created a bank account with the credentials given")
    public void the_merchant_has_created_a_bank_account_with_the_credentials_given() {
        // Write code here that turns the phrase above into concrete actions

        assertTrue(userService.merchantExists(this.currentMerchant));
    }

    @When("the merchant tries to register with the same credentials")
    public void the_merchant_tries_to_register_with_the_same_credentials() {
        // Write code here that turns the phrase above into concrete actions
        try {
            this.userService.registerMerchant(this.currentMerchant);
        } catch (UserAlreadyExistsException e) {
            ControlReg.getExceptionContainer().setErrorMessage(e.getMessage());
        }
    }

    @Then("the merchant will be rejected with the error message {string}")
    public void the_merchant_will_be_rejected_with_the_error_message(String errorMessage) {
        // Write code here that turns the phrase above into concrete actions
        assertThat(ControlReg.getExceptionContainer().getErrorMessage(), equalTo(errorMessage));
    }

    @Given("the merchant has an account")
    public void the_merchant_has_an_account() {
        // Write code here that turns the phrase above into concrete actions

        this.currentMerchant = new Merchant();

        this.currentMerchant.setFirstName("Mikkel");
        this.currentMerchant.setLastName("Hansen");
        this.currentMerchant.setCprNumber("87654321");
        this.currentMerchant.setAccountId("000000000");

        try {
            this.userService.registerMerchant(this.currentMerchant);
        } catch (UserAlreadyExistsException e) {
            ControlReg.getExceptionContainer().setErrorMessage(e.getMessage());
        }
        assertTrue(userService.merchantExists(this.currentMerchant));
    }

    @When("the merchant requests to delete the account")
    public void the_merchant_requests_to_delete_the_account() {
        // Write code here that turns the phrase above into concrete actions
        this.userService.deleteMerchant(this.currentMerchant);
    }

    @Then("the merchant account is deleted")
    public void theMerchantAccountIsDeleted() {
        // Write code here that turns the phrase above into concrete actions

        assertFalse(userService.merchantExists(this.currentMerchant));
    }


    @After
    public void tearDown(Scenario scenario) throws BankServiceException_Exception {

        if (this.currentAccountId != null) {
            this.bankService.retireAccount(this.currentAccountId);
        }

//        if (this.merchantAccountNumber != null) {
//            this.bankService.retireAccount(this.merchantAccountNumber);
//        }

        if (this.currentCustomer != null) {
            this.userService.deleteCustomer(currentCustomer);
        }
    }

}
