package Service;

import Model.Customer;
import Model.Token;
import Exception.TokenValidationException;

import java.util.ArrayList;

public class ValidationService {

    public Token validateToken(Customer customer, Token token) throws TokenValidationException {

        if (isTokenFake(customer, token) || !token.isHasBeenUsed()) {
            throw new TokenValidationException("The token is not valid.");
        }

        return token;
    }

    public boolean isTokenFake(Customer customer, Token token) {

        ArrayList<Token> tokens = customer.getTokens();

        for (Token t : tokens) {
            if (t.getValue().equals(token.getValue())) {
                return false;
            }
        }

        return true;
    }
}
