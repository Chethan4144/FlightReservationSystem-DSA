package flightreservation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FlightManagementSystem {
    private Map<String, Flight> flights;
    private Map<String, User> users;
    
    public FlightManagementSystem() {
        this.flights = new HashMap<>();
        this.users = new HashMap<>();
    }
    
    public void addFlight(Flight flight) {
        flights.put(flight.getFlightId(), flight);
    }
    
    public Flight getFlight(String flightId) {
        return flights.get(flightId);
    }
    
    public boolean removeFlight(String flightId) {
        if (flights.containsKey(flightId)) {
            flights.remove(flightId);
            return true;
        }
        return false;
    }
    
    public void registerUser(User user) {
        users.put(user.getUserId(), user);
    }
    
    public User getUser(String userId) {
        return users.get(userId);
    }
    
    public User getUserByUsername(String username) {
        for (User user : users.values()) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
    
    public List<Flight> searchFlights(String origin, String destination, LocalDateTime date) {
        List<Flight> matchingFlights = new ArrayList<>();
        
        for (Flight flight : flights.values()) {
            // Check if flight matches search criteria
            if (flight.getOrigin().equalsIgnoreCase(origin) &&
                flight.getDestination().equalsIgnoreCase(destination) &&
                isSameDay(flight.getDepartureTime(), date)) {
                
                matchingFlights.add(flight);
            }
        }
        
        return matchingFlights;
    }
    
    private boolean isSameDay(LocalDateTime time1, LocalDateTime time2) {
        return time1.getYear() == time2.getYear() &&
               time1.getMonthValue() == time2.getMonthValue() &&
               time1.getDayOfMonth() == time2.getDayOfMonth();
    }
    
    public Reservation createReservation(User user, Flight flight, SeatClass seatClass) {
        // Book a seat on the flight
        Seat seat = flight.bookSeat(seatClass);
        
        if (seat == null) {
            return null; // No available seats
        }
        
        // Calculate price
        double price = flight.calculatePrice(seatClass);
        
        // Create reservation
        Reservation reservation = new Reservation(user, flight, seat, price);
        
        // Add reservation to user
        user.addReservation(reservation);
        
        return reservation;
    }
    
    public boolean cancelReservation(User user, String reservationId) {
        for (Reservation reservation : user.getReservations()) {
            if (reservation.getReservationId().equals(reservationId)) {
                if (reservation.cancel()) {
                    return true;
                }
                break;
            }
        }
        return false;
    }
    
    public Map<String, Flight> getFlights() {
        return flights;
    }
    
    public Map<String, User> getUsers() {
        return users;
    }
}