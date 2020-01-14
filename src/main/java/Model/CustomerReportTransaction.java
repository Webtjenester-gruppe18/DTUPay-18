package Model;

import dtu.ws.fastmoney.Transaction;

public class CustomerReportTransaction extends ReportTransaction {

    private String merchantAccountId;

    public CustomerReportTransaction(Transaction transaction, String customerCpr) {
        super(transaction);

        if (transaction.getCreditor().equals(customerCpr)) {
            this.merchantAccountId = transaction.getDebtor();
        } else {
            this.merchantAccountId = transaction.getCreditor();
        }
    }

    public String getMerchantAccountId() {
        return merchantAccountId;
    }

    public void setMerchantAccountId(String merchantAccountId) {
        this.merchantAccountId = merchantAccountId;
    }
}
