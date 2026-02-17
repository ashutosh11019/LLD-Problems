package entities;

import java.util.List;

public interface SplitStrategy {
    void validateAndCalculate(int totalAmount, List<Split> splits);
}
