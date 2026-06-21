package model;

import enums.Direction;
import services.ElevatorController;

public class Floor {
    private int floorNumber;
    private ExternalButton upButton;
    private ExternalButton downButton;

    public Floor(int floorNumber, ElevatorController controller) {
        this.floorNumber = floorNumber;
        this.upButton = new ExternalButton(floorNumber, Direction.UP, controller);
        this.downButton = new ExternalButton(floorNumber, Direction.DOWN, controller);
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public ExternalButton getUpButton() {
        return upButton;
    }

    public ExternalButton getDownButton() {
        return downButton;
    }
}
