package Service;

import Control.ControlReg;
import Model.Token;
import dtu.ws.fastmoney.*;
import Exception.*;

import java.math.BigDecimal;

public class BankService implements IBankService {

    private dtu.ws.fastmoney.BankService bank = ControlReg.getFastMoneyBankService();

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
    public void transferMoneyFromTo(String fromAccountNumber, String toAccountNumber, BigDecimal amount, String description, Token token) throws BankServiceException_Exception {
        this.bank.transferMoneyFromTo(fromAccountNumber, toAccountNumber, amount, description);
    }
}