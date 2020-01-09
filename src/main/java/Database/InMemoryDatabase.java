package Database;

import Model.Customer;
import Model.Merchant;
import Model.Token;

import java.util.ArrayList;

public class InMemoryDatabase implements IDatabase {

    private ArrayList<Customer> customers;
    private ArrayList<Merchant> merchants;
    private ArrayList<Token> tokens;

    public InMemoryDatabase() {
        this.customers = new ArrayList<>();
        this.merchants = new ArrayList<>();
        this.tokens = new ArrayList<>();
    }

    @Override
    public void addCustomer(Customer customer) {
        this.customers.add(customer);
    }

    @Override
    public void addToken(Token token) {
        this.tokens.add(token);
    }

    @Override
    public void addSetOfTokens(ArrayList<Token> tokens) {
        this.tokens.addAll(tokens);
    }

    @Override
    public Customer getCustomer(String id) {
        for (Customer customer : this.customers) {
            if (customer.getId().equals(id)) {
                return customer;
            }
        }
        return null;
    }

    @Override
    public ArrayList<Token> getAllTokens() {
        return this.tokens;
    }

    @Override
    public boolean isACustomer(Customer customer) {
        return this.customers.contains(customer);
    }
}
