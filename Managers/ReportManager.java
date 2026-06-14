package Managers;

import Contracts.RentalContract;
import Customer.Company;
import Customer.Customer;
import Vehicle.Vehicle;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

	public void printRevenueReport() {
		System.out.println();
		System.out.println("========== COMPANY REVENUE ==========");
		if (rentalManager.getAllContracts().isEmpty()) {
			System.out.println("No contracts found.");
			return;
		}

		double completedRevenue = 0;
		double expectedRevenue = 0;
		int completedContracts = 0;
		int activeContracts = 0;

		for (RentalContract contract : rentalManager.getAllContracts()) {
			double finalCost = contract.calculateFinalCost();
			if (contract.isActive()) {
				activeContracts++;
				expectedRevenue += finalCost;
			} else {
				completedContracts++;
				completedRevenue += finalCost;
			}
		}

		System.out.println("Completed contracts: " + completedContracts);
		System.out.println("Active contracts:    " + activeContracts);
		System.out.println("Completed revenue:   $" + String.format("%.2f", completedRevenue));
		System.out.println("Expected active revenue: $" + String.format("%.2f", expectedRevenue));
		System.out.println("Total projected revenue: $" + String.format("%.2f", completedRevenue + expectedRevenue));
	}

	public void printMostRentedVehiclesReport() {
		System.out.println();
		System.out.println("========== MOST RENTED VEHICLES ==========");
		if (rentalManager.getAllContracts().isEmpty()) {
			System.out.println("No contracts found.");
			return;
		}

		Map<String, Integer> rentalCounts = new HashMap<>();
		Map<String, Vehicle> vehicleByPlate = new HashMap<>();

		for (RentalContract contract : rentalManager.getAllContracts()) {
			String plate = contract.getVehicle().getPlateNumber();
			rentalCounts.put(plate, rentalCounts.getOrDefault(plate, 0) + 1);
			vehicleByPlate.putIfAbsent(plate, contract.getVehicle());
		}

		List<Map.Entry<String, Integer>> sortedVehicles = new ArrayList<>(rentalCounts.entrySet());
		sortedVehicles.sort(Map.Entry.<String, Integer>comparingByValue(Comparator.reverseOrder()));

		for (Map.Entry<String, Integer> entry : sortedVehicles) {
			Vehicle vehicle = vehicleByPlate.get(entry.getKey());
			System.out.println(vehicle.getVehicleBasicInfo() + " -> Rentals: " + entry.getValue());
		}
	}

	public void printVipCustomersReport() {
		System.out.println();
		System.out.println("========== VIP CUSTOMERS ==========");
		if (rentalManager.getAllContracts().isEmpty()) {
			System.out.println("No contracts found.");
			return;
		}

		Map<String, Integer> completedContractsByCustomer = new HashMap<>();
		Map<String, Customer> customerById = new HashMap<>();

		for (RentalContract contract : rentalManager.getAllContracts()) {
			String customerId = contract.getCustomer().getCustomerId();
			customerById.putIfAbsent(customerId, contract.getCustomer());
			if (!contract.isActive()) {
				completedContractsByCustomer.put(customerId, completedContractsByCustomer.getOrDefault(customerId, 0) + 1);
			}
		}

		List<Map.Entry<String, Integer>> vipCustomers = new ArrayList<>();
		for (Map.Entry<String, Integer> entry : completedContractsByCustomer.entrySet()) {
			if (entry.getValue() >= 5) {
				vipCustomers.add(entry);
			}
		}

		vipCustomers.sort(Map.Entry.<String, Integer>comparingByValue(Comparator.reverseOrder()));

		if (vipCustomers.isEmpty()) {
			System.out.println("No VIP customers found with at least 5 completed contracts.");
			return;
		}

		for (Map.Entry<String, Integer> entry : vipCustomers) {
			Customer customer = customerById.get(entry.getKey());
			System.out.println(customer.getName() + " (" + customer.getCustomerId() + ") -> Completed contracts: " + entry.getValue());
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
			if (startDate.isBefore(from) == false && startDate.isAfter(to) == false) {
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
			if (value.isEmpty() == false) {
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



	public void showMenu(Scanner scanner) {
		boolean back = false;
		while (!back) {
			System.out.println();
			System.out.println("========== REPORT MENU ==========");
			System.out.println("1. Dashboard summary");
			System.out.println("2. Vehicles report");
			System.out.println("3. Customers report");
			System.out.println("4. Contracts report");
			System.out.println("5. Company revenue report");
			System.out.println("6. Most rented vehicles report");
			System.out.println("7. VIP customers report");
			System.out.println("8. Available vehicles report");
			System.out.println("9. Active rentals report");
			System.out.println("10. Overdue rentals report");
			System.out.println("11. Contracts by customer name");
			System.out.println("12. Company contracts report");
			System.out.println("13. Rentals in date range");
			System.out.println("14. Customers for a vehicle");
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
					printRevenueReport();
					break;
				case 6:
					printMostRentedVehiclesReport();
					break;
				case 7:
					printVipCustomersReport();
					break;
				case 8:
					printAvailableVehiclesReport();
					break;
				case 9:
					printActiveContractsReport();
					break;
				case 10:
					printOverdueContractsReport();
					break;
				case 11:
					printContractsByCustomerName(readNonEmpty(scanner, "Customer name: "));
					break;
				case 12:
					printCompanyContracts();
					break;
				case 13:
					printRentalsInPeriod(
							readDate(scanner, "From date (yyyy-MM-dd): "),
							readDate(scanner, "To date (yyyy-MM-dd): "));
					break;
				case 14:
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
}
