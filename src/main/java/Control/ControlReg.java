package Control;
import Bank.Bank;
import Bank.IBank;
import Database.IDatabase;
import Database.ITokenDatabase;
import Database.InMemoryDatabase;
import Database.InMemoryTokenDatabase;
import Exception.ExceptionContainer;
import Service.ITokenManager;
import Service.TokenManager;
import Service.TokenService;
import Service.ValidationService;
import dtu.ws.fastmoney.BankService;
import Bank.InMemoryBankService;

public class ControlReg {
    private static IDatabase database;
    private static ExceptionContainer exceptionContainer;
    private static TokenService tokenService;
    private static ValidationService validationService;
    private static IBank bank;
    private static ITokenDatabase tokenDatabase;
    private static ITokenManager tokenManager;
    private static BankService bankService;

    public static IDatabase getDatabase() {
        if (database == null) database = new InMemoryDatabase();
        return database;
    }

    public static IBank getBank() {
        if (bank == null) bank = new Bank();
        return bank;
    }

    public static ExceptionContainer getExceptionContainer() {
        if (exceptionContainer == null) exceptionContainer = new ExceptionContainer();
        return exceptionContainer;
    }

    public static TokenService getTokenService() {
        if (tokenService == null) tokenService = new TokenService();
        return tokenService;
    }

    public static ValidationService getValidationService() {
        if (validationService == null) validationService = new ValidationService();
        return validationService;
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
        if (bankService == null) bankService = new InMemoryBankService();
        return bankService;
    }
}

