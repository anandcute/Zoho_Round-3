import java.util.*;
public class TicketBooker {
    //63 berths(upper , lower , middle ) + (18 RAC passengers)
    //10 waiting list tickets -> 21L , 21M , 21U , 18RAC , 10WL
    static int availableLowerBerths = 1 ;
    static int availableMiddleBerths = 1 ;
    static int availableUpperBerths = 1 ;
    static int availableRACTickets = 1;
    static int availablewaitingList = 1 ;

    static Queue<Integer> waitingList = new LinkedList<>();
    static Queue<Integer> racList = new LinkedList<>();
    static List<Integer> bookedTicketList = new ArrayList<>();

    static List<Integer> lowerBerthPositions = new ArrayList<>(Arrays.asList(1));
    static List<Integer> middleBerthPositions = new ArrayList<>(Arrays.asList(1));
    static List<Integer> upperBerthPositions = new ArrayList<>(Arrays.asList(1));
    static List<Integer> racListPositions = new ArrayList<>(Arrays.asList(1));
    static List<Integer> waitingListPositions = new ArrayList<>(Arrays.asList(1));

   //id kum passenger kum oru mapping
    static Map<Integer,Passenger> passengers = new HashMap<>();

    // Book ticket
    public void bookTicket(Passenger p , int berthInfo , String allotedBerth){
        //Assign the seat number and type of berth (L ,M, U)
        p.number = berthInfo;
        p.alloted = allotedBerth;
        //Add the passenger to the map
        passengers.put(p.passengerId , p); 
        //Add the passenger id to the booked ticket list
        bookedTicketList.add(p.passengerId);
        System.out.println(".....................Booked succesfull");
        
    }
    public void addToRAC(Passenger p , int racInfo , String allotedRAC){
        //Assign the seat number and type of RAC 
        p.number = racInfo;
        p.alloted = allotedRAC;
        //Add the passenger to the map
        passengers.put(p.passengerId , p);
        //add passenger to the Queue of the RAc ticket
        racList.add(p.passengerId);
        //decrement available RAC ticket by 1
        availableRACTickets--;
        racListPositions.remove(0);
        
        System.out.println("..................added to RAC succesfully");
    }

    public void addToWaitingList(Passenger p,int waitInfo , String allotedWait){
        p.number = waitInfo;
        p.alloted = allotedWait;
        //Added to passenger to the map
        passengers.put(p.passengerId, p);
        waitingList.add(p.passengerId);
        availablewaitingList--;
        waitingListPositions.remove(0);

        System.out.println("-----------added to waiting LIST succesfully");
    }

    // cancel booking

    public void cancelTicket(int passengerId){
        //remove the passenger from the map
        Passenger p = passengers.get(passengerId); // object en store pandrom nu pathina map la iruka value  removw pananum so athula name , age ...elama irukum  
        passengers.remove(passengerId);
        //remove the passenger id from the booked ticket list
        bookedTicketList.remove(passengerId);

        //take the booked position which is now free
        int positionBooked = p.number;

        System.out.println("----------------------cancelled successfully");

        //add  the free positon to the corresponding type of list (either L | M | U)
        if(p.alloted.equals("L")){
            availableLowerBerths++;
            lowerBerthPositions.add(positionBooked);
        }
        else if(p.alloted.equals("M")){
            availableMiddleBerths++;
            middleBerthPositions.add(positionBooked);
        }
        else if(p.alloted.equals("U")){
            availableUpperBerths++;
            upperBerthPositions.add(positionBooked);
        }
         // check if any RAC is there 
         if(racList.size() > 0){
            //take passenger from RAC and increase the free space in RAC list and increae available RAC ticket
            Passenger passengerFromRAC = passengers.get(racList.poll());
            int positionRAC = passengerFromRAC.number;
            racListPositions.add(positionRAC);
            racList.remove(passengerFromRAC.passengerId);
            availableRACTickets++;

            //check if any WL is there
            if(waitingList.size() > 0 ){
                //take the passenger from WL and add them to RAC , increase the free space in waiting list and 
                //increse available Wl and decrease available RAc by 1
                Passenger passengerFromWaitingList = passengers.get(waitingList.poll());
                int positionWL = passengerFromWaitingList.number;
                waitingListPositions.add(positionWL);
                waitingList.remove(passengerFromWaitingList.passengerId);

                passengerFromWaitingList.number = racListPositions.get(0);
                passengerFromWaitingList.alloted = "RAC";
                racListPositions.remove(0);
                racList.add(passengerFromWaitingList.passengerId);

                availablewaitingList++;
                availableRACTickets--;
            }
            //now we have a passenger from RAC to whom we can book a ticket ,
            //so book the cancelled ticket to the RAC Passenger
            Main1.bookTicket(passengerFromRAC); // conform ticket la iruthu cancel panitu poitaga la so rac la iruthu conform ticket move pani aganum.

         } 
    }
    
    //print all Available seats
    public void printAvailable(){
        System.out.println("Available lower berth : " + availableLowerBerths);
        System.out.println("Available middle berth : " + availableMiddleBerths);
        System.out.println("Available upper berth : " + availableUpperBerths);
        System.out.println("Available RAC list :" + availableRACTickets);
        System.out.println("Available waiting list : " + availablewaitingList);
        System.out.println("--------------------------------------");
    }

    // print all occupied passenger from all type including Wl
    public void printPassengers(){
        if(passengers.size() == 0){
            System.out.println("No details of passenger");
            return;
        }
        for(Passenger p : passengers.values()){
            System.out.println("PASSENGER ID : " + p.passengerId);
            System.out.println("NAME : " + p.name);
            System.out.println("AGE : " + p.age);
            System.out.println("GENDER (MALE / FEMALE) : " + p.gender);
            System.out.println("STATUS : " + p.number + p.alloted);
            System.out.println("-------------------");
        }
    }
}
