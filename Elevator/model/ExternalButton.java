package model;

import enums.Direction;
import services.ElevatorController;

public class ExternalButton {
    private int floorNumber;
    private Direction direction;
    private ElevatorController controller;

    public ExternalButton(int floorNumber, Direction direction, ElevatorController controller) {
        this.floorNumber = floorNumber;
        this.direction = direction;
        this.controller = controller;
    }

    public void pressButton() {
        System.out.println("External button pressed at floor " + floorNumber + " for direction " + direction);
        Request request = new Request(floorNumber, direction);
        controller.submitRequest(request);
    }
}
