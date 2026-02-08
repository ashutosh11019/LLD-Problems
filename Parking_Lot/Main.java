import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import entities.*;
import enums.*;

public class Main {

    public static void main(String[] args) {

        ParkingSpot carSpot1 = new ParkingSpot(1, SpotType.CAR);
        ParkingSpot carSpot2 = new ParkingSpot(2, SpotType.CAR);
        ParkingSpot bikeSpot1 = new ParkingSpot(3, SpotType.BIKE);

        List<ParkingSpot> floor1Spots =
                Arrays.asList(carSpot1, carSpot2, bikeSpot1);

        Level level1 = new Level(1, floor1Spots);

        List<Level> levels = new ArrayList<>();
        levels.add(level1);

        ParkingLot parkingLot = new ParkingLot(levels);

        Vehicle car = new Vehicle("CAR-123", VehicleType.CAR);
        Vehicle bike = new Vehicle("BIKE-456", VehicleType.BIKE);

        System.out.println("Parking car...");
        Ticket carTicket = parkingLot.parkVehicle(car);
        System.out.println("Car parked at spot " +
                carTicket.getParkingSpot().getSlotId());

        System.out.println("Parking bike...");
        Ticket bikeTicket = parkingLot.parkVehicle(bike);
        System.out.println("Bike parked at spot " +
                bikeTicket.getParkingSpot().getSlotId());

        System.out.println("Car exiting...");
        parkingLot.freeSpot(carTicket);

        Vehicle car2 = new Vehicle("CAR-789", VehicleType.CAR);
        Ticket car2Ticket = parkingLot.parkVehicle(car2);
        System.out.println("New car parked at spot " +
                car2Ticket.getParkingSpot().getSlotId());
    }
}
