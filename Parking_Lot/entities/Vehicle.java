package entities;

import enums.VehicleType;

public class Vehicle{
	private String licenseNumber;
	private VehicleType vehicleType;

	public Vehicle(String licenseNumber, VehicleType vehicleType){
		this.licenseNumber = licenseNumber;
		this.vehicleType = vehicleType;
	}

	public VehicleType getVehicleType() {
        return vehicleType;
    }

	public String getLicenseNumber() {
        return licenseNumber;
    }
}