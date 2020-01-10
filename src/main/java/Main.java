import dtu.ws.fastmoney.*;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws BankServiceException_Exception {
        BankService bank = new BankServiceService().getBankServicePort();
//        User user = new User();
//        user.setCprNumber("1212121212");
//        user.setFirstName("Jane");
//        user.setLastName("Doe");
//
//        bank.createAccountWithBalance(user, BigDecimal.valueOf(10000));


     /*   for (AccountInfo a : bank.getAccounts()) {
            System.out.println(a.getUser().getFirstName() + " Balance "+ bank.getAccount(a.getAccountId()).getBalance());

        }*/
    }
}
