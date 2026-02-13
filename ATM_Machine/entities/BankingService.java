package entities;

public class BankingService {
    public boolean validateAccount(Card card) {
        return card != null && !card.isBlocked() && card.getAccount() != null;
    }

    public boolean hasSufficientBalance(Account account, int amount) {
        return account != null && account.hasSufficientBalance(amount);
    }

    public void debit(Account account, int amount) {
        account.debit(amount);
    }

    public void credit(Account account, int amount) {
        account.credit(amount);
    }

    public int getBalance(Account account) {
        return account.getBalance();
    }
}
