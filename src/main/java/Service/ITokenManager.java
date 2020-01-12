package Service;

import Model.Customer;
import Model.Token;
import dtu.ws.fastmoney.User;
import Exception.*;

import java.util.ArrayList;

public interface ITokenManager {

    ArrayList<Token> getTokensByCpr(String cpr);
    Token generateToken(User customer);
    ArrayList<Token> generateTokens(User customer, int amount) throws TooManyTokensException;
    ArrayList<Token> requestForNewTokens(User customer) throws TooManyTokensException;
    void clearUserTokens(String cpr);
    Token validateToken(User customer, Token token) throws TokenValidationException;
}
