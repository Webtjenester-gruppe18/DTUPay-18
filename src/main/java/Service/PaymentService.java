package Service;

import Control.ControlReg;
import Model.Token;
import dtu.ws.fastmoney.Account;
import dtu.ws.fastmoney.BankServiceException_Exception;
import dtu.ws.fastmoney.Transaction;
import Exception.*;

import java.math.BigDecimal;

public class PaymentService implements IPaymentService {

    private IBankService bankService = ControlReg.getBankService();
    private ITokenManager tokenManager = ControlReg.getTokenManager();

    @Override
    public void performPayment(String fromAccountNumber, String toAccountNumber, BigDecimal amount, String description, Token token) throws BankServiceException_Exception, TokenValidationException {
        Account customerAccount = this.bankService.getAccount(fromAccountNumber);
        this.tokenManager.validateToken(customerAccount.getUser().getCprNumber(), token);

        this.bankService.transferMoneyFromTo(fromAccountNumber, toAccountNumber, amount, description, token);

        this.tokenManager.useToken(token);
    }

    @Override
    public void performRefund(Transaction transaction) {

    }
}
