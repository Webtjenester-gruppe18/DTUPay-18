package Database;
import Exception.UserNotFoundException;
import Model.Customer;
import Model.Merchant;
import dtu.ws.fastmoney.User;

import java.util.ArrayList;

public interface IUserDatabase {


    Customer getCustomer(String cprNumber) throws UserNotFoundException;

    Merchant getMerchant(String cprNumber) throws UserNotFoundException;

    ArrayList<Customer> getAllCustomers();

    ArrayList<Merchant> getAllMerchants();

    String saveCustomer(Customer customer);

    String saveMerchant(Merchant merchant);

    boolean deleteCustomer(Customer customer);

    boolean deleteMerchant(Merchant merchant);
}
