package Model;

import dtu.ws.fastmoney.Transaction;

import java.math.BigDecimal;

public class MerchantReportTransaction extends ReportTransaction {

    public MerchantReportTransaction(BigDecimal amount, long dateTime, Token tokenUsed) {
        super(amount, dateTime, tokenUsed);
    }
}
