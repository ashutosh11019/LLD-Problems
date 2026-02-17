package entities;

import java.util.List;

public class PercentSplitStrategy implements SplitStrategy {

    @Override
    public void validateAndCalculate(int totalAmount, List<Split> splits) {

        double totalPercent = 0;

        for (Split split : splits) {
            PercentSplit ps = (PercentSplit) split;
            totalPercent += ps.getPercent();
        }

        if (Math.abs(totalPercent - 100.0) > 0.001) {
            throw new IllegalArgumentException("Total percent must be 100");
        }

        int calculatedTotal = 0;

        for (Split split : splits) {
            PercentSplit ps = (PercentSplit) split;
            int amount = (int) (totalAmount * ps.getPercent() / 100);
            split.setAmount(amount);
            calculatedTotal += amount;
        }

        int diff = totalAmount - calculatedTotal;
        if (diff != 0) {
            splits.get(0).setAmount(splits.get(0).getAmount() + diff);
        }
    }
}
