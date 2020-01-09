package Control;

import Database.IDatabase;
import Database.InMemoryDatabase;

public class ControlReg {
    private static IDatabase database;

    public static IDatabase getDatabase() {
        if (database == null) database = new InMemoryDatabase(); return database;
    }
}
