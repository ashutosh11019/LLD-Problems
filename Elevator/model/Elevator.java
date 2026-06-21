package model;

import java.util.TreeSet;

import enums.Direction;
import enums.ElevatorStatus;

public class Elevator {
    private int id;
    private int currentFloor;
    private Direction direction;
    private ElevatorStatus elevatorStatus;
    private TreeSet<Integer> upRequests;
    private TreeSet<Integer> downRequests;
    private static int count = 1;
    
    public Elevator(int currentFloor, Direction direction, ElevatorStatus elevatorStatus, 
                    TreeSet<Request> upRequests, TreeSet<Request> downRequests){
        this.id = count++;
        this.currentFloor = currentFloor;
        this.direction =  direction;
        this.elevatorStatus = elevatorStatus;
        this.upRequests = new TreeSet<>();
        this.downRequests = new TreeSet<>();
    }

    public Elevator(int currentFloor) {
        this.id = count++;
        this.currentFloor = currentFloor;
        this.direction = Direction.IDLE;
        this.elevatorStatus = ElevatorStatus.IDLE;
        this.upRequests = new TreeSet<>();
        this.downRequests = new TreeSet<>();
    }

    public int getId(){
        return id;
    }

    public int getCurrentFloor(){
        return currentFloor;
    }

    public Direction getDirection(){
        return direction;
    }

    public ElevatorStatus getElevatorStatus(){
        return elevatorStatus;
    }

    public TreeSet<Integer> getUpRequests(){
        return upRequests;
    }

    public TreeSet<Integer> getDowRequests(){
        return downRequests;
    }

    public TreeSet<Integer> getDownRequests(){
        return downRequests;
    }

    public void addRequest(int floor) {
        if (floor == currentFloor) {
            System.out.println("Elevator " + id + " is already at floor " + floor + ". Opening doors.");
            openDoor();
            closeDoor();
            return;
        }
        
        if (floor > currentFloor) {
            upRequests.add(floor);
        } else {
            downRequests.add(floor);
        }
        updateDirection();
    }

    public void addRequest(int floor, Direction reqDirection) {
        if (floor == currentFloor) {
            System.out.println("Elevator " + id + " is already at floor " + floor + ". Opening doors.");
            openDoor();
            closeDoor();
            return;
        }

        if (reqDirection == Direction.UP) {
            upRequests.add(floor);
        } else if (reqDirection == Direction.DOWN) {
            downRequests.add(floor);
        } else {
            // Default behavior if request direction is IDLE/unspecified
            addRequest(floor);
            return;
        }
        updateDirection();
    }

    public Integer getNextStop() {
        if (direction == Direction.UP) {
            Integer next = upRequests.ceiling(currentFloor);
            if (next != null) {
                return next;
            }
            if (!downRequests.isEmpty()) {
                return downRequests.last();
            }
            if (!upRequests.isEmpty()) {
                return upRequests.first();
            }
        } else if (direction == Direction.DOWN) {
            Integer next = downRequests.floor(currentFloor);
            if (next != null) {
                return next;
            }
            if (!upRequests.isEmpty()) {
                return upRequests.first();
            }
            if (!downRequests.isEmpty()) {
                return downRequests.last();
            }
        } else if (direction == Direction.IDLE) {
            Integer nextUp = !upRequests.isEmpty() ? upRequests.first() : null;
            Integer nextDown = !downRequests.isEmpty() ? downRequests.last() : null;
            if (nextUp != null && nextDown != null) {
                if (Math.abs(nextUp - currentFloor) <= Math.abs(nextDown - currentFloor)) {
                    return nextUp;
                } else {
                    return nextDown;
                }
            } else if (nextUp != null) {
                return nextUp;
            } else if (nextDown != null) {
                return nextDown;
            }
        }
        return null;
    }

    public void step() {
        updateDirection();
        if (direction == Direction.IDLE) {
            return;
        }

        Integer nextStop = getNextStop();
        if (nextStop == null) {
            direction = Direction.IDLE;
            elevatorStatus = ElevatorStatus.IDLE;
            return;
        }

        elevatorStatus = ElevatorStatus.MOVING;
        if (nextStop > currentFloor) {
            direction = Direction.UP;
            currentFloor++;
            System.out.println("Elevator " + id + " moving UP to floor " + currentFloor);
        } else if (nextStop < currentFloor) {
            direction = Direction.DOWN;
            currentFloor--;
            System.out.println("Elevator " + id + " moving DOWN to floor " + currentFloor);
        }

        // Check if we reached a requested floor in our current direction
        if (direction == Direction.UP && upRequests.contains(currentFloor)) {
            stopAndServe(currentFloor, Direction.UP);
        } else if (direction == Direction.DOWN && downRequests.contains(currentFloor)) {
            stopAndServe(currentFloor, Direction.DOWN);
        } else if (currentFloor == nextStop) {
            stopAndServe(currentFloor, direction);
        }
    }

    private void stopAndServe(int floor, Direction queueDirection) {
        elevatorStatus = ElevatorStatus.STOPPED;
        openDoor();
        if (queueDirection == Direction.UP) {
            upRequests.remove(floor);
        } else if (queueDirection == Direction.DOWN) {
            downRequests.remove(floor);
        } else {
            upRequests.remove(floor);
            downRequests.remove(floor);
        }
        closeDoor();
        updateDirection();
    }

    public void openDoor() {
        System.out.println("Elevator " + id + " opening door at floor " + currentFloor);
    }

    public void closeDoor() {
        System.out.println("Elevator " + id + " closing door at floor " + currentFloor);
    }

    public boolean hasPendingRequests() {
        return !upRequests.isEmpty() || !downRequests.isEmpty();
    }

    public void updateDirection() {
        if (upRequests.isEmpty() && downRequests.isEmpty()) {
            direction = Direction.IDLE;
            elevatorStatus = ElevatorStatus.IDLE;
            return;
        }

        if (direction == Direction.UP) {
            if (upRequests.ceiling(currentFloor) == null) {
                if (!downRequests.isEmpty() || !upRequests.isEmpty()) {
                    direction = Direction.DOWN;
                } else {
                    direction = Direction.IDLE;
                    elevatorStatus = ElevatorStatus.IDLE;
                }
            }
        } else if (direction == Direction.DOWN) {
            if (downRequests.floor(currentFloor) == null) {
                if (!upRequests.isEmpty() || !downRequests.isEmpty()) {
                    direction = Direction.UP;
                } else {
                    direction = Direction.IDLE;
                    elevatorStatus = ElevatorStatus.IDLE;
                }
            }
        } else if (direction == Direction.IDLE) {
            if (!upRequests.isEmpty()) {
                int target = upRequests.first();
                if (target > currentFloor) {
                    direction = Direction.UP;
                } else if (target < currentFloor) {
                    direction = Direction.DOWN;
                } else {
                    direction = Direction.UP;
                }
            } else if (!downRequests.isEmpty()) {
                int target = downRequests.last();
                if (target > currentFloor) {
                    direction = Direction.UP;
                } else if (target < currentFloor) {
                    direction = Direction.DOWN;
                } else {
                    direction = Direction.DOWN;
                }
            }
        }
    }
}
