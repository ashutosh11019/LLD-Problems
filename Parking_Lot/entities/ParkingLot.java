package entities;

import java.util.List;

import entities.HourlyFeeStrategy;

public class ParkingLot{
	private List<Level> levels;
	private FeeStrategy feeStrategy;

	public ParkingLot(List<Level> levels){
		this.levels = levels;
	}

	public List<Level> getLevels(){
		return levels;
	}

	public void addLevel(Level level){
		levels.add(level);
	}

	public ParkingSpot findSpot(Vehicle vehicle){
		for (Level level : levels) {
			ParkingSpot parkingSpot = level.getAvailableSpot(vehicle);
			if(parkingSpot != null){
				return parkingSpot;
			}
		}
		return null;
	}

	public Ticket parkVehicle(Vehicle vehicle) {
        for (Level level : levels) {
            ParkingSpot spot = level.parkVehicle(vehicle);
            if (spot != null) {
                return new Ticket(vehicle, spot);
            }
        }
        return null;
    }

	public void freeSpot(Ticket ticket) {
        ParkingSpot spot = ticket.getParkingSpot();
		ticket.markExit();
		double fee = feeStrategy.calculateFee(ticket);
		System.out.println(fee);
        spot.removeVehicle();
    }
}