package entities;

public class Account {
    private String accountNumber;
    private int balance;

    public Account(String accountNumber, int balance){
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public String getAccountNumber(){
        return accountNumber;
    }

    public boolean hasSufficientBalance(int amount){
        if(balance >= amount) return true;
        else return false;
    }

    public void debit(int amount) {
        if (!hasSufficientBalance(amount)) {
            throw new IllegalArgumentException("Insufficient balance");
        }
        balance -= amount;
    }

    public void credit(int amount){
        balance += amount;
    }

    public int getBalance(){
        return balance;
    }

}
