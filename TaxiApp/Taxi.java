// package TaxiApp;

import java.util.ArrayList;
import java.util.List;

class Taxi {
	String taxiName;
    String currentLocation;
    double totalEarnings;
    List<Trip> travelHistory;

    public Taxi(String taxiName) {
        this.taxiName = taxiName;
        this.currentLocation = "A";  
        this.totalEarnings = 0;
        this.travelHistory = new ArrayList<>();
    }

    // Method to add a trip to the taxi's history
    public void addTrip(Trip trip) {
        this.travelHistory.add(trip);
        this.totalEarnings += trip.fare;
    }

   

    public void displayTravelHistory() {
        System.out.println("Travel History of " + taxiName + ":");
        System.out.println("Trip\tStart\tDestination\tStartTime\tEndTime\t\tFare");
        for (int i = 0; i < travelHistory.size(); i++) {
            Trip trip = travelHistory.get(i);
            System.out.printf("%d\t%s\t%s\t%s\t\t%s\t\t%.2f\n", 
                              i + 1, 
                              trip.start, 
                              trip.destination, 
                              trip.startTime, 
                              trip.endTime, 
                              trip.fare);
        }
    }
}
