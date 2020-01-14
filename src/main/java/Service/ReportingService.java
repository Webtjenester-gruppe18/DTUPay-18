package Service;

import Control.ControlReg;
import dtu.ws.fastmoney.BankServiceException_Exception;
import dtu.ws.fastmoney.Transaction;
import java.util.List;

public class ReportingService implements IReportingService {

    private IBankService bankService = ControlReg.getBankService();

    @Override
    public List<Transaction> getTransactionsByCpr(String cpr) throws BankServiceException_Exception {
        return this.bankService.getAccountByCpr(cpr).getTransactions();
    }
}
