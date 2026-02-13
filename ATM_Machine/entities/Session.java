package entities;

public class Session {

    private final Card card;
    private int failedAttempts;
    private boolean isAuthenticated;

    public Session(Card card) {
        this.card = card;
        this.failedAttempts = 0;
        this.isAuthenticated = false;
    }

    public boolean authenticate(String inputPin) {

        if (card.isBlocked()) {
            throw new IllegalStateException("Card is blocked");
        }

        if (card.validatePin(inputPin)) {
            isAuthenticated = true;
            return true;
        }

        failedAttempts++;

        if (failedAttempts >= 3) {
            card.block();
            throw new IllegalStateException("Card blocked after 3 failed attempts");
        }

        return false;
    }

    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    public void endSession() {
        isAuthenticated = false;
    }

    public Card getCard() {
        return card;
    }
}
