package entities;

import java.util.List;

public class ExactSplitStrategy implements SplitStrategy {

    @Override
    public void validateAndCalculate(int totalAmount, List<Split> splits) {

        int sum = 0;
        for (Split split : splits) {
            sum += split.getAmount();
        }

        if (sum != totalAmount) {
            throw new IllegalArgumentException("Exact splits do not sum to total amount");
        }
    }
}
