package Managers;

import Contracts.RentalContract;
import Customer.Company;
import Customer.Customer;
import Vehicle.Vehicle;

import java.time.LocalDate;
import java.util.Scanner;

public class ReportManager {

	private final VehicleManager vehicleManager;
	private final CustomerManager customerManager;
	private final RentalManager rentalManager;

	public ReportManager(VehicleManager vehicleManager,
						 CustomerManager customerManager,
						 RentalManager rentalManager) {
		this.vehicleManager = vehicleManager;
		this.customerManager = customerManager;
		this.rentalManager = rentalManager;
	}

	public void showMenu(Scanner scanner) {
		boolean back = false;
		while (!back) {
			System.out.println();
			System.out.println("========== REPORT MENU ==========");
			System.out.println("1. Dashboard summary");
			System.out.println("2. Vehicles report");
			System.out.println("3. Customers report");
			System.out.println("4. Contracts report");
			System.out.println("5. Available vehicles report");
			System.out.println("6. Active rentals report");
			System.out.println("7. Overdue rentals report");
			System.out.println("8. Contracts by customer name");
			System.out.println("9. Company contracts report");
			System.out.println("10. Rentals in date range");
			System.out.println("11. Customers for a vehicle");
			System.out.println("0. Back");

			int choice = readInt(scanner, "Choose an option: ");

			switch (choice) {
				case 1:
					printDashboard();
					break;
				case 2:
					printVehiclesReport();
					break;
				case 3:
					printCustomersReport();
					break;
				case 4:
					printContractsReport();
					break;
				case 5:
					printAvailableVehiclesReport();
					break;
				case 6:
					printActiveContractsReport();
					break;
				case 7:
					printOverdueContractsReport();
					break;
				case 8:
					printContractsByCustomerName(readNonEmpty(scanner, "Customer name: "));
					break;
				case 9:
					printCompanyContracts();
					break;
				case 10:
					printRentalsInPeriod(
							readDate(scanner, "From date (yyyy-MM-dd): "),
							readDate(scanner, "To date (yyyy-MM-dd): "));
					break;
				case 11:
					printCustomersForVehicle(readNonEmpty(scanner, "Vehicle plate number: "));
					break;
				case 0:
					back = true;
					break;
				default:
					System.out.println("Invalid choice.");
			}
		}
	}

	public void printDashboard() {
		int totalVehicles = vehicleManager.getAllVehicles().size();
		int availableVehicles = 0;
		for (Vehicle vehicle : vehicleManager.getAllVehicles()) {
			if (vehicle.isAvailable()) {
				availableVehicles++;
			}
		}

		int totalCustomers = customerManager.getAllCustomers().size();
		int individualCustomers = 0;
		int companyCustomers = 0;
		for (Customer customer : customerManager.getAllCustomers()) {
			if (customer instanceof Company) {
				companyCustomers++;
			} else {
				individualCustomers++;
			}
		}

		int totalContracts = rentalManager.getAllContracts().size();
		int activeContracts = 0;
		int overdueContracts = 0;
		for (RentalContract contract : rentalManager.getAllContracts()) {
			if (contract.isActive()) {
				activeContracts++;
			}
			if (contract.isOverdue()) {
				overdueContracts++;
			}
		}

		System.out.println();
		System.out.println("========== DASHBOARD ==========");
		System.out.println("Vehicles:         " + totalVehicles);
		System.out.println("Available:        " + availableVehicles);
		System.out.println("Customers:        " + totalCustomers);
		System.out.println("Individuals:      " + individualCustomers);
		System.out.println("Companies:        " + companyCustomers);
		System.out.println("Contracts:        " + totalContracts);
		System.out.println("Active contracts: " + activeContracts);
		System.out.println("Overdue contracts: " + overdueContracts);
	}

	public void printVehiclesReport() {
		System.out.println();
		System.out.println("========== VEHICLES REPORT ==========");
		if (vehicleManager.getAllVehicles().isEmpty()) {
			System.out.println("No vehicles found.");
			return;
		}

		for (Vehicle vehicle : vehicleManager.getAllVehicles()) {
			System.out.println(vehicle);
			System.out.println("----------------------");
		}
	}

	public void printCustomersReport() {
		System.out.println();
		System.out.println("========== CUSTOMERS REPORT ==========");
		if (customerManager.getAllCustomers().isEmpty()) {
			System.out.println("No customers found.");
			return;
		}

		for (Customer customer : customerManager.getAllCustomers()) {
			System.out.println(customer);
			System.out.println("----------------------");
		}
	}

