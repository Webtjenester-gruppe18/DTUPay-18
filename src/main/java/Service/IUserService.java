package Service;
import Exception.UserAlreadyExistsException;
import Database.IUserDatabase;
import Model.Customer;
import Model.Merchant;
import dtu.ws.fastmoney.User;

public interface IUserService {

    boolean customerExists(Customer customer);

    boolean merchantExists(Merchant merchant);

    String registerCustomer(Customer customer) throws UserAlreadyExistsException;

    String registerMerchant(Merchant merchant) throws UserAlreadyExistsException;

    boolean deleteCustomer(Customer customer);

    boolean deleteMerchant(Merchant merchant);

    Customer getCustomerByAccountId(String accountId);

    Merchant getMerchantByAccountId(String accountId);

    void addTransactionToUserByAccountId(String accountId, String transactionId);
}
