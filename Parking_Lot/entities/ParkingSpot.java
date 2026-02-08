package entities;

import enums.SpotType;

public class ParkingSpot{
	private int slotId;
	private SpotType spotType;
	private Vehicle vehicle;

	public ParkingSpot(int slotId, SpotType spotType){
		this.slotId = slotId;
		this.spotType = spotType;
	}

	public int getSlotId(){
		return slotId;
	}

	public SpotType getsSpotType(){
		return spotType;
	}

	public boolean isFree(){
		return vehicle == null;
	}

	public boolean canFitVehicle(Vehicle vehicle) {
        return vehicle.getVehicleType().name().equals(spotType.name());
    }

	public void assignVehicle(Vehicle vehicle) {
        if (!isFree()) {
            throw new IllegalStateException("Spot already occupied");
        }
        this.vehicle = vehicle;
    }

	public void removeVehicle() {
        this.vehicle = null;
    }

	public Vehicle getVehicle() {
		return vehicle;
	}
}