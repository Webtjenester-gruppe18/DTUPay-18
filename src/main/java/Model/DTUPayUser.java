package Model;

import java.util.ArrayList;

public abstract class DTUPayUser {

    private String accountId;
    private String firstName;
    private String lastName;
    private String cprNumber;
    ArrayList<String> transactionIds;

    public DTUPayUser() {
    }

    public DTUPayUser(String accountId, String firstName, String lastName, String cprNumber) {
        this.accountId = accountId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.cprNumber = cprNumber;

        this.transactionIds = new ArrayList<>();

        System.out.println("TEST " + this.transactionIds.size());
    }

    public String getAccountId() {
        return accountId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCprNumber() {
        return cprNumber;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setCprNumber(String cprNumber) {
        this.cprNumber = cprNumber;
    }

    public ArrayList<String> getTransactionIds() {
        return transactionIds;
    }

    public void setTransactionIds(ArrayList<String> transactionIds) {
        this.transactionIds = transactionIds;
    }
}
