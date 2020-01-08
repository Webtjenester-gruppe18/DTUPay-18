package Database;

import Model.Customer;
import Model.Merchant;

import java.util.ArrayList;

public class InMemoryDatabase implements IDatabase {

    private ArrayList<Customer> customers;
    private ArrayList<Merchant> merchants;

    public InMemoryDatabase() {
        this.customers = new ArrayList<>();
        this.merchants = new ArrayList<>();
    }

    @Override
    public void addCustomer(Customer customer) {
        this.customers.add(customer);
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
    public boolean isACustomer(Customer customer) {
        return this.customers.contains(customer);
    }
}
