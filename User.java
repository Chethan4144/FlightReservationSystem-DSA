package flightreservation;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class User {
    private String userId;
    private String username;
    private String password;
    private String fullName;
    private String email;
    private String phoneNumber;
    private List<Reservation> reservations;
    
    public User(String username, String password, String fullName, String email, String phoneNumber) {
        this.userId = UUID.randomUUID().toString();
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.reservations = new ArrayList<>();
    }
    
    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }
    
    public boolean removeReservation(String reservationId) {
        return reservations.removeIf(r -> r.getReservationId().equals(reservationId));
    }
    
    // Getters and setters
    public String getUserId() {
        return userId;
    }
    
    public String getUsername() {
        return username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public String getFullName() {
        return fullName;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public List<Reservation> getReservations() {
        return reservations;
    }
    
    @Override
    public String toString() {
        return String.format("User: %s (%s) | Email: %s | Phone: %s | Reservations: %d",
                fullName, username, email, phoneNumber, reservations.size());
    }
}