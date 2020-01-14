package Database;

import dtu.ws.fastmoney.User;
import io.cucumber.java.bs.A;

import java.util.ArrayList;
import Exception.UserNotFoundException;

public class InMomoryUserDatabase implements IUserDatabase {

    ArrayList<User> customers = new ArrayList<>();
    ArrayList<User> merchants = new ArrayList<>();


    @Override
    public User getCustomer(String cprNumber) throws UserNotFoundException {

        for (User currentCustomer : this.customers) {
            if (currentCustomer.getCprNumber().equals(cprNumber)) {
                return currentCustomer;
            }
        }
        throw new UserNotFoundException("Could not find a user on that cprNumber" + cprNumber);
    }

    @Override
    public User getMerchant(String cprNumber) {
        return null;
    }

    @Override
    public ArrayList<User> getAllCustomers() {
        return this.customers;
    }

    @Override
    public ArrayList<User> getAllMerchants() {
        return this.merchants;
    }

    @Override
    public String saveCustomer(User customer) {

        this.customers.add(customer);

        return customer.getCprNumber();
    }

    @Override
    public String saveMerchant(User merchant) {

        this.merchants.add(merchant);

        return merchant.getCprNumber();
    }
}
