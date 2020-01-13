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
    public ArrayList<Token> getTokensByCpr(String cpr) {
        return this.tokenDatabase.getTokensByCpr(cpr);
    }

    @Override
    public ArrayList<Token> getUnusedTokensByCpr(String cpr) {
        ArrayList<Token> result = new ArrayList<>();

        for (Token token : this.getTokensByCpr(cpr)) {
            if (!token.isHasBeenUsed()) {
                result.add(token);
            }
        }

        return result;
    }

    @Override
    public Token generateToken(User customer) {

        Token token = new Token(customer.getCprNumber());

        this.tokenDatabase.saveToken(token);

        return token;
    }

    @Override
    public ArrayList<Token> generateTokens(User customer, int amount) throws TooManyTokensException {
        ArrayList<Token> result = new ArrayList<>();

        if (getTokensByCpr(customer.getCprNumber()).size() > 1) {
            throw new TooManyTokensException("The user has too many token to request for new ones.");
        }

        for (int i = 0; i < amount; i++) {
            result.add(generateToken(customer));
        }

        return result;
    }

    @Override
    public ArrayList<Token> requestForNewTokens(User customer) throws TooManyTokensException {

        ArrayList<Token> userTokens = getTokensByCpr(customer.getCprNumber());

        if (userTokens.size() > amountOfTokensToRequestForNewOnes) {
            throw new TooManyTokensException("The user has too many token to request for new ones.");
        }

        return generateTokens(customer, maxAmountOfTokens - userTokens.size());
    }

    @Override
    public void clearUserTokens(String cpr) {
        for (Token token : this.tokenDatabase.getTokensByCpr(cpr)) {
            this.tokenDatabase.getAllTokens().remove(token);
        }
    }

    @Override
    public Token validateToken(String userCpr, Token token) throws TokenValidationException {
        if (isTokenFake(userCpr, token) || token.isHasBeenUsed()) {
            throw new TokenValidationException("The token is not valid.");
        }

        return token;
    }

    @Override
    public Token useToken(Token token) {
        token.setHasBeenUsed(true);

        return token;
    }

    public boolean isTokenFake(String userCpr, Token token) {

        ArrayList<Token> tokens = this.getTokensByCpr(userCpr);

        for (Token t : tokens) {
            if (t.getValue().equals(token.getValue())) {
                return false;
            }
        }

        return true;
    }
}
