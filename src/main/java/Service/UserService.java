package Service;
import Exception.UserAlreadyExistsException;
import Control.ControlReg;
import Database.IUserDatabase;
import dtu.ws.fastmoney.User;

public class UserService implements IUserService {

    IUserDatabase database = ControlReg.getUserDatabase();
    
    @Override
    public boolean customerExists(User customer) {

        for (User currentCustomer : database.getAllCustomers()) {
            if (currentCustomer.equals(customer)) {
                return true;
            }
        }
        
        return false;
    }


    @Override
    public boolean merchantExists(User merchant) {
        return false;
    }

    @Override
    public String saveCustomer(User customer) throws UserAlreadyExistsException {

        if (customerExists(customer)){
            throw new UserAlreadyExistsException("This user already exists in the database.");
        }

        return this.database.saveCustomer(customer);
    }

    @Override
    public String saveMerchant(User merchant) {
        return null;
    }
}
