package Service;

import Control.ControlReg;
import Database.IDatabase;
import Exception.TokenValidationException;
import Model.Token;
import Exception.TooManyTokensException;
import java.util.ArrayList;

public class TokenService {

    private final int maxAmountOfTokens = 6;
    private IDatabase database = ControlReg.getDatabase();

    public ArrayList<Token> generateTokens(int amount) {
        ArrayList<Token> res = new ArrayList<>();

        for (int i = 0; i < amount; i++) {
            res.add(new Token());
        }

        return res;
    }

    public ArrayList<Token> requestNewTokens(ArrayList<Token> exitingTokens) throws TooManyTokensException {
        ArrayList<Token> generatedTokens = new ArrayList<>();

        if (exitingTokens.size() < 2) {
            generatedTokens.addAll(generateTokens(maxAmountOfTokens - exitingTokens.size()));
        } else {
            throw new TooManyTokensException("You have too many tokens to get new ones.");
        }

        return generatedTokens;
    }

    public boolean validateToken(Token token) throws TokenValidationException {

        if (isTokenFake(token) || !token.isValid()) {
            throw new TokenValidationException("The token is not valid.");
        }

        return token.isValid();
    }

    public boolean isTokenFake(Token token) {

        ArrayList<Token> tokens = this.database.getAllTokens();

        for (Token t : tokens) {
            if (t.getValue().equals(token.getValue())) {
                return false;
            }
        }

        return true;
    }
}
