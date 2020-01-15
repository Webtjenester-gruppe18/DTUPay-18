package Service;
import Exception.UserAlreadyExistsException;
import Control.ControlReg;
import Database.IUserDatabase;
import Model.Customer;
import Model.Merchant;
import dtu.ws.fastmoney.User;

public class UserService implements IUserService {

    IUserDatabase database = ControlReg.getUserDatabase();
    
    @Override
    public boolean customerExists(Customer customer) {

        for (Customer currentCustomer : database.getAllCustomers()) {
            if (currentCustomer.equals(customer)) {
                return true;
            }
        }
        
        return false;
    }


    @Override
    public boolean merchantExists(Merchant merchant) {
        for (Merchant currentMerchant : database.getAllMerchants()) {
            if (currentMerchant.equals(merchant)){
                return true;
            }
        }

        return false;
    }

    @Override
    public String registerCustomer(Customer customer) throws UserAlreadyExistsException {

        if (customerExists(customer)){
            throw new UserAlreadyExistsException("This user already exists in the database.");
        }

        return this.database.saveCustomer(customer);
    }

    @Override
    public String registerMerchant(Merchant merchant) throws UserAlreadyExistsException {
        if (merchantExists(merchant)) {
            throw new UserAlreadyExistsException("This user already exists in the database.");
        }

        return this.database.saveMerchant(merchant);
    }

    @Override
    public boolean deleteCustomer(Customer customer) {

        return this.database.deleteCustomer(customer);
    }

    @Override
    public boolean deleteMerchant(Merchant merchant) {
        return this.database.deleteMerchant(merchant);
    }

    @Override
    public Customer getCustomerByAccountId(String accountId) {
        return null;
    }

    @Override
    public Merchant getMerchantByAccountId(String accountId) {
        return null;
    }

    @Override
    public void addTransactionToUserByAccountId(String accountId, String transactionId) {
        this.database.addTransactionToUserByAccountId(accountId, transactionId);
    }
}
