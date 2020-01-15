package Model;

import dtu.ws.fastmoney.Transaction;

import java.math.BigDecimal;

public class CustomerReportTransaction extends ReportTransaction {

    private String merchantAccountId;

    public CustomerReportTransaction(BigDecimal amount, long dateTime, Token tokenUsed, String merchantAccountId) {
        super(amount, dateTime, tokenUsed);

        this.merchantAccountId = merchantAccountId;
    }

    public String getMerchantAccountId() {
        return merchantAccountId;
    }

    public void setMerchantAccountId(String merchantAccountId) {
        this.merchantAccountId = merchantAccountId;
    }
}
