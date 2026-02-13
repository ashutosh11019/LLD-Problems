package entities;

public abstract class Transaction {

    protected Card card;
    protected int amount;

    public Transaction(Card card, int amount) {
        this.card = card;
        this.amount = amount;
    }

    public abstract void execute(ATM atm);
}
