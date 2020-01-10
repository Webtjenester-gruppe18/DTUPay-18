package Control;
import Bank.Bank;
import Bank.IBank;
import Database.IDatabase;
import Database.InMemoryDatabase;
import Exception.ExceptionContainer;
import Service.TokenService;
import Service.ValidationService;

public class ControlReg {
    private static IDatabase database;
    private static ExceptionContainer exceptionContainer;
    private static TokenService tokenService;
    private static ValidationService validationService;
    private static IBank bank;

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
}

