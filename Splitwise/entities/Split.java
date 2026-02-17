package entities;

public abstract class Split {

    protected User user;
    protected int amount;

    public Split(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}

