package entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Group {
    private String id;
    private String name;
    private List<User> members;
    private List<Expense> expenses;
    private Map<User, Map<User, Integer>> balances;

    public Group(String id, String name, List<User> members){
        this.id = id;
        this.name = name;
        this.members = new ArrayList<>(members);
        this.expenses = new ArrayList<>();
        this.balances = new HashMap<>();
        initializeBalances();
    }

    private void initializeBalances() {
        for (User u1 : members) {
            balances.put(u1, new HashMap<>());
            for (User u2 : members) {
                if (!u1.equals(u2)) {
                    balances.get(u1).put(u2, 0);
                }
            }
        }
    }

    public String getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public List<User> getMembers(){
        return members;
    }

    public List<Expense> getExpenses(){
        return expenses;
    }

    public Map<User, Map<User, Integer>> getBalances(){
        return balances;
    }

    public void addMember(User user){
        this.members.add(user);
    }

    public void addExpense(Expense expense) {
        if (!members.contains(expense.getPaidBy())) {
            throw new IllegalArgumentException("Payer not part of group");
        }
        for (Split split : expense.getSplits()) {
            if (!members.contains(split.getUser())) {
                throw new IllegalArgumentException("Split user not part of group");
            }
        }
        expense.validateAndCalculate();
        User payer = expense.getPaidBy();
        for (Split split : expense.getSplits()) {
            User user = split.getUser();
            int splitAmount = split.getAmount();
            if (user.equals(payer)) continue;
            balances.get(payer).put(user,balances.get(payer).get(user) + splitAmount);
            balances.get(user).put(payer,balances.get(user).get(payer) - splitAmount);
        }
        expenses.add(expense);
    }


    public int getBalance(User u1, User u2){
        return balances.get(u1).get(u2);
    }

    public Map<User, Integer> getBalanceForUser(User user){
        return this.balances.get(user);
    }
}
