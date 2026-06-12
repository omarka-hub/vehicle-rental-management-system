import Customer.Company;
import Customer.Customer;
import Customer.Individual;
import Managers.CustomerManager;
import Managers.RentalManager;
import Managers.ReportManager;
import Managers.VehicleManager;
import Vehicle.Car;
import Vehicle.FuelType;
import Vehicle.Motorcycle;
import Vehicle.Truck;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        VehicleManager vehicleManager = new VehicleManager();
        CustomerManager customerManager = new CustomerManager();
        RentalManager rentalManager = new RentalManager(vehicleManager, customerManager);
        ReportManager reportManager = new ReportManager(vehicleManager, customerManager, rentalManager);

        seedDemoData(vehicleManager, customerManager, rentalManager);

        boolean running = true;
        while (running) {
            System.out.println();
            System.out.println("========== MAIN MENU ==========");
            System.out.println("1. Vehicles");
            System.out.println("2. Customers");
            System.out.println("3. Rentals");
            System.out.println("4. Reports");
            System.out.println("0. Exit");

            int choice = readInt(scanner, "Choose an option: ");

            switch (choice) {
                case 1:
                    handleVehicleMenu(scanner, vehicleManager);
                    break;
                case 2:
                    handleCustomerMenu(scanner, customerManager);
                    break;
                case 3:
                    handleRentalMenu(scanner, rentalManager);
                    break;
                case 4:
                    handleReportMenu(scanner, reportManager);
                    break;
                case 0:
                    running = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }

        scanner.close();
    }

    private static void seedDemoData(VehicleManager vehicleManager,
                                     CustomerManager customerManager,
                                     RentalManager rentalManager) {
        vehicleManager.addVehicle(new Car("CAR-001", "Toyota", "Camry", 50.0, FuelType.PETROL, true, 5));
        vehicleManager.addVehicle(new Motorcycle("MOTO-101", "Yamaha", "MT-07", 35.0, 689.0, false));
        vehicleManager.addVehicle(new Truck("TRK-201", "Volvo", "FH", 120.0, 12.0, true));

        customerManager.addCustomer(new Individual(
                "CUST-001",
                "Ahmed Ali",
                "Riyadh",
                "0500000001",
                "D1234567",
                LocalDate.of(1998, 4, 12)));

        customerManager.addCustomer(new Company(
                "CUST-002",
                "Alpha Logistics",
                "Jeddah",
                "0500000002",
                "CR-88991",
                0.15));

        rentalManager.rentVehicle("CAR-001", "CUST-001", LocalDate.now().minusDays(2), 7);
    }


    private static void handleVehicleMenu(Scanner scanner, VehicleManager vehicleManager) {
        boolean back = false;
        while (!back) {
            System.out.println();
            System.out.println("========== VEHICLE MENU ==========");
            System.out.println("1. Add car");
            System.out.println("2. Add motorcycle");
            System.out.println("3. Add truck");
            System.out.println("4. Show all vehicles");
            System.out.println("5. Show available vehicles");
            System.out.println("6. Remove vehicle");
            System.out.println("7. Find vehicle by plate");
            System.out.println("0. Back");

            int choice = readInt(scanner, "Choose an option: ");

            switch (choice) {
                case 1:
                    vehicleManager.addVehicle(new Car(
                            readNonEmpty(scanner, "Plate number: "),
                            readNonEmpty(scanner, "Brand: "),
                            readNonEmpty(scanner, "Model: "),
                            readDouble(scanner, "Daily price: "),
                            readFuelType(scanner),
                            readBoolean(scanner, "Has AC (yes/no): "),
                            readInt(scanner, "Number of seats: ")));
                    break;
                case 2:
                    vehicleManager.addVehicle(new Motorcycle(
                            readNonEmpty(scanner, "Plate number: "),
                            readNonEmpty(scanner, "Brand: "),
                            readNonEmpty(scanner, "Model: "),
                            readDouble(scanner, "Daily price: "),
                            readDouble(scanner, "Engine capacity: "),
                            readBoolean(scanner, "Has sidecar (yes/no): ")));
                    break;
                case 3:
                    vehicleManager.addVehicle(new Truck(
                            readNonEmpty(scanner, "Plate number: "),
                            readNonEmpty(scanner, "Brand: "),
                            readNonEmpty(scanner, "Model: "),
                            readDouble(scanner, "Daily price: "),
                            readDouble(scanner, "Load capacity in tons: "),
                            readBoolean(scanner, "Is refrigerated (yes/no): ")));
                    break;
                case 4:
                    if (vehicleManager.getAllVehicles().isEmpty()) {
                        System.out.println("No vehicles found.");
                    } else {
                        vehicleManager.displayAll();
                    }
                    break;
                case 5:
                    vehicleManager.displayAvailable();
                    break;
                case 6:
                    vehicleManager.removeVehicle(readNonEmpty(scanner, "Plate number to remove: "));
                    break;
                case 7: {
                    String plateNumber = readNonEmpty(scanner, "Plate number: ");
                    if (vehicleManager.findByPlate(plateNumber) == null) {
                        System.out.println("Vehicle not found!");
                    } else {
                        System.out.println(vehicleManager.findByPlate(plateNumber));
                    }
                    break;
                }
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }



    private static void handleCustomerMenu(Scanner scanner, CustomerManager customerManager) {
        boolean back = false;
        while (!back) {
            System.out.println();
            System.out.println("========== CUSTOMER MENU ==========");
            System.out.println("1. Add individual customer");
            System.out.println("2. Add company customer");
            System.out.println("3. Show all customers");
            System.out.println("4. Find customer by ID");
            System.out.println("5. Find customer by name");
            System.out.println("6. Update customer");
            System.out.println("0. Back");


            int choice = readInt(scanner, "Choose an option: ");

            switch (choice) {
                case 1:
                    customerManager.addCustomer(new Individual(
                            readNonEmpty(scanner, "Customer ID: "),
                            readNonEmpty(scanner, "Name: "),
                            readNonEmpty(scanner, "Address: "),
                            readNonEmpty(scanner, "Phone number: "),
                            readNonEmpty(scanner, "License number: "),
                            readDate(scanner, "Date of birth (yyyy-MM-dd): ")));
                    System.out.println("Customer added successfully.");
                    break;
                case 2:
                    customerManager.addCustomer(new Company(
                            readNonEmpty(scanner, "Customer ID: "),
                            readNonEmpty(scanner, "Company name: "),
                            readNonEmpty(scanner, "Address: "),
                            readNonEmpty(scanner, "Phone number: "),
                            readNonEmpty(scanner, "Commercial register: "),
                            readDouble(scanner, "Discount rate (0.0 to 0.30): ")));
                    System.out.println("Customer added successfully.");
                    break;
                case 3:
                    if (customerManager.getAllCustomers().isEmpty()) {
                        System.out.println("No customers found.");
                    } else {
                        customerManager.displayAll();
                    }
                    break;
                case 4: {
                    Customer customer = customerManager.findCustomer(readNonEmpty(scanner, "Customer ID: "));
                    if (customer == null) {
                        System.out.println("Customer not found!");
                    } else {
                        System.out.println(customer);
                    }
                    break;
                }
                case 5: {
                    Customer customer = customerManager.findByName(readNonEmpty(scanner, "Customer name: "));
                    if (customer == null) {
                        System.out.println("Customer not found!");
                    } else {
                        System.out.println(customer);
                    }
                    break;
                }
                case 6:
                    customerManager.updateCustomer(
                            readNonEmpty(scanner, "Customer ID: "),
                            readNonEmpty(scanner, "Field name (name, phone, address): "),
                            readNonEmpty(scanner, "New value: "));
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void handleRentalMenu(Scanner scanner, RentalManager rentalManager) {
        boolean back = false;
        while (!back) {
            System.out.println();
            System.out.println("========== RENTAL MENU ==========");
            System.out.println("1. Rent vehicle");
            System.out.println("2. Return vehicle");
            System.out.println("3. Show all contracts");
            System.out.println("4. Show active contracts");
            System.out.println("5. Show overdue contracts");
            System.out.println("6. Show contracts by customer name");
            System.out.println("7. Show company contracts");
            System.out.println("8. Show customers for a vehicle");
            System.out.println("9. Show rentals in a date range");
            System.out.println("0. Back");

            int choice = readInt(scanner, "Choose an option: ");

            switch (choice) {
                case 1:
                    rentalManager.rentVehicle(
                            readNonEmpty(scanner, "Vehicle plate number: "),
                            readNonEmpty(scanner, "Customer ID: "),
                            readDate(scanner, "Start date (yyyy-MM-dd): "),
                            readInt(scanner, "Rental days: "));
                    break;
                case 2:
                    rentalManager.returnVehicle(
                            readNonEmpty(scanner, "Contract ID: "),
                            readDate(scanner, "Return date (yyyy-MM-dd): "));
                    break;
                case 3:
                    if (rentalManager.getAllContracts().isEmpty()) {
                        System.out.println("No contracts found.");
                    } else {
                        rentalManager.displayAllContracts(false);
                    }
                    break;
                case 4:
                    if (rentalManager.getAllContracts().isEmpty()) {
                        System.out.println("No contracts found.");
                    } else {
                        rentalManager.displayCurrentlyRented();
                    }
                    break;
                case 5:
                    if (rentalManager.getAllContracts().isEmpty()) {
                        System.out.println("No contracts found.");
                    } else {
                        rentalManager.displayOverdueVehicles();
                    }
                    break;
                case 6:
                    rentalManager.displayContractsByCustomerName(readNonEmpty(scanner, "Customer name: "));
                    break;
                case 7:
                    rentalManager.displayContractsByCompanies();
                    break;
                case 8:
                    rentalManager.displayCustomersForVehicle(readNonEmpty(scanner, "Vehicle plate number: "));
                    break;
                case 9:
                    rentalManager.displayRentedInPeriod(
                            readDate(scanner, "From date (yyyy-MM-dd): "),
                            readDate(scanner, "To date (yyyy-MM-dd): "));
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void handleReportMenu(Scanner scanner, ReportManager reportManager) {
        reportManager.showMenu(scanner);
    }

    private static String readNonEmpty(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim();
            if (!value.isEmpty()) {
                return value;
            }
            System.out.println("Value cannot be empty.");
        }
    }

    
    private static int readInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim();
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException ex) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static double readDouble(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim();
            try {
                return Double.parseDouble(value);
            } catch (NumberFormatException ex) {
                System.out.println("Please enter a valid decimal number.");
            }
        }
    }

    private static boolean readBoolean(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim().toLowerCase();
            if (value.equals("yes") || value.equals("y") || value.equals("true")) {
                return true;
            }
            if (value.equals("no") || value.equals("n") || value.equals("false")) {
                return false;
            }
            System.out.println("Please answer yes or no.");
        }
    }

    private static LocalDate readDate(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim();
            try {
                return LocalDate.parse(value);
            } catch (DateTimeParseException ex) {
                System.out.println("Please use the yyyy-MM-dd format.");
            }
        }
    }

    private static FuelType readFuelType(Scanner scanner) {
        while (true) {
            System.out.println("Fuel type options: PETROL, DIESEL, ELECTRIC");
            System.out.print("Fuel type: ");
            String value = scanner.nextLine().trim().toUpperCase();
            try {
                return FuelType.valueOf(value);
            } catch (IllegalArgumentException ex) {
                System.out.println("Invalid fuel type.");
            }
        }
    }
}
