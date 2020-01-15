package Service;

import Model.*;
import dtu.ws.fastmoney.BankServiceException_Exception;
import dtu.ws.fastmoney.Transaction;

import java.util.ArrayList;
import java.util.List;

public interface IReportingService {

    DTUPayTransaction getTransactionById(String transactionId);
    ArrayList<CustomerReportTransaction> getCustomerTransactionsByIds(Customer customer);
    ArrayList<CustomerReportTransaction> getCustomerTransactionsByIdsFromThenToNow(Customer customer, long fromTime);
    ArrayList<MerchantReportTransaction> getMerchantTransactionsByIds(Merchant merchant);
    ArrayList<DTUPayTransaction> getAllTransactions();
    String saveTransaction(DTUPayTransaction transaction);

}
