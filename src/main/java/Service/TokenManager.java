package Service;

import Control.ControlReg;
import Database.ITokenDatabase;
import Model.Token;
import dtu.ws.fastmoney.User;
import Exception.*;

import java.util.ArrayList;

public class TokenManager implements ITokenManager {

    private ITokenDatabase tokenDatabase = ControlReg.getTokenDatabase();
    private final int maxAmountOfTokens = 6;
    private final int amountOfTokensToRequestForNewOnes = 1;

    @Override
    public ArrayList<Token> getTokensByCpr(String cprNumber) {
        return this.tokenDatabase.getTokensByCpr(cprNumber);
    }

    @Override
    public ArrayList<Token> getUnusedTokensByCpr(String cprNumber) {
        ArrayList<Token> result = new ArrayList<>();

        for (Token token : this.getTokensByCpr(cprNumber)) {
            if (!token.isHasBeenUsed()) {
                result.add(token);
            }
        }

        return result;
    }

    @Override
    public Token generateToken(String cprNumber) {

        Token token = new Token(cprNumber);

        this.tokenDatabase.saveToken(token);

        return token;
    }

    @Override
    public ArrayList<Token> generateTokens(String cprNumber, int amount) throws TooManyTokensException {
        ArrayList<Token> result = new ArrayList<>();

        if (getTokensByCpr(cprNumber).size() > 1) {
            throw new TooManyTokensException("The user has too many token to request for new ones.");
        }

        for (int i = 0; i < amount; i++) {
            result.add(generateToken(cprNumber));
        }

        return result;
    }

    @Override
    public ArrayList<Token> requestForNewTokens(String cprNumber) throws TooManyTokensException {

        ArrayList<Token> userTokens = getTokensByCpr(cprNumber);

        if (userTokens.size() > amountOfTokensToRequestForNewOnes) {
            throw new TooManyTokensException("The user has too many token to request for new ones.");
        }

        return generateTokens(cprNumber, maxAmountOfTokens - userTokens.size());
    }

    @Override
    public void clearUserTokens(String cprNumber) {
        for (Token token : this.tokenDatabase.getTokensByCpr(cprNumber)) {
            this.tokenDatabase.getAllTokens().remove(token);
        }
    }

    @Override
    public Token validateToken(String userCprNumber, Token token) throws TokenValidationException {
        if (isTokenFake(userCprNumber, token) || token.isHasBeenUsed()) {
            throw new TokenValidationException("The token is not valid.");
        }

        return token;
    }

    @Override
    public Token useToken(Token token) {
        token.setHasBeenUsed(true);

        return token;
    }

    public boolean isTokenFake(String userCprNumber, Token token) {

        ArrayList<Token> tokens = this.getTokensByCpr(userCprNumber);

        for (Token t : tokens) {
            if (t.getValue().equals(token.getValue())) {
                return false;
            }
        }

        return true;
    }
}
