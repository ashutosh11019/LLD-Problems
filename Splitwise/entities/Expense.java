package entities;

import java.util.List;

public class Expense {

    private String id;
    private String description;
    private int amount;
    private User paidBy;
    private List<Split> splits;
    private SplitStrategy strategy;

    public Expense(String id,
                   String description,
                   int amount,
                   User paidBy,
                   List<Split> splits,
                   SplitStrategy strategy) {

        this.id = id;
        this.description = description;
        this.amount = amount;
        this.paidBy = paidBy;
        this.splits = splits;
        this.strategy = strategy;
    }

    public void validateAndCalculate() {
        strategy.validateAndCalculate(amount, splits);
    }

    public User getPaidBy() { return paidBy; }
    public List<Split> getSplits() { return splits; }
}
