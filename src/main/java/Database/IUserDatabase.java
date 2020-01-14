package Database;
import Exception.UserNotFoundException;
import dtu.ws.fastmoney.User;

import java.util.ArrayList;

public interface IUserDatabase {


    User getCustomer(String cprNumber) throws UserNotFoundException;

    User getMerchant(String cprNumber);

    ArrayList<User> getAllCustomers();

    ArrayList<User> getAllMerchants();

    String saveCustomer(User customer);

    String saveMerchant(User merchant);
}
