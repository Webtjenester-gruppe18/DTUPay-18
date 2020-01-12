package Bank;

import Model.Token;
import dtu.ws.fastmoney.Account;
import dtu.ws.fastmoney.BankServiceException_Exception;
import dtu.ws.fastmoney.User;
import Exception.*;

import java.math.BigDecimal;

public interface IBank {

    String createAccountWithBalance(User user, BigDecimal initialBalance) throws BankServiceException_Exception;
    Account getAccount(String accountNumber) throws BankServiceException_Exception;
    Account getAccountByCpr(String cpr) throws BankServiceException_Exception;
    void retireAccount(String accountNumber) throws BankServiceException_Exception;
    void transferMoneyFromTo(String fromAccountNumber, String toAccountNumber, BigDecimal amount, String description, Token token) throws BankServiceException_Exception, TokenValidationException;
}
