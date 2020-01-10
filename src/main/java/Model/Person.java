package Model;

import java.util.UUID;

public abstract class Person {
    private String id;
    private String name;
    private Account account;

    public Person(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.account = new Account();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Account getAccount() {
        return account;
    }
}
