package Service;

import Model.Token;
import dtu.ws.fastmoney.User;
import Exception.*;

import java.util.ArrayList;

public interface ITokenManager {

    ArrayList<Token> getTokensByCpr(String cprNumber);
    ArrayList<Token> getUnusedTokensByCpr(String cprNumber);
    Token generateToken(String cprNumber);
    ArrayList<Token> generateTokens(String cprNumber, int amount) throws TooManyTokensException;
    ArrayList<Token> requestForNewTokens(String cprNumber) throws TooManyTokensException;
    void clearUserTokens(String cprNumber);
    Token validateToken(String userCprNumber, Token token) throws TokenValidationException;
    Token useToken(Token token);
}
