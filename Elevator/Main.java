import java.util.ArrayList;
import java.util.List;

import model.*;
import services.*;
import enums.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Elevator System Simulator ===");
        
        ElevatorController controller = new ElevatorController();
        
        // Add 2 elevators
        Elevator elevator1 = new Elevator(0); // starts at floor 0
        Elevator elevator2 = new Elevator(5); // starts at floor 5
        
        controller.addElevator(elevator1);
        controller.addElevator(elevator2);
        
        System.out.println("Initialized Elevator 1 at floor " + elevator1.getCurrentFloor());
        System.out.println("Initialized Elevator 2 at floor " + elevator2.getCurrentFloor());
        
        // Press external button: floor 3 wants to go UP
        System.out.println("\n--- Submitting Request 1: External Up Request at Floor 3 ---");
        ExternalButton extUp3 = new ExternalButton(3, Direction.UP, controller);
        extUp3.pressButton();
        
        // Press external button: floor 7 wants to go DOWN
        System.out.println("\n--- Submitting Request 2: External Down Request at Floor 7 ---");
        ExternalButton extDown7 = new ExternalButton(7, Direction.DOWN, controller);
        extDown7.pressButton();
        
        // Let's run a few steps
        runSteps(controller, 5);
        
        // Passenger inside Elevator 1 selects floor 8 (going UP)
        System.out.println("\n--- Passenger inside Elevator 1 presses Internal Button for Floor 8 ---");
        InternalButton intButton8 = new InternalButton(8, elevator1);
        intButton8.pressButton();
        
        // Passenger inside Elevator 2 selects floor 2 (going DOWN)
        System.out.println("\n--- Passenger inside Elevator 2 presses Internal Button for Floor 2 ---");
        InternalButton intButton2 = new InternalButton(2, elevator2);
        intButton2.pressButton();
        
        // Run until all requests are completed
        System.out.println("\n--- Running simulation until all requests are served ---");
        int stepCount = 0;
        while (hasAnyPendingRequests(controller) && stepCount < 30) {
            stepCount++;
            System.out.println("\n--- Step " + stepCount + " ---");
            controller.step();
            printElevatorStates(controller);
        }
        
        System.out.println("\nSimulation finished. All requests served.");
    }
    
    private static void runSteps(ElevatorController controller, int steps) {
        for (int i = 1; i <= steps; i++) {
            System.out.println("\n--- Step (Setup Phase) " + i + " ---");
            controller.step();
            printElevatorStates(controller);
        }
    }
    
    private static boolean hasAnyPendingRequests(ElevatorController controller) {
        for (Elevator elevator : controller.getElevators()) {
            if (elevator.hasPendingRequests()) {
                return true;
            }
        }
        return false;
    }
    
    private static void printElevatorStates(ElevatorController controller) {
        for (Elevator elevator : controller.getElevators()) {
            System.out.println("Elevator " + elevator.getId() + 
                               " | Floor: " + elevator.getCurrentFloor() + 
                               " | Direction: " + elevator.getDirection() + 
                               " | Status: " + elevator.getElevatorStatus() +
                               " | Up Queue: " + elevator.getUpRequests() + 
                               " | Down Queue: " + elevator.getDownRequests());
        }
    }
}
