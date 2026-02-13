package entities;

public class WithdrawTransaction extends Transaction {

    public WithdrawTransaction(Card card, int amount) {
        super(card, amount);
    }

    @Override
    public void execute(ATM atm) {
        BankingService bankingService = atm.getBankingService();
        CashDispenser cashDispenser = atm.getCashDispenser();
        if (!bankingService.validateAccount(card)) {
            throw new IllegalStateException("Invalid account");
        }
        if (!bankingService.hasSufficientBalance(card.getAccount(), amount)) {
            throw new IllegalStateException("Insufficient account balance");
        }
        cashDispenser.dispense(amount);
        bankingService.debit(card.getAccount(), amount);
        System.out.println("Withdrawal successful: " + amount);
    }
}
