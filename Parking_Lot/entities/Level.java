package entities;

import java.util.List;

public class Level {
    private int floorNumber;
    private List<ParkingSpot> spots;

    public Level(int floorNumber, List<ParkingSpot> spots){
        this.floorNumber = floorNumber;
        this.spots = spots;
    }

    public int getFloorNumber(){
        return floorNumber;
    }

    public List<ParkingSpot> getSpots(){
        return spots;
    }

    public ParkingSpot getAvailableSpot(Vehicle vehicle){
        for(ParkingSpot spot: spots){
            if(spot.isFree() && spot.canFitVehicle(vehicle)) {
                return spot;
            }
        }
        return null;
    }

    public ParkingSpot parkVehicle(Vehicle vehicle){
        ParkingSpot spot = getAvailableSpot(vehicle);
        if (spot != null) {
            spot.assignVehicle(vehicle);
            return spot;
        }
        return null;
    }
}
