package Service;

import Control.ControlReg;
import Database.IDatabase;
import Exception.TokenValidationException;
import Model.Customer;
import Model.Merchant;
import Model.Token;
import Exception.TooManyTokensException;
import Model.Transaction;

import java.util.ArrayList;

public class TokenService {

    private final int maxAmountOfTokens = 6;
    private IDatabase database = ControlReg.getDatabase();

    public ArrayList<Token> generateTokens(int amount) {
        ArrayList<Token> res = new ArrayList<>();

        for (int i = 0; i < amount; i++) {
            res.add(new Token());
        }

        return res;
    }

    public ArrayList<Token> requestNewTokens(ArrayList<Token> exitingTokens) throws TooManyTokensException {
        ArrayList<Token> generatedTokens = new ArrayList<>();

        if (exitingTokens.size() < 2) {
            generatedTokens.addAll(generateTokens(maxAmountOfTokens - exitingTokens.size()));
        } else {
            throw new TooManyTokensException("You have too many tokens to get new ones.");
        }

        return generatedTokens;
    }

    public void useToken(Customer customer, Token token) throws TokenValidationException {
        if (ControlReg.getValidationService().validateToken(customer, token) != null) {
            token.setHasBeenUsed(false);
            removeTokenFromCustomer(customer, token);
        }
    }

    private void removeTokenFromCustomer(Customer customer, Token token) {
        customer.getTokens().remove(token);
    }

    private void performTransaction(Transaction transaction) {
        transaction.getCustomer().getAccount().setBalance(transaction.getCustomer().getAccount().getBalance() - transaction.getAmount());
        transaction.getMerchant().getAccount().setBalance(transaction.getMerchant().getAccount().getBalance() + transaction.getAmount());

        transaction.getCustomer().getAccount().getTransactions().add(transaction);
        transaction.getMerchant().getAccount().getTransactions().add(transaction);
    }

    public void makePayment(double amount, Customer customer, Merchant merchant, Token token) throws TokenValidationException {

        useToken(customer, token);

        Transaction transaction = new Transaction(amount, customer, merchant, token);
        performTransaction(transaction);
    }
}
