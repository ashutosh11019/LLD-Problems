package Airline_Ticket_Management_System.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import Airline_Ticket_Management_System.enums.SeatStatus;
import Airline_Ticket_Management_System.enums.TicketStatus;
import Airline_Ticket_Management_System.model.Booking;
import Airline_Ticket_Management_System.model.Flight;
import Airline_Ticket_Management_System.model.Seat;
import Airline_Ticket_Management_System.model.Ticket;
import Airline_Ticket_Management_System.repository.InMemoryRepository;

public class BookingService {
    private final InMemoryRepository repository;
    int incrementTicket=1;

    public BookingService(InMemoryRepository repository){
        this.repository = repository;
    }

    public boolean selectSeat(int userId, int flightId, int seatId) {
        if (!repository.getFlights().containsKey(flightId)) {
            throw new IllegalArgumentException("Flight does not exist");
        }

        if (!repository.getSeats().containsKey(seatId)) {
            throw new IllegalArgumentException("Seat does not exist");
        }

        if (!repository.getUsers().containsKey(userId)) {
            throw new IllegalArgumentException("User does not exist");
        }

        Flight flight = repository.getFlights().get(flightId);
        Seat seat = repository.getSeats().get(seatId);

        if (!flight.getSeatIds().contains(seatId)) {
            throw new IllegalArgumentException("Seat does not belong to flight");
        }

        if (seat.getStatus() == SeatStatus.ON_HOLD) {
            if (!seat.checkAndReleaseIfExpired()) {
                return false;
            }
        }

        if (seat.getStatus() == SeatStatus.AVAILABLE) {
            seat.hold(userId);
            return true;
        }

        return false;
    }

    public void createBooking(int userId, int flightId, Booking[] bookings) {
        if (!repository.getFlights().containsKey(flightId)) {
            throw new IllegalArgumentException("Flight does not exist");
        }

        if (!repository.getUsers().containsKey(userId)) {
            throw new IllegalArgumentException("User does not exist");
        }

        // 1. Validate all seats first
        for (Booking booking : bookings) {
            Seat seat = repository.getSeats().get(booking.getSeatId());

            if (seat == null) {
                throw new IllegalArgumentException("Seat does not exist");
            }

            if (seat.getStatus() != SeatStatus.ON_HOLD) {
                throw new IllegalArgumentException("Seat is not on hold");
            }

            if (seat.getHeldByUserId() != userId) {
                throw new IllegalArgumentException("Seat is held by another user");
            }

            if (seat.getHoldUntil() == null ||
                    !seat.getHoldUntil().isAfter(LocalDateTime.now())) {
                seat.release();
                throw new IllegalArgumentException("Seat hold has expired");
            }
        }

        // 2. Book all seats
        List<Integer> bookingIds = new ArrayList<>();
        int amount = 0;

        for (Booking booking : bookings) {
            Seat seat = repository.getSeats().get(booking.getSeatId());

            if (!seat.book(userId)) {
                throw new IllegalStateException("Unable to book seat");
            }

            booking.setStatus(TicketStatus.BOOKED);
            repository.getBookings().put(booking.getId(), booking);

            amount += seat.getPrice();
            bookingIds.add(booking.getId());
        }

        // 3. Create ticket
        Ticket ticket = new Ticket(
                incrementTicket,
                userId,
                flightId,
                bookingIds,
                amount
        );

        repository.getTickets().put(incrementTicket, ticket);
        incrementTicket++;
    }
}
