package Model;

public class Token {

    private String value;
    private boolean used;

    public Token(String value) {
        this.value = value;
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
