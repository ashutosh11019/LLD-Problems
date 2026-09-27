package Airline_Ticket_Management_System.repository;

import java.util.HashMap;
import java.util.Map;

import Airline_Ticket_Management_System.model.Booking;
import Airline_Ticket_Management_System.model.Flight;
import Airline_Ticket_Management_System.model.Seat;
import Airline_Ticket_Management_System.model.Staff;
import Airline_Ticket_Management_System.model.Ticket;
import Airline_Ticket_Management_System.model.User;

public class InMemoryRepository {
    private final Map<Integer, User> users;
    private final Map<Integer, Staff> staffs;
    private final Map<Integer, Flight> flights;
    private final Map<Integer, Seat> seats;
    private final Map<Integer, Booking> bookings;
    private final Map<Integer, Ticket> tickets;

    public InMemoryRepository(){
        this.users = new HashMap<>();
        this.staffs = new HashMap<>();
        this.flights = new HashMap<>();
        this.seats = new HashMap<>();
        this.bookings = new HashMap<>();
        this.tickets = new HashMap<>();
    }

    public Map<Integer, User> getUsers() {
        return users;
    }

    public Map<Integer, Staff> getStaffs() {
        return staffs;
    }

    public Map<Integer, Flight> getFlights() {
        return flights;
    }

    public Map<Integer, Seat> getSeats() {
        return seats;
    }

    public Map<Integer, Booking> getBookings() {
        return bookings;
    }

    public Map<Integer, Ticket> getTickets() {
        return tickets;
    }
}
