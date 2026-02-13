package entities;

import java.util.HashMap;
import java.util.Map;

import enums.Denomination;

public class CashDispenser {
    private CashInventory cashInventory;

    public CashDispenser(CashInventory cashInventory){
        this.cashInventory = cashInventory;
    }

    public CashInventory getCashInventory(){
        return cashInventory;
    }

    public Map<Denomination, Integer> dispense(int amount) {

        if (!cashInventory.hasSufficientCash(amount)) {
            throw new IllegalArgumentException("Insufficient cash in ATM");
        }

        int remaining = amount;
        Map<Denomination, Integer> dispensedNotes = new HashMap<>();

        for (Denomination denomination : Denomination.values()) {

            int noteValue = denomination.getValue();
            int availableNotes = cashInventory.getNoteCount(denomination);

            int requiredNotes = remaining / noteValue;
            int notesToDispense = Math.min(requiredNotes, availableNotes);

            if (notesToDispense > 0) {
                dispensedNotes.put(denomination, notesToDispense);
                remaining -= notesToDispense * noteValue;
            }
        }

        if (remaining != 0) {
            throw new IllegalArgumentException("Cannot dispense exact amount");
        }

        cashInventory.deductCash(dispensedNotes);

        return dispensedNotes;
    }

}
