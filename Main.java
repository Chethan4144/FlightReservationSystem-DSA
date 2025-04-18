package flightreservation;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static FlightManagementSystem system = new FlightManagementSystem();
    private static WaitListManager waitlistManager = new WaitListManager();
    private static Scanner scanner = new Scanner(System.in);
    private static User currentUser = null;
    private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    
    public static void main(String[] args) {
        initializeSystem();
        
        while (true) {
            if (currentUser == null) {
                showLoginMenu();
            } else {
                showMainMenu();
            }
        }
    }
    
    private static void initializeSystem() {
        // Add sample flights
        Flight flight1 = new Flight("AI101", "Delhi", "Mumbai", 
                LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(1).plusHours(2), 
                50, 4000.0);
        Flight flight2 = new Flight("AI102", "Mumbai", "Delhi", 
                LocalDateTime.now().plusDays(2), LocalDateTime.now().plusDays(2).plusHours(2), 
                50, 4500.0);
        Flight flight3 = new Flight("AI201", "Delhi", "Hyderabad", 
                LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(1).plusHours(3), 
                40, 5000.0);
        Flight flight4 = new Flight("AI202", "Bangalore", "Delhi", 
                LocalDateTime.now().plusDays(3), LocalDateTime.now().plusDays(3).plusHours(3), 
                40, 5500.0);
        
        system.addFlight(flight1);
        system.addFlight(flight2);
        system.addFlight(flight3);
        system.addFlight(flight4);
        
        // Add sample users
        User user1 = new User("john", "password", "John Doe", "john@example.com", "9876543210");
        User user2 = new User("jane", "password", "Jane Smith", "jane@example.com", "9876543211");
        
        system.registerUser(user1);
        system.registerUser(user2);
    }
    
    private static void showLoginMenu() {
        System.out.println("\n==== Flight Reservation System ====");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        System.out.print("Choose an option: ");
        
        int choice = Integer.parseInt(scanner.nextLine());
        
        switch (choice) {
            case 1:
                login();
                break;
            case 2:
                register();
                break;
            case 3:
                System.out.println("Thank you for using Flight Reservation System.");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }
    
    private static void login() {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        
        User user = system.getUserByUsername(username);
        
        if (user != null && user.getPassword().equals(password)) {
            currentUser = user;
            System.out.println("Login successful! Welcome, " + user.getFullName() + ".");
        } else {
            System.out.println("Invalid username or password.");
        }
    }
    
    private static void register() {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        
        if (system.getUserByUsername(username) != null) {
            System.out.println("Username already exists.");
            return;
        }
        
        System.out.print("Password: ");
        String password = scanner.nextLine();
        System.out.print("Full Name: ");
        String fullName = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Phone Number: ");
        String phoneNumber = scanner.nextLine();
        
        User newUser = new User(username, password, fullName, email, phoneNumber);
        system.registerUser(newUser);
        
        System.out.println("Registration successful! You can now login.");
    }
    
    private static void showMainMenu() {
        System.out.println("\n==== Main Menu ====");
        System.out.println("1. Search Flights");
        System.out.println("2. View My Reservations");
        System.out.println("3. Cancel Reservation");
        System.out.println("4. Logout");
        System.out.print("Choose an option: ");
        
        int choice = Integer.parseInt(scanner.nextLine());
        
        switch (choice) {
            case 1:
                searchFlights();
                break;
            case 2:
                viewMyReservations();
                break;
            case 3:
                cancelReservation();
                break;
            case 4:
                currentUser = null;
                System.out.println("Logged out successfully.");
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }
    
    // In Main.java, modify the searchFlights method:
    // In Main.java, alternative searchFlights method:

private static void searchFlights() {
    System.out.print("Origin: ");
    String origin = scanner.nextLine();
    System.out.print("Destination: ");
    String destination = scanner.nextLine();
    
    // Find all flights matching origin and destination, ignoring date
    List<Flight> matchingFlights = new ArrayList<>();
    
    for (Flight flight : system.getFlights().values()) {
        if (flight.getOrigin().equalsIgnoreCase(origin) && 
            flight.getDestination().equalsIgnoreCase(destination)) {
            matchingFlights.add(flight);
        }
    }
    
    if (matchingFlights.isEmpty()) {
        System.out.println("No flights found for your search criteria.");
        return;
    }
    
    System.out.println("\n==== Available Flights ====");
    for (int i = 0; i < matchingFlights.size(); i++) {
        Flight f = matchingFlights.get(i);
        System.out.println((i + 1) + ". " + f + " (Date: " + 
                f.getDepartureTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + ")");
    }
    
    // Rest of the method remains the same...
    System.out.print("Select a flight to book (0 to cancel): ");
    int flightChoice = Integer.parseInt(scanner.nextLine());
    
    if (flightChoice < 1 || flightChoice > matchingFlights.size()) {
        return;
    }
    
    Flight selectedFlight = matchingFlights.get(flightChoice - 1);
    
    // Continue with booking...
    System.out.println("\nSelect seat class:");
    System.out.println("1. Economy");
    System.out.println("2. Business");
    System.out.print("Choose an option: ");
    
    int classChoice = Integer.parseInt(scanner.nextLine());
    SeatClass seatClass = (classChoice == 2) ? SeatClass.BUSINESS : SeatClass.ECONOMY;
    
    // Calculate price
    double price = selectedFlight.calculatePrice(seatClass);
    System.out.printf("Ticket price: $%.2f\n", price);
    System.out.print("Confirm booking (Y/N)? ");
    
    String confirm = scanner.nextLine();
    
    if (confirm.equalsIgnoreCase("Y")) {
        Reservation reservation = system.createReservation(currentUser, selectedFlight, seatClass);
        
        if (reservation != null) {
            System.out.println("Booking successful!");
            System.out.println(reservation);
        } else {
            System.out.println("No available seats of the selected class.");
            System.out.print("Would you like to join the waitlist (Y/N)? ");
            
            String joinWaitlist = scanner.nextLine();
            
            if (joinWaitlist.equalsIgnoreCase("Y")) {
                WaitlistEntry entry = new WaitlistEntry(currentUser, selectedFlight, seatClass, 1);
                waitlistManager.addToWaitlist(entry);
                System.out.println("You've been added to the waitlist.");
            }
        }
    }
}
    
    private static void viewMyReservations() {
        List<Reservation> reservations = currentUser.getReservations();
        
        if (reservations.isEmpty()) {
            System.out.println("You have no reservations.");
            return;
        }
        
        System.out.println("\n==== Your Reservations ====");
        for (int i = 0; i < reservations.size(); i++) {
            System.out.println((i + 1) + ". " + reservations.get(i));
        }
    }
    
    private static void cancelReservation() {
        List<Reservation> reservations = currentUser.getReservations();
        
        if (reservations.isEmpty()) {
            System.out.println("You have no reservations to cancel.");
            return;
        }
        
        System.out.println("\n==== Your Reservations ====");
        for (int i = 0; i < reservations.size(); i++) {
            System.out.println((i + 1) + ". " + reservations.get(i));
        }
        
        System.out.print("Select a reservation to cancel (0 to cancel): ");
        int choice = Integer.parseInt(scanner.nextLine());
        
        if (choice < 1 || choice > reservations.size()) {
            return;
        }
        
        Reservation selectedReservation = reservations.get(choice - 1);
        
        if (system.cancelReservation(currentUser, selectedReservation.getReservationId())) {
            System.out.println("Reservation cancelled successfully.");
            
            // Check if anyone is on waitlist for this flight
            String flightId = selectedReservation.getFlight().getFlightId();
            WaitlistEntry nextInLine = waitlistManager.getNextInWaitlist(flightId);
            
            if (nextInLine != null) {
                System.out.println("Notifying next person on waitlist...");
                // In a real system, this would trigger a notification
            }
        } else {
            System.out.println("Failed to cancel. Cancellation is only allowed 24 hours before departure.");
        }
    }
}