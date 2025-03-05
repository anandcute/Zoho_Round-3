// package Zoho_Round-3;
public class Passenger {
    static int id = 1 ; // static variable to give id for every new passenger
    String name;
    int age;
    String gender;
    String berthPeference;   // U ,or M OR L  
    int passengerId;  // Id of passenger create automatically 
    String alloted; // alloacted type (L,M,M,RAC,WL)
    int number; // seat number

    Passenger(String name , int age ,String gender,String berthPeference){
        this.name = name;
        this.age = age;
        this.gender= gender;
        this.berthPeference=berthPeference;
        this.passengerId = id++;
        alloted = "";
        number = -1;
    }
}
