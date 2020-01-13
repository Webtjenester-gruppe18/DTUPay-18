package Database;

import Model.Token;

import java.util.ArrayList;

public class InMemoryTokenDatabase implements ITokenDatabase {

    private ArrayList<Token> generatedTokens = new ArrayList<>();

    @Override
    public void saveToken(Token token) {
        this.generatedTokens.add(token);
    }

    @Override
    public void saveSetOfTokens(ArrayList<Token> tokens) {
        this.generatedTokens.addAll(tokens);
    }

    @Override
    public Token getTokenByTokenValue(String tokenValue) {
        for (Token token : this.generatedTokens) {
            if (token.getValue().equals(tokenValue)) {
                return token;
            }
        }

        return null;
    }

    @Override
    public ArrayList<Token> getTokensByCpr(String cpr) {

        ArrayList<Token> result = new ArrayList<>();

        for (Token token : this.generatedTokens) {
            if (token.getCustomerCpr().equals(cpr)) {
                result.add(token);
            }
        }

        return result;
    }

    @Override
    public ArrayList<Token> getAllTokens() {
        return this.generatedTokens;
    }
}
