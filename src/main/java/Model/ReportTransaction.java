package Model;

import dtu.ws.fastmoney.Transaction;

import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.util.Date;

public abstract class ReportTransaction {

    private BigDecimal amount;
    private long dateTime;
    private Token tokenUsed;

    public ReportTransaction(BigDecimal amount, long dateTime, Token tokenUsed) {
        this.amount = amount;
        this.dateTime = dateTime;
        this.tokenUsed = tokenUsed;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public long getDateTime() {
        return dateTime;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }

    public Token getTokenUsed() {
        return tokenUsed;
    }

    public void setTokenUsed(Token tokenUsed) {
        this.tokenUsed = tokenUsed;
    }
}
