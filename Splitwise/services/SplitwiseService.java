package services;

import java.util.*;

import entities.*;

public class SplitwiseService {

    private Map<String, User> users;
    private Map<String, Group> groups;

    private int userIdCounter = 1;
    private int groupIdCounter = 1;

    public SplitwiseService() {
        this.users = new HashMap<>();
        this.groups = new HashMap<>();
    }

    public User addUser(String name, String email) {

        String id = "U" + userIdCounter++;
        User user = new User(id, name, email);
        users.put(id, user);
        return user;
    }

    public Group createGroup(String name, List<User> members) {

        String id = "G" + groupIdCounter++;
        Group group = new Group(id, name, members);
        groups.put(id, group);
        return group;
    }

    public void addExpense(Group group, Expense expense) {
        group.addExpense(expense);
    }

    public void showBalances(Group group, User user) {

        Map<User, Integer> balances = group.getBalanceForUser(user);

        for (Map.Entry<User, Integer> entry : balances.entrySet()) {

            if (entry.getValue() > 0) {
                System.out.println(entry.getKey().getName()
                        + " owes " + user.getName()
                        + " : " + entry.getValue());
            }
        }
    }
}
