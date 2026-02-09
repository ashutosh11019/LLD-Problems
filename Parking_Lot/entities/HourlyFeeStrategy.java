package entities;

import java.time.ZoneOffset;
import enums.VehicleType;

public class HourlyFeeStrategy implements FeeStrategy {

    public HourlyFeeStrategy() {

    }

    @Override
    public double calculateFee(Ticket ticket) {
        double amount = 0.0;
        long seconds = ticket.getExitTime().toEpochSecond(ZoneOffset.UTC)
                - ticket.getEntryTime().toEpochSecond(ZoneOffset.UTC);
        long hours = seconds / 3600;
        if(seconds % 3600 != 0){
            hours++;
        }

        if(ticket.getVehicle().getVehicleType() == VehicleType.CAR){
            amount = hours * 10.0;
        }else if(ticket.getVehicle().getVehicleType() == VehicleType.BIKE){
            amount = hours * 20.0;
        }else if(ticket.getVehicle().getVehicleType() == VehicleType.TRUCK){
            amount = hours * 40.0;
        }

        return amount;
    }
}
