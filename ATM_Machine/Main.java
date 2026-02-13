import java.util.HashMap;
import java.util.Map;

import entities.*;
import enums.Denomination;

public class Main {

    public static void main(String[] args) {

        // 1 Create Account
        Account account = new Account("ACC123", 10000);

        // 2 Create Card
        Card card = new Card("CARD123", "1234", account);

        // 3 Setup Cash Inventory
        Map<Denomination, Integer> initialNotes = new HashMap<>();
        initialNotes.put(Denomination.TWO_THOUSAND, 5);
        initialNotes.put(Denomination.FIVE_HUNDRED, 10);
        initialNotes.put(Denomination.TWO_HUNDRED, 10);
        initialNotes.put(Denomination.ONE_HUNDRED, 20);

        CashInventory cashInventory = new CashInventory(initialNotes);

        // 4 Create Cash Dispenser
        CashDispenser cashDispenser = new CashDispenser(cashInventory);

        // 5 Create Banking Service
        BankingService bankingService = new BankingService();

        // 6 Create ATM
        ATM atm = new ATM(bankingService, cashDispenser);

        // 7 Insert Card
        atm.insertCard(card);

        // 8 Authenticate
        try {
            atm.getCurrentSession().authenticate("1234");
            System.out.println("Authentication successful");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }

        // 9 Withdraw Money
        try {
            Transaction withdraw = new WithdrawTransaction(card, 3000);
            atm.processTransaction(withdraw);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // 10 Check Balance
        try {
            Transaction balanceInquiry = new BalanceInquiryTransaction(card);
            atm.processTransaction(balanceInquiry);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // 11 Deposit Money
        try {
            Transaction deposit = new DepositTransaction(card, 2000);
            atm.processTransaction(deposit);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // 12 Check Balance Again
        try {
            Transaction balanceInquiry2 = new BalanceInquiryTransaction(card);
            atm.processTransaction(balanceInquiry2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // 13 Eject Card
        atm.ejectCard();
        System.out.println("Card ejected");
    }
}
