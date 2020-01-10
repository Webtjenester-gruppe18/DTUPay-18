package Database;

import Model.Customer;
import Model.Merchant;
import Model.Token;

import java.util.ArrayList;

public interface IDatabase {

    void addCustomer(Customer customer);
    void addMerchant(Merchant merchant);
    void addToken(Token token);
    void addSetOfTokens(ArrayList<Token> tokens);
    Customer getCustomer(String id);
    Merchant getMerchant(String id);
    ArrayList<Token> getAllTokens();
    boolean isACustomer(Customer customer);

}
