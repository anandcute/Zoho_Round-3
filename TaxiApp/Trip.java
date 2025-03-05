package TaxiApp;

class Trip {
    String customerId;
    String start;
    String destination;
    String startTime;
    String endTime;
    double fare;

    public Trip(String customerId, String start, String destination, String startTime, String endTime, double fare) {
        this.customerId = customerId;
        this.start = start;
        this.destination = destination;
        this.startTime = startTime;
        this.endTime = endTime;
        this.fare = fare;
    }
}
