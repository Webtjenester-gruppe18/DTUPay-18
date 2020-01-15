package Reporting;

import Control.ControlReg;
import Model.Customer;
import Service.IBankService;
import Service.IReportingService;
import dtu.ws.fastmoney.Account;
import dtu.ws.fastmoney.BankServiceException_Exception;
import dtu.ws.fastmoney.Transaction;
import dtu.ws.fastmoney.User;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CustomerReportSteps {

    private IBankService bankService;
    private IReportingService reportingService;
    private Customer currentCustomer;
    private String accountId;
    private List<Transaction> customerTransactions;


    @Before
    public void setUp() {
        this.bankService = ControlReg.getBankService();
        this.reportingService = ControlReg.getReportingService();
    }

    @Given("a registered customer with an account")
    public void aRegisteredCustomerWithAnAccount() {
        this.currentCustomer = new Customer();
        this.currentCustomer.setCprNumber("888888-2222");
        this.currentCustomer.setFirstName("Jane");
        this.currentCustomer.setLastName("Doe");

        try {
            this.accountId = this.bankService.createAccountWithBalance(this.currentCustomer, BigDecimal.valueOf(500));
        } catch (BankServiceException_Exception e) {
            ControlReg.getExceptionContainer().setErrorMessage(e.getMessage());
        }

        Account customerAccount = null;
        try {
            customerAccount = this.bankService.getAccount(this.accountId);
        } catch (BankServiceException_Exception e) {
            ControlReg.getExceptionContainer().setErrorMessage(e.getMessage());
        }

        assertEquals(customerAccount.getBalance(), BigDecimal.valueOf(500));
    }

    @Given("the customer has performed atleast one transaction")
    public void theCustomerHasPerformedAtleastOneTransaction() {
        Account customerAccount = null;
        try {
            customerAccount = this.bankService.getAccount(this.accountId);
        } catch (BankServiceException_Exception e) {
            e.printStackTrace();
        }

        Transaction transaction = new Transaction();
        customerAccount.getTransactions().add(transaction);

        assertEquals(1, customerAccount.getTransactions().size());
    }

    @When("the customer requests for an overview")
    public void theCustomerRequestsForAnOverview() {
        try {
            this.customerTransactions = this.reportingService.getTransactionsByCpr(this.currentCustomer.getCprNumber());
        } catch (BankServiceException_Exception e) {
            ControlReg.getExceptionContainer().setErrorMessage(e.getMessage());
        }
    }

    @Then("an overview is create with one transaction")
    public void anOverviewIsCreateWithOneTransaction() {
        assertEquals(1, this.customerTransactions.size());
    }

    @After
    public void tearDown() throws BankServiceException_Exception {
        if (this.accountId != null) {
            this.bankService.retireAccount(this.accountId);
        }
    }
}
