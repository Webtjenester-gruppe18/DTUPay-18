package Service;

import Control.ControlReg;
import Database.ITokenDatabase;
import Model.Token;
import dtu.ws.fastmoney.User;

import java.util.ArrayList;

public class TokenManager implements ITokenManager {

    private ITokenDatabase tokenDatabase = ControlReg.getTokenDatabase();

    @Override
    public ArrayList<Token> getTokensByCpr(String cpr) {
        return this.tokenDatabase.getTokensByCpr(cpr);
    }

    @Override
    public Token generateToken(User customer) {

        Token token = new Token(customer.getCprNumber());

        this.tokenDatabase.saveToken(token);

        return token;
    }

    @Override
    public ArrayList<Token> generateTokens(User customer, int amount) {
        ArrayList<Token> result = new ArrayList<>();

        for (int i = 0; i < amount; i++) {
            result.add(generateToken(customer));
        }

        return result;
    }

    @Override
    public void clearUserTokens(String cpr) {
        for (Token token : this.tokenDatabase.getTokensByCpr(cpr)) {
            this.tokenDatabase.getAllTokens().remove(token);
        }
    }
}
