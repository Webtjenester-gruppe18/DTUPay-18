package Service;

import Model.Customer;
import Model.Token;
import dtu.ws.fastmoney.User;

import java.util.ArrayList;

public interface ITokenManager {

    ArrayList<Token> getTokensByCpr(String cpr);
    Token generateToken(User customer);
    ArrayList<Token> generateTokens(User customer, int amount);
    void clearUserTokens(String cpr);
}
