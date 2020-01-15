package Service;

import dtu.ws.fastmoney.BankServiceException_Exception;
import dtu.ws.fastmoney.Transaction;
import java.util.List;

public interface IReportingService {

    List<Transaction> getTransactionsByCpr(String cpr) throws BankServiceException_Exception;

}
