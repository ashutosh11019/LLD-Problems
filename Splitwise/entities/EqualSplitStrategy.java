package entities;

import java.util.List;

public class EqualSplitStrategy implements SplitStrategy {

    @Override
    public void validateAndCalculate(int totalAmount, List<Split> splits) {

        if (splits == null || splits.isEmpty()) {
            throw new IllegalArgumentException("Splits cannot be empty");
        }

        int size = splits.size();
        int baseAmount = totalAmount / size;
        int remainder = totalAmount % size;

        for (Split split : splits) {
            split.setAmount(baseAmount);
        }

        if (remainder > 0) {
            splits.get(0).setAmount(baseAmount + remainder);
        }
    }
}
