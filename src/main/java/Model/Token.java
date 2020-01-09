package Model;

import java.util.UUID;

public class Token {

    private String value;
    private boolean valid;

    public Token() {
        this.value = UUID.randomUUID().toString();
        this.valid = true;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
}
