package Bank;

import Control.ControlReg;
import Model.Token;
import Service.ITokenManager;
import dtu.ws.fastmoney.*;
import Exception.*;

import java.math.BigDecimal;

public class Bank implements IBank {

    private BankService bank = ControlReg.getBankService();
    private ITokenManager tokenManager = ControlReg.getTokenManager();

    @Override
    public String createAccountWithBalance(User user, BigDecimal initialBalance) throws BankServiceException_Exception {
        return this.bank.createAccountWithBalance(user, initialBalance);
    }

    @Override
    public Account getAccount(String accountNumber) throws BankServiceException_Exception {
        return this.bank.getAccount(accountNumber);
    }

    @Override
    public Account getAccountByCpr(String cpr) throws BankServiceException_Exception {
        return this.bank.getAccountByCprNumber(cpr);
    }

    @Override
    public void retireAccount(String accountNumber) throws BankServiceException_Exception {
        this.bank.retireAccount(accountNumber);
    }

    @Override
    public void transferMoneyFromTo(String fromAccountNumber, String toAccountNumber, BigDecimal amount, String description, Token token) throws BankServiceException_Exception, TokenValidationException {

        Account customerAccount = this.bank.getAccount(fromAccountNumber);
        this.tokenManager.validateToken(customerAccount.getUser().getCprNumber(), token);

        this.bank.transferMoneyFromTo(fromAccountNumber, toAccountNumber, amount, description);

        token.setHasBeenUsed(true);
    }
}
