package flightreservation;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Flight {
    private String flightId;
    private String flightNumber;
    private String origin;
    private String destination;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private int totalSeats;
    private int availableSeats;
    private double basePrice;
    private List<Seat> seats;
    
    public Flight(String flightNumber, String origin, String destination, 
                 LocalDateTime departureTime, LocalDateTime arrivalTime, 
                 int totalSeats, double basePrice) {
        this.flightId = UUID.randomUUID().toString();
        this.flightNumber = flightNumber;
        this.origin = origin;
        this.destination = destination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.totalSeats = totalSeats;
        this.availableSeats = totalSeats;
        this.basePrice = basePrice;
        this.seats = initializeSeats();
    }
    
    private List<Seat> initializeSeats() {
        List<Seat> seatList = new ArrayList<>();
        
        // Create business class seats (20% of total)
        int businessSeats = (int)(totalSeats * 0.2);
        for (int i = 1; i <= businessSeats; i++) {
            seatList.add(new Seat("B" + i, SeatClass.BUSINESS, true));
        }
        
        // Create economy class seats (80% of total)
        for (int i = 1; i <= totalSeats - businessSeats; i++) {
            seatList.add(new Seat("E" + i, SeatClass.ECONOMY, true));
        }
        
        return seatList;
    }
    
    public Seat bookSeat(SeatClass seatClass) {
        if (availableSeats == 0) {
            return null; // No available seats
        }
        
        for (Seat seat : seats) {
            if (seat.isAvailable() && seat.getSeatClass() == seatClass) {
                seat.setAvailable(false);
                availableSeats--;
                return seat;
            }
        }
        
        return null; // No available seats of requested class
    }
    
    public boolean cancelSeat(String seatNumber) {
        for (Seat seat : seats) {
            if (seat.getSeatNumber().equals(seatNumber) && !seat.isAvailable()) {
                seat.setAvailable(true);
                availableSeats++;
                return true;
            }
        }
        return false;
    }
    
    public double calculatePrice(SeatClass seatClass) {
        // Dynamic pricing based on availability and seat class
        double availabilityFactor = 1.0 + (1.0 - ((double)availableSeats / totalSeats));
        double classMultiplier = (seatClass == SeatClass.BUSINESS) ? 2.5 : 1.0;
        
        return basePrice * availabilityFactor * classMultiplier;
    }
    
    // Getters and setters
    public String getFlightId() {
        return flightId;
    }
    
    public String getFlightNumber() {
        return flightNumber;
    }
    
    public String getOrigin() {
        return origin;
    }
    
    public String getDestination() {
        return destination;
    }
    
    public LocalDateTime getDepartureTime() {
        return departureTime;
    }
    
    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }
    
    public int getTotalSeats() {
        return totalSeats;
    }
    
    public int getAvailableSeats() {
        return availableSeats;
    }
    
    public double getBasePrice() {
        return basePrice;
    }
    
    public List<Seat> getSeats() {
        return seats;
    }
    
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return String.format("Flight %s: %s to %s | Departure: %s | Arrival: %s | Available Seats: %d/%d | Base Price: $%.2f",
                flightNumber, origin, destination, 
                departureTime.format(formatter), arrivalTime.format(formatter),
                availableSeats, totalSeats, basePrice);
    }
}