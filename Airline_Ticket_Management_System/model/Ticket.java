package Airline_Ticket_Management_System.model;

import java.time.LocalDateTime;
import java.util.List;

import Airline_Ticket_Management_System.enums.TicketStatus;

public class Ticket {
    private final int id;
    private int bookedByUserId;
    private int flightId;
    private List<Integer> bookingIds;
    private int amount;
    private int refundedAmount;
    private TicketStatus status;
    private LocalDateTime bookingTime;

    public Ticket(int id, int bookedByUserId, int flightId, List<Integer> bookingIds, 
        int amount){
            this.id = id;
            this.bookedByUserId = bookedByUserId;
            this.flightId = flightId;
            this.bookingIds = bookingIds;
            this.amount = amount;
            this.refundedAmount = 0;
            this.status = TicketStatus.BOOKED;
            this.bookingTime = LocalDateTime.now();
        }
    
    public int getId() {
        return id;
    }

    public int getBookedByUserId() {
        return bookedByUserId;
    }

    public int getFlightId() {
        return flightId;
    }

    public List<Integer> getBookingIds() {
        return bookingIds;
    }

    public int getAmount() {
        return amount;
    }

    public int getRefundedAmount() {
        return refundedAmount;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public LocalDateTime getBookingTime() {
        return bookingTime;
    }

    public void cancelBookings(int refundAmount) {
        this.refundedAmount += refundAmount;

        if (this.refundedAmount >= this.amount) {
            this.status = TicketStatus.CANCELLED;
        } else {
            this.status = TicketStatus.PARTIAL_CANCELLED;
        }
    }
}
