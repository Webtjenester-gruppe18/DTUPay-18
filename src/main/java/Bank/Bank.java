package Bank;

import Control.ControlReg;
import dtu.ws.fastmoney.*;

import java.math.BigDecimal;

public class Bank implements IBank {

    private BankService bank = ControlReg.getBankService();

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
    public void transferMoneyFromTo(String toAccountNumber, String fromAccountNumber, BigDecimal amount, String description) throws BankServiceException_Exception {
        this.bank.transferMoneyFromTo(toAccountNumber, fromAccountNumber, amount, description);
    }
}
