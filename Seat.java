package flightreservation;

public class Seat {
    private String seatNumber;
    private SeatClass seatClass;
    private boolean isAvailable;
    
    public Seat(String seatNumber, SeatClass seatClass, boolean isAvailable) {
        this.seatNumber = seatNumber;
        this.seatClass = seatClass;
        this.isAvailable = isAvailable;
    }
    
    // Getters and setters
    public String getSeatNumber() {
        return seatNumber;
    }
    
    public SeatClass getSeatClass() {
        return seatClass;
    }
    
    public boolean isAvailable() {
        return isAvailable;
    }
    
    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }
    
    @Override
    public String toString() {
        return String.format("Seat %s (%s) - %s", 
                seatNumber, seatClass, isAvailable ? "Available" : "Booked");
    }
}