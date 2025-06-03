import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
enum LeaveStatus {
    PENDING, APPROVED, REJECTED
}

class Employee {
    private int employeeID;
    private String name, phoneNumber, gender, dob, address, bloodGroup, role, dateOfJoining;
    private int age, reportingHeadID;
    private List<String> dependents;

    public Employee(int employeeID, String name, String phoneNumber, String gender, String dob, 
                    String address, String bloodGroup, List<String> dependents, String role, 
                    String dateOfJoining, int reportingHeadID, int age) {
        this.employeeID = employeeID;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.dob = dob;
        this.address = address;
        this.bloodGroup = bloodGroup;
        this.dependents = new ArrayList<>(dependents);
        this.role = role;
        this.dateOfJoining = dateOfJoining;
        this.reportingHeadID = reportingHeadID;
        this.age = age;
    }

    // public void updatePersonalInfo(String dob, int age, String gender, String address) {
    //     this.dob = dob;
    //     this.age = age;
    //     this.gender = gender;
    //     this.address = address;
    // }

    public int getEmployeeID() {
        return employeeID;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getRole() {
        return role;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void displayInfo() {
        System.out.println("\nEmployee ID: " + employeeID);
        System.out.println("Name: " + name);
        System.out.println("Phone: " + phoneNumber);
        System.out.println("Gender: " + gender);
        System.out.println("DOB: " + dob);
        System.out.println("Address: " + address);
        System.out.println("Blood Group: " + bloodGroup);
        System.out.println("Role: " + role);
        System.out.println("Date of Joining: " + dateOfJoining);
        System.out.println("Reporting Head ID: " + reportingHeadID);
        System.out.println("Age: " + age);
        System.out.println("Dependents: " + dependents);
    }
}

class Payroll {
    private int employeeID;
    private double basicSalary, houseRentAllowance, fixedAllowance, giftsBenefits, totalPay;

    public Payroll(int employeeID, double basicSalary, double houseRentAllowance, double fixedAllowance, double giftsBenefits) {
        this.employeeID = employeeID;
        this.basicSalary = basicSalary;
        this.houseRentAllowance = houseRentAllowance;
        this.fixedAllowance = fixedAllowance;
        this.giftsBenefits = giftsBenefits;
        this.totalPay = calculateTotalPay();
    }

    private double calculateTotalPay() {
        return basicSalary + houseRentAllowance + fixedAllowance + giftsBenefits;
    }

    public double getTotalPay() {
        return totalPay;
    }
    public int getEmployeeID() {
        return employeeID;
    }
}

class Leave {
    private static final AtomicInteger leaveCounter = new AtomicInteger(1); // Auto-generate Leave IDs
    private int leaveID;
    private int employeeID;
    private String reasonForLeave;
    private String leaveType;
    private String startDate;
    private String endDate;
    private LeaveStatus leaveStatus;
    private int maxPermittedLeave = 30; // Default max leave per year

    public Leave(int employeeID, String reasonForLeave, String leaveType, 
                 String startDate, String endDate) {
        this.leaveID = leaveCounter.getAndIncrement();
        this.employeeID = employeeID;
        this.reasonForLeave = reasonForLeave;
        this.leaveType = leaveType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.leaveStatus = LeaveStatus.PENDING; // Default status
    }

    public void updateLeaveStatus(LeaveStatus newStatus) {
        this.leaveStatus = newStatus;
    }

    public void displayLeaveDetails() {
        System.out.println("Leave ID: " + leaveID);
        System.out.println("Employee ID: " + employeeID);
        System.out.println("Reason: " + reasonForLeave);
        System.out.println("Leave Type: " + leaveType);
        System.out.println("Start Date: " + startDate);
        System.out.println("End Date: " + endDate);
        System.out.println("Status: " + leaveStatus);
    }

    public int getEmployeeID() {
        return employeeID;
    }
    public int getleaveID() {
        return leaveID;
    }
    public LeaveStatus getLeaveStatus() {
        return leaveStatus;
    }
}


class EmployeeManagementSystem {
    private final Map<Integer, Employee> employees = new HashMap<>();
    private final Map<Integer, Payroll> payrolls = new HashMap<>();
    private final Map<Integer, List<Leave>> leaveRecords = new HashMap<>();
    private final AtomicInteger employeeCounter = new AtomicInteger(1001);
    private final AtomicInteger leaveCounter = new AtomicInteger(2001);

    public int generateEmployeeID() {
        return employeeCounter.getAndIncrement();
    }
    public int generateLeaveID() { return leaveCounter.getAndIncrement(); }

    public void addEmployee(Employee employee, Payroll payroll) {
        employees.put(employee.getEmployeeID(), employee);
        payrolls.put(payroll.getEmployeeID(), payroll);
        leaveRecords.put(employee.getEmployeeID(), new ArrayList<>()); // Initialize leave record
        System.out.println("Employee added successfully!");
    }

    public void deleteEmployee(int employeeID) {
        if (employees.containsKey(employeeID)) {
            employees.remove(employeeID);
            payrolls.remove(employeeID);
            System.out.println("Employee deleted successfully.");
        } else {
            System.out.println("Employee not found.");
        }
    }

    public void updateEmployeeDetails(int employeeID, String phoneNumber, String role) {
        Employee emp = employees.get(employeeID);
        if (emp != null) {
            emp.setPhoneNumber(phoneNumber);
            emp.setRole(role);
            System.out.println("Employee details updated successfully.");
        } else {
            System.out.println("Employee not found.");
        }
    }
    public void applyLeave(int employeeID, String reason, String leaveType, String startDate, String endDate) {
        if (!employees.containsKey(employeeID)) {
            System.out.println("Employee not found.");
            return;
        }

        Leave newLeave = new Leave(employeeID, reason, leaveType, startDate, endDate);
        leaveRecords.get(employeeID).add(newLeave);
        System.out.println("Leave applied successfully! Pending approval.");
    }

    public void viewLeaveRequests() {
        for (List<Leave> leaveList : leaveRecords.values()) {
            for (Leave leave : leaveList) {
                if (leave.getLeaveStatus() == LeaveStatus.PENDING) {
                    leave.displayLeaveDetails();
                }
            }
        }
    }

    public void updateLeaveStatus(int employeeID, int leaveID, LeaveStatus status) {
        List<Leave> leaves = leaveRecords.get(employeeID);
        if (leaves != null) {
            for (Leave leave : leaves) {
                if (leave.getleaveID() == leaveID) {
                    leave.updateLeaveStatus(status);
                    System.out.println("Leave updated successfully.");
                    return;
                }
            }
        }
        System.out.println("Leave request not found.");
    }


    public void viewEmployeeDetails(int employeeID) {
        Employee emp = employees.get(employeeID);
        if (emp != null) {
            emp.displayInfo();
        } else {
            System.out.println("Employee not found.");
        }
    }

    public boolean authenticateEmployee(int employeeID, String phoneNumber) {
        Employee emp = employees.get(employeeID);
        return emp != null && emp.getPhoneNumber().equals(phoneNumber);
    }
    // public void applyLeave(int employeeID, String reason, String type, String startDate, String endDate) {
    //     int leaveID = generateLeaveID();
    //     Leave leave = new Leave(leaveID, employeeID, reason, type, startDate, endDate);
    //     leaves.put(leaveID, leave);
    //     System.out.println("Leave applied successfully.");
    // }
}

public class EmployeeManagementSystemApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        EmployeeManagementSystem ems = new EmployeeManagementSystem();

        while (true) {
            System.out.println("\n1. Admin Login\n2. Employee Login\n3. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    if (adminLogin(scanner)) {
                        adminActionsMenu(scanner, ems);
                    } else {
                        System.out.println("Invalid admin credentials.");
                    }
                    break;
                case 2:
                    if (employeeLogin(scanner, ems)) {
                        employeeActionsMenu(scanner, ems);
                    } else {
                        System.out.println("Invalid employee credentials.");
                    }
                    break;
                case 3:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static boolean adminLogin(Scanner scanner) {
        System.out.print("Enter admin username: ");
        String username = scanner.nextLine();
        System.out.print("Enter admin password: ");
        String password = scanner.nextLine();
        return username.equals("admin") && password.equals("password");
    }

    private static void adminActionsMenu(Scanner scanner, EmployeeManagementSystem ems) {
        while (true) {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. Add Employee\n2. Delete Employee\n3. Update Employee\n4. View Employee\n5. View Leave Requests\n6. Approve/Reject Leave\n7. Logout");
            int choice = scanner.nextInt();
            scanner.nextLine();
    
            switch (choice) {
                case 1:
                    addNewEmployee(scanner, ems);
                    break;
                case 2:
                    System.out.print("Enter Employee ID to delete: ");
                    ems.deleteEmployee(scanner.nextInt());
                    break;
                case 3:
                    System.out.print("Enter Employee ID: ");
                    int empID = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter new phone number: ");
                    String phone = scanner.nextLine();
                    System.out.print("Enter new role: ");
                    String role = scanner.nextLine();
                    ems.updateEmployeeDetails(empID, phone, role);
                    break;
                case 4:
                    System.out.print("Enter Employee ID: ");
                    ems.viewEmployeeDetails(scanner.nextInt());
                    break;
                case 5:
                    ems.viewLeaveRequests();
                    break;
                case 6:
                    System.out.print("Enter Employee ID: ");
                    int empId = scanner.nextInt();
                    System.out.print("Enter Leave ID: ");
                    int leaveId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Approve (A) or Reject (R): ");
                    String status = scanner.nextLine();
                    ems.updateLeaveStatus(empId, leaveId, status.equalsIgnoreCase("A") ? LeaveStatus.APPROVED : LeaveStatus.REJECTED);
                    break;
                case 7:
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
    

    private static boolean employeeLogin(Scanner scanner, EmployeeManagementSystem ems) {
        System.out.print("Enter employee ID: ");
        int empID = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter phone number: ");
        String phone = scanner.nextLine();
        return ems.authenticateEmployee(empID, phone);
    }

    private static void employeeActionsMenu(Scanner scanner, EmployeeManagementSystem ems) {
        System.out.println("\nEmployee Menu:");
        System.out.println("1. View Details\n2. Apply for Leave\n3. Logout");
        int choice = scanner.nextInt();
        scanner.nextLine();
    
        switch (choice) {
            case 1:
                System.out.print("Enter Employee ID: ");
                ems.viewEmployeeDetails(scanner.nextInt());
                break;
            case 2:
                System.out.print("Enter Employee ID: ");
                int employeeID = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Enter reason for leave: ");
                String reason = scanner.nextLine();
                System.out.print("Enter leave type (Sick, Casual, etc.): ");
                String leaveType = scanner.nextLine();
                System.out.print("Enter start date (YYYY-MM-DD): ");
                String startDate = scanner.nextLine();
                System.out.print("Enter end date (YYYY-MM-DD): ");
                String endDate = scanner.nextLine();
                ems.applyLeave(employeeID, reason, leaveType, startDate, endDate);
                break;
            case 3:
                return;
            default:
                System.out.println("Invalid choice.");
        }
    }
    

    private static void addNewEmployee(Scanner scanner, EmployeeManagementSystem ems) {
        System.out.println("Enter Employee Details:");
    
        System.out.print("Name: ");
        String name = scanner.nextLine();
    
        System.out.print("Phone Number: ");
        String phoneNumber = scanner.nextLine();
    
        System.out.print("Gender: ");
        String gender = scanner.nextLine();
    
        System.out.print("Date of Birth (YYYY-MM-DD): ");
        String dob = scanner.nextLine();
    
        System.out.print("Address: ");
        String address = scanner.nextLine();
    
        System.out.print("Blood Group: ");
        String bloodGroup = scanner.nextLine();
    
        System.out.print("Enter dependents (comma-separated): ");
        List<String> dependents = Arrays.asList(scanner.nextLine().split(","));
    
        System.out.print("Role: ");
        String role = scanner.nextLine();
    
        System.out.print("Date of Joining (YYYY-MM-DD): ");
        String dateOfJoining = scanner.nextLine();
    
        System.out.print("Reporting Head ID: ");
        int reportingHeadID = scanner.nextInt();
    
        System.out.print("Age: ");
        int age = scanner.nextInt();
    
        System.out.print("Basic Salary: ");
        double basicSalary = scanner.nextDouble();
    
        System.out.print("House Rent Allowance: ");
        double houseRentAllowance = scanner.nextDouble();
    
        System.out.print("Fixed Allowance: ");
        double fixedAllowance = scanner.nextDouble();
    
        System.out.print("Gifts/Benefits: ");
        double giftsBenefits = scanner.nextDouble();
        
        scanner.nextLine(); // Consume the newline
    
        int id = ems.generateEmployeeID(); // Assuming this method generates a unique ID
    
        Employee newEmp = new Employee(id, name, phoneNumber, gender, dob, address, bloodGroup, 
                                       dependents, role, dateOfJoining, reportingHeadID, age);
    
        Payroll payroll = new Payroll(id, basicSalary, houseRentAllowance, fixedAllowance, giftsBenefits);
    
        ems.addEmployee(newEmp, payroll);
        // System.out.println("Employee added successfully.");
    }
    
}
