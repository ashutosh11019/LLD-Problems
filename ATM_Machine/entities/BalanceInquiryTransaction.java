package entities;

public class BalanceInquiryTransaction extends Transaction {

    public BalanceInquiryTransaction(Card card) {
        super(card, 0);
    }

    @Override
    public void execute(ATM atm) {

        BankingService bankingService = atm.getBankingService();

        if (!bankingService.validateAccount(card)) {
            throw new IllegalStateException("Invalid account");
        }

        int balance = bankingService.getBalance(card.getAccount());

        System.out.println("Current balance: " + balance);
    }
}
