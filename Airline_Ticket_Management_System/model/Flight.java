package Airline_Ticket_Management_System.model;

import java.time.LocalDateTime;
import java.util.List;

import Airline_Ticket_Management_System.enums.FlightStatus;

public class Flight {
    private final int id;
    private int flightNumber;
    private List<Integer> seatIds;
    private String startCity;
    private String endCity;
    private FlightStatus status;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public Flight(int id, int flightNumber, List<Integer> seatIds, 
        String startCity, String endCity, LocalDateTime startTime, 
        LocalDateTime endTime){
            this.id = id;
            this.flightNumber = flightNumber;
            this.seatIds = seatIds;
            this.startCity = startCity;
            this.endCity = endCity;
            this.status = FlightStatus.AVAILABLE;
            this.startTime = startTime;
            this.endTime = endTime;
        }
    
    public int getId() {
        return id;
    }

    public int getFlightNumber() {
        return flightNumber;
    }

    public List<Integer> getSeatIds() {
        return seatIds;
    }

    public String getStartCity() {
        return startCity;
    }

    public String getEndCity() {
        return endCity;
    }

    public FlightStatus getStatus() {
        return status;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndCity(String endCity) {
        this.endCity = endCity;
    }

    public void setStartCity(String startCity) {
        this.startCity = startCity;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public void cancelFlight(){
        this.status = FlightStatus.CANCELLED;
    }
}
