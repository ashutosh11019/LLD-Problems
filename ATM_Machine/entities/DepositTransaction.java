package entities;

import java.util.Map;
import enums.Denomination;

public class DepositTransaction extends Transaction {

    public DepositTransaction(Card card, int amount) {
        super(card, amount);
    }

    @Override
    public void execute(ATM atm) {
        BankingService bankingService = atm.getBankingService();
        CashDispenser cashDispenser = atm.getCashDispenser();
        if (!bankingService.validateAccount(card)) {
            throw new IllegalStateException("Invalid account");
        }
        bankingService.credit(card.getAccount(), amount);
        Map<Denomination, Integer> depositNotes = Map.of(Denomination.ONE_HUNDRED, amount / 100);
        cashDispenser.getCashInventory().addCash(depositNotes);
        System.out.println("Deposit successful: " + amount);
    }
}
