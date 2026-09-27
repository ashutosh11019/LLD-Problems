package Airline_Ticket_Management_System;

import Airline_Ticket_Management_System.enums.*;
import Airline_Ticket_Management_System.model.*;
import Airline_Ticket_Management_System.repository.InMemoryRepository;
import Airline_Ticket_Management_System.services.BookingService;
import Airline_Ticket_Management_System.services.FlightService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        // ------------------------------------------------
        // 1. Repository + Services
        // ------------------------------------------------

        InMemoryRepository repository = new InMemoryRepository();

        FlightService flightService = new FlightService(repository);
        BookingService bookingService = new BookingService(repository);


        // ------------------------------------------------
        // 2. Create User
        // ------------------------------------------------

        User user = new User(
                1,
                "Ashutosh",
                "ashutosh@gmail.com",
                "9876543210"
        );

        repository.getUsers().put(user.getId(), user);


        // ------------------------------------------------
        // 3. Create Seats
        // ------------------------------------------------

        Seat seat1 = new Seat(
                101,
                "12A",
                5000,
                SeatType.ECONOMY,
                SeatStatus.AVAILABLE,
                SeatLocation.WINDOW
        );

        Seat seat2 = new Seat(
                102,
                "12B",
                5000,
                SeatType.ECONOMY,
                SeatStatus.AVAILABLE,
                SeatLocation.MIDDLE
        );

        Seat seat3 = new Seat(
                103,
                "1A",
                10000,
                SeatType.BUSINESS,
                SeatStatus.AVAILABLE,
                SeatLocation.WINDOW
        );

        repository.getSeats().put(seat1.getId(), seat1);
        repository.getSeats().put(seat2.getId(), seat2);
        repository.getSeats().put(seat3.getId(), seat3);


        // ------------------------------------------------
        // 4. Create Flight
        // ------------------------------------------------

        LocalDateTime departure =
                LocalDateTime.of(2026, 10, 15, 10, 0);

        LocalDateTime arrival =
                LocalDateTime.of(2026, 10, 15, 12, 30);

        Flight flight = new Flight(
                1,
                101,
                Arrays.asList(
                        seat1.getId(),
                        seat2.getId(),
                        seat3.getId()
                ),
                "Delhi",
                "Mumbai",
                departure,
                arrival
        );

        flightService.addFlight(flight);


        // ------------------------------------------------
        // 5. Search Flight
        // ------------------------------------------------

        List<Flight> flights = flightService.searchFlight(
                departure.toLocalDate(),
                "Delhi",
                "Mumbai"
        );

        System.out.println("Flights found: " + flights.size());


        // ------------------------------------------------
        // 6. Create Passenger Bookings
        // ------------------------------------------------

        Booking passenger1 = new Booking(
                1,
                "Rahul",
                "rahul@gmail.com",
                "9000000001"
        );

        Booking passenger2 = new Booking(
                2,
                "Amit",
                "amit@gmail.com",
                "9000000002"
        );


        // ------------------------------------------------
        // 7. Select Seats
        // ------------------------------------------------

        boolean seatSelected1 =
                bookingService.selectSeat(
                        user.getId(),
                        flight.getId(),
                        seat1.getId()
                );

        boolean seatSelected2 =
                bookingService.selectSeat(
                        user.getId(),
                        flight.getId(),
                        seat2.getId()
                );

        System.out.println("Seat 12A selected: " + seatSelected1);
        System.out.println("Seat 12B selected: " + seatSelected2);


        // Assign selected seats to passengers
        passenger1.addSeat(seat1.getId());
        passenger2.addSeat(seat2.getId());


        // ------------------------------------------------
        // 8. Confirm Booking
        // ------------------------------------------------

        bookingService.createBooking(
                user.getId(),
                flight.getId(),
                new Booking[]{
                        passenger1,
                        passenger2
                }
        );


        // ------------------------------------------------
        // 9. Validate Result
        // ------------------------------------------------

        System.out.println("\n========== BOOKING RESULT ==========");

        System.out.println(
                "Seat 12A status: " + seat1.getStatus()
        );

        System.out.println(
                "Seat 12B status: " + seat2.getStatus()
        );

        System.out.println(
                "Booking 1 status: " + passenger1.getStatus()
        );

        System.out.println(
                "Booking 2 status: " + passenger2.getStatus()
        );

        System.out.println(
                "Number of tickets: " +
                        repository.getTickets().size()
        );

        System.out.println(
                "Number of bookings: " +
                        repository.getBookings().size()
        );


        // ------------------------------------------------
        // 10. Final Assertions
        // ------------------------------------------------

        if (seat1.getStatus() != SeatStatus.BOOKED) {
            throw new RuntimeException("Seat 12A was not booked");
        }

        if (seat2.getStatus() != SeatStatus.BOOKED) {
            throw new RuntimeException("Seat 12B was not booked");
        }

        if (passenger1.getStatus() != TicketStatus.BOOKED) {
            throw new RuntimeException("Booking 1 was not booked");
        }

        if (passenger2.getStatus() != TicketStatus.BOOKED) {
            throw new RuntimeException("Booking 2 was not booked");
        }

        if (repository.getTickets().size() != 1) {
            throw new RuntimeException("Ticket was not created");
        }

        System.out.println("\n✅ ALL TESTS PASSED");
    }
}