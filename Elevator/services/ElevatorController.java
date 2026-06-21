package services;

import java.util.ArrayList;
import java.util.List;

import enums.Direction;
import enums.ElevatorStatus;
import model.Elevator;
import model.Request;

public class ElevatorController {
    private List<Elevator> elevators;

    public ElevatorController() {
        this.elevators = new ArrayList<>();
    }

    public void addElevator(Elevator elevator) {
        elevators.add(elevator);
    }

    public List<Elevator> getElevators() {
        return elevators;
    }

    public void submitRequest(Request request) {
        Elevator assignedElevator = assignElevator(request);
        if (assignedElevator != null) {
            System.out.println("Assigned request from floor " + request.getSourceFloor() + 
                               " (" + request.getDirection() + ") to Elevator " + assignedElevator.getId());
            assignedElevator.addRequest(request.getSourceFloor(), request.getDirection());
        } else {
            System.out.println("No active elevator available to serve request from floor " + request.getSourceFloor());
        }
    }

    public Elevator assignElevator(Request request) {
        Elevator bestElevator = null;
        int minDistance = Integer.MAX_VALUE;

        // Try to find elevator that matches direction or is IDLE
        for (Elevator elevator : elevators) {
            if (elevator.getElevatorStatus() == ElevatorStatus.MAINTENANCE) {
                continue;
            }

            boolean sameDirection = (elevator.getDirection() == request.getDirection());
            boolean isIdle = (elevator.getDirection() == Direction.IDLE);

            // Check if elevator is ahead of the request in its current direction
            boolean isAhead = true;
            if (elevator.getDirection() == Direction.UP && elevator.getCurrentFloor() > request.getSourceFloor()) {
                isAhead = false;
            }
            if (elevator.getDirection() == Direction.DOWN && elevator.getCurrentFloor() < request.getSourceFloor()) {
                isAhead = false;
            }

            if (isIdle || (sameDirection && isAhead)) {
                int distance = Math.abs(elevator.getCurrentFloor() - request.getSourceFloor());
                if (distance < minDistance) {
                    minDistance = distance;
                    bestElevator = elevator;
                }
            }
        }

        // Fallback: Pick nearest elevator regardless of direction or alignment
        if (bestElevator == null) {
            minDistance = Integer.MAX_VALUE;
            for (Elevator elevator : elevators) {
                if (elevator.getElevatorStatus() == ElevatorStatus.MAINTENANCE) {
                    continue;
                }
                int distance = Math.abs(elevator.getCurrentFloor() - request.getSourceFloor());
                if (distance < minDistance) {
                    minDistance = distance;
                    bestElevator = elevator;
                }
            }
        }

        return bestElevator;
    }

    public void step() {
        for (Elevator elevator : elevators) {
            elevator.step();
        }
    }
}
