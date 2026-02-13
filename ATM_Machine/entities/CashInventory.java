package entities;

import java.util.Map;

import enums.Denomination;

public class CashInventory {
    private Map<Denomination, Integer> noteCountMap;

    public CashInventory(Map<Denomination, Integer> noteCountMap) {
        this.noteCountMap = noteCountMap;
    }

    public boolean hasSufficientCash(int amount) {

        int remaining = amount;

        for (Denomination denomination : Denomination.values()) {

            int noteValue = denomination.getValue();
            int availableNotes = getNoteCount(denomination);

            int requiredNotes = remaining / noteValue;
            int notesToUse = Math.min(requiredNotes, availableNotes);

            remaining -= notesToUse * noteValue;
        }

        return remaining == 0;
    }


    public int getNoteCount(Denomination d) {
        return noteCountMap.getOrDefault(d, 0);

    }

    public void deductCash(Map<Denomination, Integer> notesToDeduct) {
        for (Denomination denomination : notesToDeduct.keySet()) {
            noteCountMap.put(denomination, noteCountMap.get(denomination)-notesToDeduct.get(denomination));
        }
    }

    public void addCash(Map<Denomination, Integer> notesToAdd) {
        for (Denomination denomination : notesToAdd.keySet()) {
            noteCountMap.put(denomination, noteCountMap.get(denomination)+notesToAdd.get(denomination));
        }
    }
}
