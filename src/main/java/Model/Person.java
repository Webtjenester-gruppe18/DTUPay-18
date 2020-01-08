package Model;

import java.util.UUID;

public abstract class Person {
    private String id;
    private String name;

    public Person(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
