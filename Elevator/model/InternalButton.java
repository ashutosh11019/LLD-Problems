package model;

public class InternalButton {
    private int destinationFloor;
    private Elevator elevator;

    public InternalButton(int destinationFloor, Elevator elevator) {
        this.destinationFloor = destinationFloor;
        this.elevator = elevator;
    }

    public void pressButton() {
        System.out.println("Internal button pressed in Elevator " + elevator.getId() + " for floor " + destinationFloor);
        elevator.addRequest(destinationFloor);
    }
}
