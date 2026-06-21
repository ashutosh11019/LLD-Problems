# Elevator System Low Level Design

## Problem Statement
Design a Low Level Design for an Elevator System that supports multiple elevators servicing a multi-floor building. The system should process floor requests efficiently, optimize movement using the SCAN algorithm to minimize wait times, and dispatch the most suitable elevator for external calls.

## Requirements
*   **Multiple Elevators**: Support coordination of multiple elevators in a building.
*   **External Buttons**: Each floor has external buttons (Up/Down) to request an elevator.
*   **Internal Buttons**: Each elevator has internal buttons to select destination floors.
*   **SCAN Scheduling**: Elevators serve requests in their current direction until all requests in that direction are completed, then reverse.
*   **State Tracking**: Each elevator tracks its ID, current floor, status (moving, stopped, idle), and direction (up, down, idle).
*   **Intelligent Dispatching**: Choose the best elevator based on direction matching, proximity, and idle status.

---

## Core Entities & Responsibilities

### 1. Elevator
Represents an individual elevator cabin.
*   **Responsibilities**:
    *   Tracks current floor, current direction, and status.
    *   Maintains lists of pending upward and downward stops (queues).
    *   Moves floor-by-floor and stops at target destinations.
    *   Controls door operations (opening and closing).

### 2. ElevatorController
The orchestrator of the elevator system.
*   **Responsibilities**:
    *   Manages the list of active elevators.
    *   Implements the dispatch algorithm to delegate external requests to the most appropriate elevator.
    *   Advances the simulation clock (ticks/steps) across all elevators.

### 3. Request
Represents a call or destination request in the system.
*   **Responsibilities**:
    *   Encapsulates the source floor, optional destination floor, and target direction (UP/DOWN).

### 4. Floor
Represents a building floor.
*   **Responsibilities**:
    *   Hosts external buttons for calling elevators (UP and DOWN).

### 5. ExternalButton
A button located on a floor outside the elevators.
*   **Responsibilities**:
    *   Submits an external request to the central controller when pressed.

### 6. InternalButton
A button located inside an elevator cabin.
*   **Responsibilities**:
    *   Adds a destination request directly to the host elevator when pressed.

---

## Class Definitions

### Elevator
```java
class Elevator {
    - int id
    - int currentFloor
    - Direction direction
    - ElevatorStatus elevatorStatus
    - TreeSet<Integer> upRequests
    - TreeSet<Integer> downRequests

    + void addRequest(int floor)
    + void addRequest(int floor, Direction reqDirection)
    + Integer getNextStop()
    + void step()
    + void openDoor()
    + void closeDoor()
    + boolean hasPendingRequests()
    + void updateDirection()
}
```

### ElevatorController
```java
class ElevatorController {
    - List<Elevator> elevators

    + void addElevator(Elevator elevator)
    + List<Elevator> getElevators()
    + void submitRequest(Request request)
    + Elevator assignElevator(Request request)
    + void step()
}
```

### Request
```java
class Request {
    - int sourceFloor
    - int destinationFloor
    - Direction direction

    + int getSourceFloor()
    + int getDestinationFloor()
    + Direction getDirection()
}
```

### Floor
```java
class Floor {
    - int floorNumber
    - ExternalButton upButton
    - ExternalButton downButton

    + int getFloorNumber()
    + ExternalButton getUpButton()
    + ExternalButton getDownButton()
}
```

### ExternalButton
```java
class ExternalButton {
    - int floorNumber
    - Direction direction
    - ElevatorController controller

    + void pressButton()
}
```

### InternalButton
```java
class InternalButton {
    - int destinationFloor
    - Elevator elevator

    + void pressButton()
}
```

---

## Key Processes

### 1. Elevator Dispatch/Assignment Flow
When an external request is submitted:
1.  **Filtering**: Filter out elevators under maintenance.
2.  **Direction & Proximity Alignment**:
    *   Prioritize elevators that are moving in the same direction as the request and have not yet passed the request floor.
    *   If no matching moving elevator is found, consider idle elevators.
3.  **Selection**: Select the elevator with the minimum absolute distance to the request floor from the filtered candidates.
4.  **Fallback**: If no aligned elevator is found, select the nearest active elevator overall.

### 2. SCAN Scheduling Algorithm
*   An elevator moving **UP** only stops at floor requests >= its current floor in the upward queue.
*   An elevator moving **DOWN** only stops at floor requests <= its current floor in the downward queue.
*   Once an elevator reaches the top/bottom of its sweep (i.e. no more requests in the current direction), it checks the opposite queue:
    *   If requests exist in the other queue, it changes its direction and begins sweeping in the opposite direction.
    *   If no requests exist in either queue, it sets its direction to `IDLE` and status to `IDLE`.

---

## 🏃 Running the Simulation

A step-by-step simulator has been built to test the SCAN algorithm and elevator controller routing logic.

### Compile the Code
Run the following command from the `Elevator` directory:
```bash
javac enums/*.java model/*.java services/*.java Main.java
```

### Run the Simulator
Run the compiled main class:
```bash
java Main
```
