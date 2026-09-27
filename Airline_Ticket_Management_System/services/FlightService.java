package Airline_Ticket_Management_System.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import Airline_Ticket_Management_System.enums.FlightStatus;
import Airline_Ticket_Management_System.model.Flight;
import Airline_Ticket_Management_System.model.Ticket;
import Airline_Ticket_Management_System.repository.InMemoryRepository;

public class FlightService {
    private final InMemoryRepository repository;

    public FlightService(InMemoryRepository repository){
        this.repository = repository;
    }

    public InMemoryRepository getRepository() {
        return repository;
    }

    public void addFlight(Flight flight) {
        if (repository.getFlights().containsKey(flight.getId())) {
            throw new IllegalArgumentException("Flight already exists");
        }

        repository.getFlights().put(flight.getId(), flight);
    }

    public void cancelFlight(int flightId){
        if (!repository.getFlights().containsKey(flightId)) {
            throw new IllegalArgumentException("Flight not exists");
        }

        repository.getFlights().get(flightId).cancelFlight();
        for(int seatId: repository.getFlights().get(flightId).getSeatIds()){
            repository.getSeats().get(seatId).release();
        }

        for(Ticket ticket: repository.getTickets().values()){
            if(ticket.getFlightId()==flightId){
                for(int bookingId: ticket.getBookingIds()){
                    repository.getBookings().get(bookingId).cancelBooking();
                }
                repository.getTickets().get(ticket.getId()).cancelBookings(ticket.getAmount());
            }
        }
    }

    public void editFlight(int flightId, String startCity, String endCity, 
        LocalDateTime startTime, LocalDateTime endTime){
            if (!repository.getFlights().containsKey(flightId)) {
                throw new IllegalArgumentException("Flight does not exists");
            }

            if(repository.getFlights().get(flightId).getStatus()==FlightStatus.CANCELLED){
                throw new IllegalArgumentException("Flight already Cancelled");
            }

            repository.getFlights().get(flightId).setStartCity(startCity);
            repository.getFlights().get(flightId).setEndCity(endCity);
            repository.getFlights().get(flightId).setStartTime(startTime);
            repository.getFlights().get(flightId).setEndTime(endTime);
    }

    public List<Flight> searchFlight(LocalDate date, String startCity, String endCity){
        List<Flight> flightList = new ArrayList<>();
        for(Flight flight: repository.getFlights().values()){
            if((flight.getStartCity()==startCity) && (flight.getEndCity()==endCity) 
                && (flight.getStartTime().toLocalDate().equals(date))){
                    flightList.add(flight);
            }
        }
        return flightList;
    }
}
