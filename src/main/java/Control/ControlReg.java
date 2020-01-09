package Control;

import Database.IDatabase;
import Database.InMemoryDatabase;
import Exception.ExceptionContainer;

public class ControlReg {
    private static IDatabase database;
    private static ExceptionContainer exceptionContainer;

    public static IDatabase getDatabase() {
        if (database == null) database = new InMemoryDatabase(); return database;
    }

    public static ExceptionContainer getExceptionContainer() {
        if (exceptionContainer == null) exceptionContainer = new ExceptionContainer(); return exceptionContainer;
    }
}
