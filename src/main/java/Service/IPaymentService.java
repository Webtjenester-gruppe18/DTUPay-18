package Service;

import Model.DTUPayTransaction;
import Model.Token;
import dtu.ws.fastmoney.BankServiceException_Exception;
import dtu.ws.fastmoney.Transaction;
import Exception.*;

import java.math.BigDecimal;

public interface IPaymentService {

    boolean performPayment(String fromAccountNumber, String toAccountNumber, BigDecimal amount, String description, Token token) throws BankServiceException_Exception, TokenValidationException, NotEnoughMoneyException;
    boolean performRefund(DTUPayTransaction transaction) throws BankServiceException_Exception;

}
