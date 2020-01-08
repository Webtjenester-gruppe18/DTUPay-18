package Model;

import java.util.UUID;

public class Token {

    private String value;
    private boolean used;

    public Token() {
        this.value = UUID.randomUUID().toString();
        this.used = false;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }
}
