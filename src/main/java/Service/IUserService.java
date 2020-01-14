package Service;
import Exception.UserAlreadyExistsException;
import Database.IUserDatabase;
import dtu.ws.fastmoney.User;

public interface IUserService {

    boolean customerExists(User customer);

    boolean merchantExists(User merchant);

    String saveCustomer(User customer) throws UserAlreadyExistsException;

    String saveMerchant(User merchant) throws UserAlreadyExistsException;

    boolean deleteCustomer(User customer);

    boolean deleteMerchant(User merchant);
}
