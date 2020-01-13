package Bank;

import dtu.ws.fastmoney.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InMemoryBankService implements BankService {

    private ArrayList<Account> accounts = new ArrayList<>();
    private ArrayList<AccountInfo> accountInfo = new ArrayList<>();

    @Override
    public Account getAccount(String accountId) throws BankServiceException_Exception {
        for (Account account : this.accounts) {
            if (account.getId().equals(accountId)) {
                return account;
            }
        }

        throw new BankServiceException_Exception("Could not find a account with the requested account id.", new BankServiceException());
    }

    @Override
    public Account getAccountByCprNumber(String cpr) throws BankServiceException_Exception {
        for (Account account : this.accounts) {
            if (account.getUser().getCprNumber().equals(cpr)) {
                return account;
            }
        }

        throw new BankServiceException_Exception("Could not find a account with the requested user cpr number.", new BankServiceException());
    }

    @Override
    public String createAccountWithBalance(User user, BigDecimal balance) throws BankServiceException_Exception {
        Account newAccount = new Account();
        newAccount.setId(UUID.randomUUID().toString());
        newAccount.setUser(user);
        newAccount.setBalance(balance);

        this.accounts.add(newAccount);

        AccountInfo newAccountInfo = new AccountInfo();
        newAccountInfo.setUser(user);
        newAccountInfo.setAccountId(newAccount.getId());

        this.accountInfo.add(newAccountInfo);

        return newAccount.getId();
    }

    @Override
    public void retireAccount(String accountId) throws BankServiceException_Exception {
        Account accountToRetire = null;

        for (Account account : this.accounts) {
            if (account.getId().equals(accountId)) {
                accountToRetire = account;
                break;
            }
        }

        if (accountToRetire == null) {
            throw new BankServiceException_Exception("Could not find a account with the requested account id.", new BankServiceException());
        }

        this.accounts.remove(accountToRetire);

        AccountInfo accountInfoToRetire = null;

        for (AccountInfo accountInfo : this.accountInfo) {
            if (accountInfo.getAccountId().equals(accountId)) {
                accountInfoToRetire = accountInfo;
                break;
            }
        }

        if (accountInfoToRetire == null) {
            throw new BankServiceException_Exception("Could not find a accountInfo with the requested account id.", new BankServiceException());
        }

        this.accountInfo.remove(accountInfoToRetire);
    }

    @Override
    public List<AccountInfo> getAccounts() {
        return this.accountInfo;
    }

    @Override
    public void transferMoneyFromTo(String debtor, String creditor, BigDecimal amount, String description) throws BankServiceException_Exception {
        Account debtorAccount = this.getAccount(debtor);
        Account creditorAccount = this.getAccount(creditor);

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setDebtor(debtor);
        transaction.setCreditor(creditor);
        transaction.setDescription(description);

        // moving the amount
        debtorAccount.setBalance(debtorAccount.getBalance().subtract(amount));
        creditorAccount.setBalance(creditorAccount.getBalance().add(amount));

        //TODO: we need to put a time, and the balance. I have no idea what the balance is
    }
}
