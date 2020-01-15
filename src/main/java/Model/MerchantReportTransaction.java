package Model;

import dtu.ws.fastmoney.Transaction;

import java.math.BigDecimal;

public class MerchantReportTransaction extends ReportTransaction {

    public MerchantReportTransaction(BigDecimal amount, String description, long time, Token token) {
        super(amount, description, time, token);
    }
}
