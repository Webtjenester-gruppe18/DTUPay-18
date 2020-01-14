package Model;

import dtu.ws.fastmoney.Transaction;

import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;

public abstract class ReportTransaction {

    private BigDecimal amount;
    private XMLGregorianCalendar time;
    private Token tokenUsed;

    public ReportTransaction(Transaction transaction) {
        this.amount = transaction.getAmount();
        this.time = transaction.getTime();
        this.tokenUsed = new Token();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public XMLGregorianCalendar getTime() {
        return time;
    }

    public void setTime(XMLGregorianCalendar time) {
        this.time = time;
    }

    public Token getTokenUsed() {
        return tokenUsed;
    }

    public void setTokenUsed(Token tokenUsed) {
        this.tokenUsed = tokenUsed;
    }
}
