package Helpers;

import Service.IBankService;
import dtu.ws.fastmoney.BankServiceException_Exception;

public class AccountHelper {

    public static String getAccountFirstName(String accountNumber, IBankService bank) {
        String accountFirstName = "";

        try {
            accountFirstName = bank.getAccount(accountNumber).getUser().getFirstName();
        } catch (BankServiceException_Exception e) {
            e.printStackTrace();
        }

        return accountFirstName;
    }

}
