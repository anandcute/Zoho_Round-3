import java.util.Scanner;

public class Main1 {

    // function for booking ticket
    public static void bookTicket(Passenger p) {
        TicketBooker booker = new TicketBooker();
        /// IF no WL is available , means all tickets are booked .. so no ticket
        /// available
        if (TicketBooker.availablewaitingList == 0) {
            System.out.println("No Ticket Available");
            return;
        }
        // check if prefered berth is available
        if ((p.berthPeference.equals("L") && TicketBooker.availableLowerBerths > 0) ||
                (p.berthPeference.equals("M") && TicketBooker.availableMiddleBerths > 0) ||
                (p.berthPeference.equals("U") && TicketBooker.availableUpperBerths > 0)) {
            System.out.println("Prefered ticket is available..");
            // if available then book the ticket
            if (p.berthPeference.equals("L")) {
                System.out.println("Lower berth Given");
                // call booking function in the ticket booker cls
                booker.bookTicket(p, (TicketBooker.lowerBerthPositions.get(0)), "L");
                // remove the booked position from available position and also decrease
                // available seats of that
                // particular type
                TicketBooker.lowerBerthPositions.remove(0);
                TicketBooker.availableLowerBerths--;
            }

            else if (p.berthPeference.equals("M")) {
                System.out.println("Middle berth Given");
                // call booking function in the ticket booker cls
                booker.bookTicket(p, (TicketBooker.middleBerthPositions.get(0)), "M");
                // remove the booked position from available position and also decrease
                // available seats of that
                // particular type
                TicketBooker.middleBerthPositions.remove(0);
                TicketBooker.availableMiddleBerths--;
            }

            else if (p.berthPeference.equals("U")) {
                System.out.println("Upper berth Given");
                // call booking function in the ticket booker cls
                booker.bookTicket(p, (TicketBooker.upperBerthPositions.get(0)), "U");
                // remove the booked position from available position and also decrease
                // available seats of that
                // particular type
                TicketBooker.upperBerthPositions.remove(0);
                TicketBooker.availableUpperBerths--;
            }
        }
        // preferred not available -> book the available berth ( incase passenger L katu
        // irukaga but lower already finished so next ethula ticket iruko athula book
        // panidum)
        else if (TicketBooker.availableLowerBerths > 0) {
            System.out.println("Lower berth Given");
            // call booking function in the ticket booker cls
            booker.bookTicket(p, (TicketBooker.lowerBerthPositions.get(0)), "L");
            // remove the booked position from available position and also decrease
            // available seats of that
            // particular type
            TicketBooker.lowerBerthPositions.remove(0);
            TicketBooker.availableLowerBerths--;
        } else if (TicketBooker.availableMiddleBerths > 0) {
            System.out.println("Middle berth Given");
            // call booking function in the ticket booker cls
            booker.bookTicket(p, (TicketBooker.middleBerthPositions.get(0)), "M");
            // remove the booked position from available position and also decrease
            // available seats of that
            // particular type
            TicketBooker.middleBerthPositions.remove(0);
            TicketBooker.availableMiddleBerths--;
        } else if (TicketBooker.availableUpperBerths > 0) {
            System.out.println("Upper berth Given");
            // call booking function in the ticket booker cls
            booker.bookTicket(p, (TicketBooker.upperBerthPositions.get(0)), "U");
            // remove the booked position from available position and also decrease
            // available seats of that
            // particular type
            TicketBooker.upperBerthPositions.remove(0);
            TicketBooker.availableUpperBerths--;
        }
        // if no berth available go to RAC
        else if (TicketBooker.availableRACTickets > 0) {
            System.out.println("RAC Available");
            // call booking function in the ticket booker cls
            booker.addToRAC(p, (TicketBooker.racListPositions.get(0)), "RAC");
        } else if (TicketBooker.availablewaitingList > 0) {
            System.out.println("Added to Waiting List Available");
            // call booking function in the ticket booker cls
            booker.addToWaitingList(p, (TicketBooker.waitingListPositions.get(0)), "WL");
        }

    }

    // cancel ticket function

    public static void cancelTicket(int id) {
        TicketBooker booker = new TicketBooker();
        // check if passenger id is valid
        if (!booker.passengers.containsKey(id)) {
            System.out.println("passenger detail is unknown");
        } else {
            booker.cancelTicket(id);
        }
    }

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        boolean loop = true;
        while (loop) {
            System.out.println(
                    "\n 1.Book Ticket \n 2.Cancel Ticket \n 3.Available Tickets \n 4.Booked Tickets \n 5.Exits");
            int choice = s.nextInt();
            switch (choice) {
                // book ticket
                case 1: {
                    System.out.println("Enter the Passenger name : ");
                    String name = s.next();
                    System.out.println("Enter the Passenger Age : ");
                    int age = s.nextInt();
                    System.out.println("Enter the Passenger Gender(MALE/FEMALE) : ");
                    String gender = s.next();
                    System.out.println("Berth performance (L Or M Or U) : ");
                    String berthPreference = s.next();
                    // create a passenger object
                    Passenger p = new Passenger(name, age, gender, berthPreference);
                    // booking
                    bookTicket(p);
                }
                    break;
                // cancel booking
                case 2: {
                    // get passenger to cancel
                    System.out.println("Enter passenger ID to cancel");
                    int id = s.nextInt();
                    cancelTicket(id);
                }
                    break;
                case 3: {
                    TicketBooker booker = new TicketBooker();
                    booker.printAvailable();
                }
                    break;
                case 4: {
                    TicketBooker booker = new TicketBooker();
                    booker.printPassengers();
                }
                    break;
                case 5:
                    s.close();
                    loop = false;
                    break;
                default:
                    System.out.println("Enter the vaild number");
                    break;

            }

        }

    }
}