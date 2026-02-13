package entities;

public class Card {
    private String cardNumber;
    private String encryptedPin;
    private Account account;
    private boolean isBlocked;

    public Card(String cardNumber, String encryptedPin, Account account){
        this.cardNumber = cardNumber;
        this.encryptedPin = encryptedPin;
        this.account = account;
        this.isBlocked = false;
    }

    public String getCardNumber(){
        return cardNumber;
    }

    public boolean validatePin(String inputPin) {
        return encryptedPin.equals(inputPin);
    }


    public void block(){
        isBlocked = true;
    }

    public boolean isBlocked(){
        return isBlocked;
    }

    public Account getAccount(){
        return account;
    }
}
