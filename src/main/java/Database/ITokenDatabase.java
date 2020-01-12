package Database;

import Model.Token;

import java.util.ArrayList;

public interface ITokenDatabase {

    void saveToken(Token token);
    void saveSetOfTokens(ArrayList<Token> tokens);
    Token getTokenByTokenValue(String tokenValue);
    ArrayList<Token> getTokensByCpr(String cpr);
    ArrayList<Token> getAllTokens();
}