	public void printContractsReport() {
		System.out.println();
		System.out.println("========== CONTRACTS REPORT ==========");
		if (rentalManager.getAllContracts().isEmpty()) {
			System.out.println("No contracts found.");
			return;
		}

		for (RentalContract contract : rentalManager.getAllContracts()) {
			System.out.println(contract);
			System.out.println("----------------------");
		}
	}

	public void printAvailableVehiclesReport() {
		System.out.println();
		System.out.println("========== AVAILABLE VEHICLES ==========");
		boolean found = false;
		for (Vehicle vehicle : vehicleManager.getAllVehicles()) {
			if (vehicle.isAvailable()) {
				System.out.println(vehicle);
				System.out.println("----------------------");
				found = true;
			}
		}
		if (!found) {
			System.out.println("No available vehicles at the moment.");
		}
	}

	public void printActiveContractsReport() {
		System.out.println();
		System.out.println("========== ACTIVE CONTRACTS ==========");
		boolean found = false;
		for (RentalContract contract : rentalManager.getAllContracts()) {
			if (contract.isActive()) {
				System.out.println(contract);
				System.out.println("----------------------");
				found = true;
			}
		}
		if (!found) {
			System.out.println("No active contracts found.");
		}
	}

	public void printOverdueContractsReport() {
		System.out.println();
		System.out.println("========== OVERDUE CONTRACTS ==========");
		boolean found = false;
		for (RentalContract contract : rentalManager.getAllContracts()) {
			if (contract.isOverdue()) {
				System.out.println(contract);
				System.out.println("----------------------");
				found = true;
			}
		}
		if (!found) {
			System.out.println("No overdue contracts found.");
		}
	}

	public void printContractsByCustomerName(String name) {
		System.out.println();
		System.out.println("========== CONTRACTS BY CUSTOMER ==========");
		boolean found = false;
		for (RentalContract contract : rentalManager.getAllContracts()) {
			if (contract.getCustomer().getName().equalsIgnoreCase(name)) {
				System.out.println(contract);
				System.out.println("----------------------");
				found = true;
			}
		}
		if (!found) {
			System.out.println("No contracts found for this customer.");
		}
	}

	public void printCompanyContracts() {
		System.out.println();
		System.out.println("========== COMPANY CONTRACTS ==========");
		boolean found = false;
		for (RentalContract contract : rentalManager.getAllContracts()) {
			if (contract.getCustomer() instanceof Company) {
				System.out.println(contract);
				System.out.println("----------------------");
				found = true;
			}
		}
		if (!found) {
			System.out.println("No company contracts found.");
		}
	}

	public void printRentalsInPeriod(LocalDate from, LocalDate to) {
		System.out.println();
		System.out.println("========== RENTALS IN PERIOD ==========");
		boolean found = false;
		for (RentalContract contract : rentalManager.getAllContracts()) {
			LocalDate startDate = contract.getStartDate();
			if (!startDate.isBefore(from) && !startDate.isAfter(to)) {
				System.out.println(contract);
				System.out.println("----------------------");
				found = true;
			}
		}
		if (!found) {
			System.out.println("No rentals found in this period.");
		}
	}

	public void printCustomersForVehicle(String plate) {
		System.out.println();
		System.out.println("========== CUSTOMERS FOR VEHICLE ==========");
		boolean found = false;
		for (RentalContract contract : rentalManager.getAllContracts()) {
			if (contract.getVehicle().getPlateNumber().equalsIgnoreCase(plate)) {
				System.out.println(contract.getCustomer());
				System.out.println("----------------------");
				found = true;
			}
		}
		if (!found) {
			System.out.println("No customers found for this vehicle.");
		}
	}

	private String readNonEmpty(Scanner scanner, String prompt) {
		while (true) {
			System.out.print(prompt);
			String value = scanner.nextLine().trim();
			if (!value.isEmpty()) {
				return value;
			}
			System.out.println("Value cannot be empty.");
		}
	}

	private int readInt(Scanner scanner, String prompt) {
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

	private LocalDate readDate(Scanner scanner, String prompt) {
		while (true) {
			System.out.print(prompt);
			String value = scanner.nextLine().trim();
			try {
				return LocalDate.parse(value);
			} catch (Exception ex) {
				System.out.println("Please use the yyyy-MM-dd format.");
			}
		}
	}
}
