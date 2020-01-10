import dtu.ws.fastmoney.*;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws BankServiceException_Exception {
        BankService bank = new BankServiceService().getBankServicePort();
/*        bank.transferMoneyFromTo(bank.getAccountByCprNumber("1212121213").getId(),
                bank.getAccountByCprNumber("1212121212").getId(), BigDecimal.valueOf(500),
                "Tak for i går");*/

      /*  User user = new User();
        user.setCprNumber("1212121213");
        user.setFirstName("John");
        user.setLastName("Doe");*/

      //  bank.createAccountWithBalance(user, BigDecimal.valueOf(10000));

 /*       for (AccountInfo a : bank.getAccounts()) {
            System.out.println(a.getAccountId());
            System.out.println(a.getUser().getFirstName() + " Balance "+ bank.getAccount(a.getAccountId()).getBalance());

          for (Transaction t: bank.getAccount(a.getAccountId()).getTransactions())
            System.out.println( "Tid "+ t.getTime()  + " Debtor "+  t.getDebtor() + " Description" + t.getDescription());

        }*/
    }
}
