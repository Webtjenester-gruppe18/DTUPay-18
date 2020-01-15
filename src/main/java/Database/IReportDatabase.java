package Database;

import Model.DTUPayTransaction;

import java.util.ArrayList;

public interface IReportDatabase {

    DTUPayTransaction getTransactionById(String transactionId);
    ArrayList<DTUPayTransaction> getAllTransactions();
    String saveTransaction(DTUPayTransaction transaction);
}
