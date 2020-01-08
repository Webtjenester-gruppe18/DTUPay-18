package Model;

public class Transaction {

    private double amount;
    private Customer customer;
    private Merchant merchant;
    private Token token;

    public Transaction(double amount, Customer customer, Merchant merchant, Token token) {
        this.amount = amount;
        this.customer = customer;
        this.merchant = merchant;
        this.token = token;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }
}
