package Service;

import Model.Token;
import dtu.ws.fastmoney.BankServiceException_Exception;
import dtu.ws.fastmoney.Transaction;
import Exception.*;

import java.math.BigDecimal;

public interface IPaymentService {

    void performPayment(String fromAccountNumber, String toAccountNumber, BigDecimal amount, String description, Token token) throws BankServiceException_Exception, TokenValidationException, NotEnoughMoneyException;
    void performRefund(Transaction transaction);

}
