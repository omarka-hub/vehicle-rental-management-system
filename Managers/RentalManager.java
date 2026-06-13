package Managers;

import java.time.LocalDate;
import java.util.ArrayList;
import Contracts.RentalContract;
import Customer.Company;
import Customer.Customer;
import Vehicle.Vehicle; 

public class RentalManager {

    private ArrayList<RentalContract> contracts;
    private VehicleManager vehicleManager;   
    private CustomerManager customerManager;
    private int nextContractId;

    public RentalManager(VehicleManager vehicleManager, CustomerManager customerManager) {
        this.contracts = new ArrayList<>();
        this.vehicleManager = vehicleManager;
        this.customerManager = customerManager;
        this.nextContractId = 1;
    }

    public void rentVehicle(String plate, String custId, LocalDate date, int days) {
        Vehicle vehicle = vehicleManager.findByPlate(plate);
        Customer customer = customerManager.findCustomer(custId);

        if (vehicle == null) {
            System.out.println("  ");
            System.out.println("Vehicle not found!");
            return;
        }
        if (vehicle.isAvailable() == false) {
            System.out.println("  ");
            System.out.println("Vehicle is not available!");
            return;
        }
        if (customer == null) {
            System.out.println("  ");
            System.out.println("Customer not found!");
            return;
        }
        if (days <= 0) {
            System.out.println("  ");
            System.out.println("Rental days cannot be 0 or less!");
            return;
        }

        String contractId = "C" + String.format("%03d", nextContractId);
        ++nextContractId;
        RentalContract contract = new RentalContract(contractId, vehicle, customer, date, days);
        vehicle.setAvailable(false);
        contracts.add(contract);
        System.out.println("Contract created: " + contractId);
    }

    public void returnVehicle(String contractId, LocalDate returnDate) {
        for (RentalContract contract : contracts) {
            if (contract.getContractId().equalsIgnoreCase(contractId)) {
                if (contract.isActive() == false) {
                    System.out.println("Contract is already closed!");
                    return;
                }
                contract.returnVehicle(returnDate);
                contract.generateInvoice();
                return;
            }
        }
        System.out.println("Contract not found!");
    }

    public void displayAllContracts(boolean activeOnly) {
        for (RentalContract contract : contracts) {
            if (activeOnly && contract.isActive() == false) continue;
            System.out.println(contract);
            System.out.println("----------------------");
        }
    }

    public void displayContractsByCustomerName(String name) {
        for (RentalContract contract : contracts) {
            if (contract.getCustomer().getName().equalsIgnoreCase(name)) {
                System.out.println(contract);
                System.out.println("----------------------");
            }
        }
    }

    public void displayContractsByCompanies() {
        for (RentalContract contract : contracts) {
            if (contract.getCustomer() instanceof Company) {
                System.out.println(contract);
                System.out.println("----------------------");
            }
        }
    }

    public void displayCurrentlyRented() {
        for (RentalContract contract : contracts) {
            if (contract.isActive()) {
                System.out.println(contract);
                System.out.println("----------------------");
            }
        }
    }

    public void displayOverdueVehicles() {
        for (RentalContract contract : contracts) {
            if (contract.isOverdue()) {
                System.out.println(contract);
                System.out.println("----------------------");
            }
        }
    }

    public void displayRentedInPeriod(LocalDate from, LocalDate to) {
        for (RentalContract contract : contracts) {
            LocalDate start = contract.getStartDate();
            if (start.isBefore(from) == false && start.isAfter(to) == false) {
                System.out.println(contract);
                System.out.println("----------------------");
            }
        }
    }
    

    public void displayCustomersForVehicle(String plate) {
        for (RentalContract contract : contracts) {
            if (contract.getVehicle().getPlateNumber().equalsIgnoreCase(plate)) {
                System.out.println(contract.getCustomer());
                System.out.println("----------------------");
            }
        }
    }

    public ArrayList<RentalContract> getAllContracts() {
        return contracts;
    }
}
