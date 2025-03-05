package TaxiApp;

import java.util.*;

public class CallTaxiBookingSystem {
    private static final int DISTANCE_BETWEEN_POINTS = 15; // 15 km between points A, B, C, D, E
    private static final int BASE_FARE = 50; // Rs 50 for first 5 km
    private static final int ADDITIONAL_FARE_PER_KM = 10; // Rs 10 per additional km

    private List<Taxi> taxis;
    private int bookingIdCounter = 1;
    private Set<String> existingCustomerIds;  // Set to store unique customer IDs
    private Scanner scanner;

    public CallTaxiBookingSystem(int n) {
        taxis = new ArrayList<>();
        existingCustomerIds = new HashSet<>();
        scanner = new Scanner(System.in);
        for (int i = 1; i <= n; i++) {
            taxis.add(new Taxi("Taxi-" + i));
        }
    }

    //check if the customer ID already exists
    private boolean isCustomerIdUnique(String customerId) {
        return !existingCustomerIds.contains(customerId);
    }

    public void handleBooking() {
        System.out.print("Enter Customer ID: ");
        String customerId = scanner.nextLine();

        // Validate that customer ID is unique
        while (!isCustomerIdUnique(customerId)) {
            System.out.println("Error: Customer ID already exists. Please enter a unique Customer ID.");
            System.out.print("Enter Customer ID: ");
            customerId = scanner.nextLine();
        }

        // Add the new customer ID to the set
        existingCustomerIds.add(customerId);

        String start = getLocation("Enter Starting Point (A/B/C/D/E): ");
        String destination = getLocation("Enter Destination Point (A/B/C/D/E): ");
        
        if (start.equals(destination)) {
            System.out.println("Error: Start point and destination cannot be the same.");
            return;
        }
        
        String startTime = getValidStartTime();

        // Find an available taxi
        Taxi allocatedTaxi = findAvailableTaxi(start);
        if (allocatedTaxi == null) {
            System.out.println("No available taxi at the requested location.");
            return;
        }

        // Calculate the fare
        int distance = Math.abs(getPointIndex(destination) - getPointIndex(start)) * DISTANCE_BETWEEN_POINTS;
        double fare = calculateFare(distance);

        // Calculate drop time (15 mins per point)
        int travelTime = Math.abs(getPointIndex(destination) - getPointIndex(start)) * 15; // in minutes
        String endTime = calculateEndTime(startTime, travelTime);

        // Print the booking confirmation
        System.out.println("Booking ID: " + bookingIdCounter++);
        System.out.println("Allocated Taxi: " + allocatedTaxi.taxiName);
        System.out.println("------------------------------------------");
    }

    // check if the location is valid
    private boolean isValidLocation(String location) {
        return location.equals("A") || location.equals("B") || location.equals("C") || location.equals("D") || location.equals("E");
    }

    // Method to repeatedly ask for location until valid input is provided
    private String getLocation(String prompt) {
        String location;
        while (true) {
            System.out.print(prompt);
            location = scanner.nextLine().toUpperCase();
            if (isValidLocation(location)) {
                return location;
            } else {
                System.out.println("Invalid location. Please choose a valid location (A/B/C/D/E).");
            }
        }
    }

    private Taxi findAvailableTaxi(String startLocation) {
        Taxi availableTaxi = null;

        for (Taxi taxi : taxis) {
            // Check if taxi is at the same location and is available
            if (taxi.currentLocation.equals(startLocation)) {
                if (availableTaxi == null) {
                    availableTaxi = taxi;
                }
            }
        }

        return availableTaxi;
    }

    private double calculateFare(int distance) {
        int additionalDistance = Math.max(0, distance - 5); // First 5 km are Rs 50
        return BASE_FARE + (additionalDistance * ADDITIONAL_FARE_PER_KM);
    }

    private int getPointIndex(String point) {
        switch (point) {
            case "A": return 0;
            case "B": return 1;
            case "C": return 2;
            case "D": return 3;
            case "E": return 4;
            default: return -1;
        }
    }

    private String calculateEndTime(String startTime, int travelTimeInMinutes) {
        // Simple time calculation assuming start time is in "HH:MM AM/PM" format
        int startHour = Integer.parseInt(startTime.split(":")[0]);
        int startMinute = Integer.parseInt(startTime.split(":")[1].split(" ")[0]);
        int travelMinutes = startMinute + travelTimeInMinutes;

        int endHour = startHour + (travelMinutes / 60);
        int endMinute = travelMinutes % 60;

        return String.format("%02d:%02d", endHour, endMinute);
    }

    // Validate Start Time input format
    private String getValidStartTime() {
        String startTime;
        while (true) {
            System.out.print("Enter Start Time (HH:MM AM/PM): ");
            startTime = scanner.nextLine();
            
            if (isValidTime(startTime)) {
                return startTime;
            } else {
                System.out.println("Invalid time format. Please enter a valid time (HH:MM AM/PM).");
            }
        }
    }

    // Method to validate time format (HH:MM AM/PM)
    private boolean isValidTime(String time) {
        try {
            String[] parts = time.split(" ");
            if (parts.length != 2) return false;

            String[] timeParts = parts[0].split(":");
            if (timeParts.length != 2) return false;

            int hour = Integer.parseInt(timeParts[0]);
            int minute = Integer.parseInt(timeParts[1]);
            String period = parts[1].toUpperCase();

            // Validate hour, minute, and period (AM/PM)
            if (hour < 1 || hour > 12 || minute < 0 || minute > 59 || (!period.equals("AM") && !period.equals("PM"))) {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void displayTravelHistory() {
        System.out.print("Enter Taxi Name (e.g., Taxi-1): ");
        String taxiName = scanner.nextLine();
        Taxi taxi = findTaxiByName(taxiName);
        if (taxi != null) {
            taxi.displayTravelHistory();
        } else {
            System.out.println("Taxi not found.");
        }
    }

    private Taxi findTaxiByName(String taxiName) {
        for (Taxi taxi : taxis) {
            if (taxi.taxiName.equals(taxiName)) {
                return taxi;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        // Instantiate CallTaxiBookingSystem with 5 taxis
    	CallTaxiBookingSystem system = new CallTaxiBookingSystem(5);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            // Allow user to make multiple bookings or check travel history
            System.out.println("\nWelcome to the Call Taxi Booking System");
            System.out.println("1. Make a new booking");
            System.out.println("2. Display travel history of a taxi");
            System.out.println("3. Exit");
            System.out.print("Choose an option (1/2/3): ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // consume the newline

            switch (choice) {
                case 1:
                    system.handleBooking();
                    break;
                case 2:
                    system.displayTravelHistory();
                    break;
                case 3:
                    System.out.println("Exiting the system...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}







