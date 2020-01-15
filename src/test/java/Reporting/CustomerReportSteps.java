package Reporting;

import Control.ControlReg;
import Model.Customer;
import Model.CustomerReportTransaction;
import Model.DTUPayTransaction;
import Model.Token;
import Service.IBankService;
import Service.IReportingService;
import Utillity.Utillity;
import dtu.ws.fastmoney.Account;
import dtu.ws.fastmoney.BankServiceException_Exception;
import dtu.ws.fastmoney.Transaction;
import dtu.ws.fastmoney.User;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CustomerReportSteps {

    private IBankService bankService;
    private IReportingService reportingService;
    private Customer currentCustomer;
    private String accountId;
    private ArrayList<CustomerReportTransaction> customerTransactions;


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
        this.currentCustomer.setAccountId("Some value for testing");
        this.currentCustomer.setTransactionIds(new ArrayList<>());
    }

    @Given("the customer has performed atleast one transaction")
    public void theCustomerHasPerformedAtleastOneTransaction() {

        DTUPayTransaction transaction =
                new DTUPayTransaction(
                        BigDecimal.valueOf(1111),
                        this.currentCustomer.getAccountId(),
                        "Some Value",
                        "Comment",
                        new Date().getTime(),
                        new Token());

        String transactionId = this.reportingService.saveTransaction(transaction);

        this.currentCustomer.getTransactionIds().add(transactionId);

        assertEquals(1, this.currentCustomer.getTransactionIds().size());
    }

    @When("the customer requests for an overview")
    public void theCustomerRequestsForAnOverview() {
        this.customerTransactions = this.reportingService.getCustomerTransactionsByIds(this.currentCustomer);
    }

    @Then("an overview is create with one transaction")
    public void anOverviewIsCreateWithOneTransaction() {
        assertEquals(1, this.customerTransactions.size());
    }

    @Given("the customer has performed atleast one transaction in the last month")
    public void theCustomerHasPerformedAtleastOneTransactionInTheLastMonth() {

        DTUPayTransaction transaction =
                new DTUPayTransaction(
                        BigDecimal.valueOf(1111),
                        this.currentCustomer.getAccountId(),
                        "Some Value",
                        "Comment",
                        new Date().getTime(),
                        new Token());

        String transactionId = this.reportingService.saveTransaction(transaction);

        this.currentCustomer.getTransactionIds().add(transactionId);

        assertEquals(1, this.currentCustomer.getTransactionIds().size());
    }

    @When("the customer requests for an monthly overview")
    public void theCustomerRequestsForAnMonthlyOverview() {
        this.customerTransactions = this.reportingService.getCustomerTransactionsByIdsFromThenToNow(this.currentCustomer, Utillity.MONTH_IN_MILLIS);
    }

    @After
    public void tearDown() throws BankServiceException_Exception {
        if (this.accountId != null) {
            this.bankService.retireAccount(this.accountId);
        }
    }
}
