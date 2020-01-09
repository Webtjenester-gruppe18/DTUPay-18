package Model;

import java.util.ArrayList;

public class Customer extends Person {
    private ArrayList<Token> tokens;

    public Customer(String name) {
        super(name);
        this.tokens = new ArrayList<>();
    }

    public ArrayList<Token> getTokens() {
        return tokens;
    }

    public void setTokens(ArrayList<Token> tokens) {
        this.tokens = tokens;
    }

    public void addTokens(ArrayList<Token> tokens) {
        this.tokens.addAll(tokens);
    }
}
