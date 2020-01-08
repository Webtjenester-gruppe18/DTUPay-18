package Database;

import Model.Customer;

public interface IDatabase {

    void addCustomer(Customer customer);
    Customer getCustomer(String id);
    boolean isACustomer(Customer customer);
}
