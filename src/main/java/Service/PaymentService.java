package Service;

import Control.ControlReg;
import Model.CustomerReportTransaction;
import Model.DTUPayTransaction;
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
    private IReportingService reportingService = ControlReg.getReportingService();
    private IUserService userService = ControlReg.getUserService();

    @Override
    public boolean performPayment(String fromAccountNumber, String toAccountNumber, BigDecimal amount, String description, Token token) throws BankServiceException_Exception, TokenValidationException, NotEnoughMoneyException {
        Account customerAccount = this.bankService.getAccount(fromAccountNumber);
        this.tokenManager.validateToken(customerAccount.getUser().getCprNumber(), token);

        if (isPaymentPossible(customerAccount, amount)) {
            this.bankService.transferMoneyFromTo(fromAccountNumber, toAccountNumber, amount, description);
            this.tokenManager.useToken(token);

            DTUPayTransaction transaction = new DTUPayTransaction(amount, fromAccountNumber, toAccountNumber, description, new Date().getTime(), token);
            this.reportingService.saveTransaction(transaction);

            this.userService.addTransactionToUserByAccountId(toAccountNumber, transaction.getTransactionId());
            this.userService.addTransactionToUserByAccountId(fromAccountNumber, transaction.getTransactionId());

            return true;
        }

        return false;
    }

    @Override
    public boolean performRefund(DTUPayTransaction transaction) throws BankServiceException_Exception {

        this.bankService.transferMoneyFromTo(transaction.getDebtor(), transaction.getCreditor(), transaction.getAmount(), transaction.getDescription());

        DTUPayTransaction dtuPayTransaction =
                new DTUPayTransaction(
                        transaction.getAmount(),
                        transaction.getDebtor(),
                        transaction.getCreditor(),
                        transaction.getDescription() + "-> REFUND",
                        new Date().getTime(),
                        null);

        this.reportingService.saveTransaction(dtuPayTransaction);

        this.userService.addTransactionToUserByAccountId(transaction.getDebtor(), transaction.getTransactionId());
        this.userService.addTransactionToUserByAccountId(transaction.getCreditor(), transaction.getTransactionId());


        return true;
    }

    private boolean isPaymentPossible(Account account, BigDecimal requestedAmount) throws NotEnoughMoneyException {
        if (account.getBalance().compareTo(requestedAmount) != -1) {
            return true;
        }

        throw new NotEnoughMoneyException("You have not enough money.");
    }
}
