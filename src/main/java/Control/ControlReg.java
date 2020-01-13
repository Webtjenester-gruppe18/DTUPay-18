package Control;
import Bank.Bank;
import Bank.IBank;
import Database.ITokenDatabase;
import Database.InMemoryTokenDatabase;
import Exception.ExceptionContainer;
import Service.ITokenManager;
import Service.TokenManager;
import dtu.ws.fastmoney.BankService;
import Bank.InMemoryBankService;
import dtu.ws.fastmoney.BankServiceService;

public class ControlReg {
    private static ExceptionContainer exceptionContainer;
    private static IBank bank;
    private static ITokenDatabase tokenDatabase;
    private static ITokenManager tokenManager;
    private static BankService bankService;

    public static IBank getBank() {
        if (bank == null) bank = new Bank();
        return bank;
    }

    public static ExceptionContainer getExceptionContainer() {
        if (exceptionContainer == null) exceptionContainer = new ExceptionContainer();
        return exceptionContainer;
    }

    public static ITokenDatabase getTokenDatabase() {
        if (tokenDatabase == null) tokenDatabase = new InMemoryTokenDatabase();
        return tokenDatabase;
    }

    public static ITokenManager getTokenManager() {
        if (tokenManager == null) tokenManager = new TokenManager();
        return tokenManager;
    }

    public static BankService getBankService() {
//        if (bankService == null) bankService = new InMemoryBankService();
        if (bankService == null) bankService = new BankServiceService().getBankServicePort();
        return bankService;
    }
}
