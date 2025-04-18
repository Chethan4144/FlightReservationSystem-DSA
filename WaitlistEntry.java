package flightreservation;

import java.time.LocalDateTime;

public class WaitlistEntry {
    private User user;
    private Flight flight;
    private SeatClass preferredSeatClass;
    private int priority; // Higher value means higher priority
    private LocalDateTime requestTime;
    
    public WaitlistEntry(User user, Flight flight, SeatClass preferredSeatClass, int priority) {
        this.user = user;
        this.flight = flight;
        this.preferredSeatClass = preferredSeatClass;
        this.priority = priority;
        this.requestTime = LocalDateTime.now();
    }
    
    // Getters
    public User getUser() {
        return user;
    }
    
    public Flight getFlight() {
        return flight;
    }
    
    public SeatClass getPreferredSeatClass() {
        return preferredSeatClass;
    }
    
    public int getPriority() {
        return priority;
    }
    
    public LocalDateTime getRequestTime() {
        return requestTime;
    }
    
    @Override
    public String toString() {
        return String.format("Waitlist: %s - Flight %s - %s class (Priority: %d)",
                user.getFullName(), flight.getFlightNumber(), preferredSeatClass, priority);
    }
}