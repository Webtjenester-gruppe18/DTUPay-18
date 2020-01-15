package Database;

import Model.DTUPayTransaction;

import java.util.ArrayList;

public class InMemoryReportDatabase implements IReportDatabase {

    private ArrayList<DTUPayTransaction> transactions = new ArrayList<>();

    @Override
    public DTUPayTransaction getTransactionById(String transactionId) {
        for (DTUPayTransaction transaction : this.transactions) {
            if (transaction.getTransactionId().equals(transactionId)) {
                return transaction;
            }
        }

        return null;
    }

    @Override
    public ArrayList<DTUPayTransaction> getAllTransactions() {
        return this.transactions;
    }

    @Override
    public String saveTransaction(DTUPayTransaction transaction) {
        this.transactions.add(transaction);

        return transaction.getTransactionId();
    }
}
