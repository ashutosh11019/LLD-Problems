package Airline_Ticket_Management_System.model;

import Airline_Ticket_Management_System.enums.TicketStatus;

public class Booking {
    private final int id;
    private String passangerName;
    private String passengerEmailId;
    private String passengerContactNumber;
    private Integer seatId;
    private boolean meal;
    private TicketStatus status;

    public Booking(int id, String passangerName, String passengerEmailId, 
        String passengerContactNumber){
            this.id = id;
            this.passangerName = passangerName;
            this.passengerEmailId = passengerEmailId;
            this.passengerContactNumber = passengerContactNumber;
            this.status = null;
            this.seatId = null;
            this.meal = false;
    }

    public int getId() {
        return id;
    }

    public String getPassangerName() {
        return passangerName;
    }

    public String getPassengerEmailId() {
        return passengerEmailId;
    }

    public String getPassengerContactNumber() {
        return passengerContactNumber;
    }

    public Integer getSeatId() {
        return seatId;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public boolean getMeal(){
        return meal;
    }

    public void setPassangerName(String passangerName) {
        this.passangerName = passangerName;
    }

    public void setPassengerEmailId(String passengerEmailId) {
        this.passengerEmailId = passengerEmailId;
    }

    public void setPassengerContactNumber(String passengerContactNumber) {
        this.passengerContactNumber = passengerContactNumber;
    }

    public void addSeat(int seatId){
        this.seatId = seatId;
        this.status = TicketStatus.BOOKED;
        this.meal = true;
    }

    public void cancelBooking(){
        this.status = TicketStatus.CANCELLED;
        this.meal = false;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }
}
