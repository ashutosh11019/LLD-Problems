import java.util.*;

import entities.*;
import services.SplitwiseService;

public class Main {

    public static void main(String[] args) {

        SplitwiseService service = new SplitwiseService();

        User alice = service.addUser("Alice", "alice@mail.com");
        User bob = service.addUser("Bob", "bob@mail.com");
        User charlie = service.addUser("Charlie", "charlie@mail.com");

        Group tripGroup = service.createGroup(
                "Goa Trip",
                Arrays.asList(alice, bob, charlie)
        );

        List<Split> equalSplits = Arrays.asList(
                new EqualSplit(alice),
                new EqualSplit(bob),
                new EqualSplit(charlie)
        );

        Expense dinner = new Expense(
                "E1",
                "Dinner",
                900,
                alice,
                equalSplits,
                new EqualSplitStrategy()
        );

        service.addExpense(tripGroup, dinner);

        List<Split> percentSplits = Arrays.asList(
                new PercentSplit(alice, 50),
                new PercentSplit(bob, 30),
                new PercentSplit(charlie, 20)
        );

        Expense hotel = new Expense(
                "E2",
                "Hotel",
                1000,
                bob,
                percentSplits,
                new PercentSplitStrategy()
        );

        service.addExpense(tripGroup, hotel);


        System.out.println("---- Balances for Alice ----");
        service.showBalances(tripGroup, alice);

        System.out.println("---- Balances for Bob ----");
        service.showBalances(tripGroup, bob);

        System.out.println("---- Balances for Charlie ----");
        service.showBalances(tripGroup, charlie);
    }
}
