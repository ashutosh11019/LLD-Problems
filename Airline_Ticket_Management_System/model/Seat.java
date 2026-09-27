package Airline_Ticket_Management_System.model;

import java.time.LocalDateTime;

import Airline_Ticket_Management_System.enums.SeatLocation;
import Airline_Ticket_Management_System.enums.SeatStatus;
import Airline_Ticket_Management_System.enums.SeatType;

public class Seat {
    private final int id;
    private String seatNumber;
    private Integer heldByUserId;
    private int price;
    private SeatType seatType;
    private SeatStatus status;
    private SeatLocation seatLocation;
    private LocalDateTime holdUntil;

    public Seat(int id, String seatNumber, int price, SeatType seatType, 
        SeatStatus status, SeatLocation seatLocation){
            this.id = id;
            this.seatNumber = seatNumber;
            this.heldByUserId = null;
            this.price = price;
            this.seatType = seatType;
            this.status = status;
            this.seatLocation = seatLocation;
        }
    
    public int getId() {
        return id;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public int getHeldByUserId() {
        return heldByUserId;
    }

    public int getPrice() {
        return price;
    }

    public SeatType getSeatType() {
        return seatType;
    }

    public SeatStatus getStatus() {
        return status;
    }

    public SeatLocation getSeatLocation() {
        return seatLocation;
    }

    public LocalDateTime getHoldUntil() {
        return holdUntil;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setSeatType(SeatType seatType) {
        this.seatType = seatType;
    }

    public void setSeatLocation(SeatLocation seatLocation) {
        this.seatLocation = seatLocation;
    }

    public void hold(int userId) {
        this.heldByUserId = userId;
        this.holdUntil = LocalDateTime.now().plusMinutes(5);
        this.status = SeatStatus.ON_HOLD;
    }

    public boolean book(int userId){
        if((this.heldByUserId == userId) && (this.holdUntil != null) 
            && (this.holdUntil.isAfter(LocalDateTime.now()))){
            this.status = SeatStatus.BOOKED;
            this.heldByUserId = null;
            this.holdUntil = null;
            return true;
        }
        return false;
    }
    
    public void release(){
        this.heldByUserId = null;
        this.holdUntil = null;
        this.status = SeatStatus.AVAILABLE;
    }

    public boolean checkAndReleaseIfExpired(){
        if(this.holdUntil.isAfter(LocalDateTime.now())) return false;
        this.heldByUserId = null;
        this.holdUntil = null;
        this.status = SeatStatus.AVAILABLE;
        return true;
    }
}
