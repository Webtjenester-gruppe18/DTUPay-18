package Service;

import Control.ControlReg;
import Model.CustomerReportTransaction;
import Model.Token;
import dtu.ws.fastmoney.Account;
import dtu.ws.fastmoney.BankServiceException_Exception;
import dtu.ws.fastmoney.Transaction;
import Exception.*;

import java.math.BigDecimal;
import java.util.Date;

public class PaymentService implements IPaymentService {

    private IBankService bankService = ControlReg.getBankService();
    private ITokenManager tokenManager = ControlReg.getTokenManager();

    @Override
    public void performPayment(String fromAccountNumber, String toAccountNumber, BigDecimal amount, String description, Token token) throws BankServiceException_Exception, TokenValidationException, NotEnoughMoneyException {
        Account customerAccount = this.bankService.getAccount(fromAccountNumber);
        this.tokenManager.validateToken(customerAccount.getUser().getCprNumber(), token);

        if (isPaymentPossible(customerAccount, amount)) {
            this.bankService.transferMoneyFromTo(fromAccountNumber, toAccountNumber, amount, description, token);
            this.tokenManager.useToken(token);

            CustomerReportTransaction customerReportTransaction = new CustomerReportTransaction(
                    amount, new Date().getTime(), token, toAccountNumber
            );
        }
    }

    @Override
    public void performRefund(Transaction transaction) {

    }

    private boolean isPaymentPossible(Account account, BigDecimal requestedAmount) throws NotEnoughMoneyException {
        if (account.getBalance().compareTo(requestedAmount) != -1) {
            return true;
        }

        throw new NotEnoughMoneyException("You have not enough money.");
    }
}
