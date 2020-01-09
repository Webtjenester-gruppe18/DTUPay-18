package Control;

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

    public static IDatabase getDatabase() {
        if (database == null) database = new InMemoryDatabase(); return database;
    }

    public static ExceptionContainer getExceptionContainer() {
        if (exceptionContainer == null) exceptionContainer = new ExceptionContainer(); return exceptionContainer;
    }

    public static TokenService getTokenService() {
        if (tokenService == null) tokenService = new TokenService(); return tokenService;
    }

    public static ValidationService getValidationService() {
        if (validationService == null) validationService = new ValidationService(); return validationService;
    }
}
