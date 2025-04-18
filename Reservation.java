package flightreservation;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Reservation {
    private String reservationId;
    private User user;
    private Flight flight;
    private Seat seat;
    private LocalDateTime bookingTime;
    private double finalPrice;
    private ReservationStatus status;
    
    public Reservation(User user, Flight flight, Seat seat, double finalPrice) {
        this.reservationId = UUID.randomUUID().toString();
        this.user = user;
        this.flight = flight;
        this.seat = seat;
        this.bookingTime = LocalDateTime.now();
        this.finalPrice = finalPrice;
        this.status = ReservationStatus.CONFIRMED;
    }
    
    public boolean cancel() {
        if (status == ReservationStatus.CONFIRMED) {
            // Check if cancellation is allowed (e.g., not too close to departure)
            LocalDateTime cancellationDeadline = flight.getDepartureTime().minusHours(24);
            
            if (LocalDateTime.now().isBefore(cancellationDeadline)) {
                flight.cancelSeat(seat.getSeatNumber());
                status = ReservationStatus.CANCELLED;
                return true;
            }
        }
        return false;
    }
    
    // Getters
    public String getReservationId() {
        return reservationId;
    }
    
    public User getUser() {
        return user;
    }
    
    public Flight getFlight() {
        return flight;
    }
    
    public Seat getSeat() {
        return seat;
    }
    
    public LocalDateTime getBookingTime() {
        return bookingTime;
    }
    
    public double getFinalPrice() {
        return finalPrice;
    }
    
    public ReservationStatus getStatus() {
        return status;
    }
    
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return String.format("Reservation %s | %s | Flight: %s | Seat: %s | Status: %s | Price: $%.2f | Booked on: %s",
                reservationId.substring(0, 8), user.getFullName(), flight.getFlightNumber(), 
                seat.getSeatNumber(), status, finalPrice, bookingTime.format(formatter));
    }
}