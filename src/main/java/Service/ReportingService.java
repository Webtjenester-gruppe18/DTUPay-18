package Service;

import Control.ControlReg;
import Database.IReportDatabase;
import Model.*;
import dtu.ws.fastmoney.BankServiceException_Exception;
import dtu.ws.fastmoney.Transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReportingService implements IReportingService {

    private IReportDatabase reportDatabase = ControlReg.getReportDatabase();

    @Override
    public DTUPayTransaction getTransactionById(String transactionId) {
        return this.reportDatabase.getTransactionById(transactionId);
    }

    @Override
    public ArrayList<CustomerReportTransaction> getCustomerTransactionsByIds(Customer customer) {

        ArrayList<CustomerReportTransaction> result = new ArrayList<>();

        for (String transactionId : customer.getTransactionIds()) {
            DTUPayTransaction transaction = this.reportDatabase.getTransactionById(transactionId);

            CustomerReportTransaction reportTransaction =
                    new CustomerReportTransaction(transaction, customer);

            result.add(reportTransaction);
        }

        return result;
    }

    @Override
    public ArrayList<CustomerReportTransaction> getCustomerTransactionsByIdsFromThenToNow(Customer customer, long fromTime) {

        ArrayList<CustomerReportTransaction> result = new ArrayList<>();

        for (String transactionId : customer.getTransactionIds()) {
            DTUPayTransaction transaction = this.reportDatabase.getTransactionById(transactionId);

            if (transaction.getTime() > new Date().getTime() - fromTime) {
                CustomerReportTransaction reportTransaction =
                        new CustomerReportTransaction(transaction, customer);

                result.add(reportTransaction);
            }
        }


        return result;
    }

    @Override
    public ArrayList<MerchantReportTransaction> getMerchantTransactionsByIds(Merchant merchant) {
        return null;
    }

    @Override
    public ArrayList<DTUPayTransaction> getAllTransactions() {
        return null;
    }

    @Override
    public String saveTransaction(DTUPayTransaction transaction) {
        return this.reportDatabase.saveTransaction(transaction);
    }
}
