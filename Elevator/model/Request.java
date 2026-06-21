package model;

import enums.Direction;

public class Request {
    private int sourceFloor;
    private int destinationFloor;
    private Direction direction;

    public Request(int sourceFloor, int destinationFloor) {
        this.sourceFloor = sourceFloor;
        this.destinationFloor = destinationFloor;
        this.direction = (destinationFloor > sourceFloor)
                ? Direction.UP
                : Direction.DOWN;
    }

    public Request(int sourceFloor, Direction direction) {
        this.sourceFloor = sourceFloor;
        this.direction = direction;
        this.destinationFloor = 0;
    }

    public int getSourceFloor() {
        return sourceFloor;
    }

    public int getDestinationFloor() {
        return destinationFloor;
    }

    public Direction getDirection() {
        return direction;
    }
}
