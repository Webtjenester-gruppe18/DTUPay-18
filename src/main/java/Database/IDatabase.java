package Database;

import Model.Customer;
import Model.Token;

import java.util.ArrayList;

public interface IDatabase {

    void addCustomer(Customer customer);
    void addToken(Token token);
    void addSetOfTokens(ArrayList<Token> tokens);
    Customer getCustomer(String id);
    ArrayList<Token> getAllTokens();
    boolean isACustomer(Customer customer);

}
