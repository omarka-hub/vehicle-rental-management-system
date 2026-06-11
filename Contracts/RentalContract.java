package Contracts;


import Customer.Customer;
import Vehicle.Vehicle;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;


public class RentalContract {
    
    private String contractId;
    private Vehicle vehicle;
    private Customer customer;
    private LocalDate startDate;
    private int rentalDays;
    private double baseCost;
    private LocalDate actualReturnDate;
    private int delayDays;
    private double earlyReturnCredit;
    private boolean isActive;

    public RentalContract(String contractId, Vehicle vehicle, Customer customer, LocalDate startDate, int rentalDays) {
        this.contractId = contractId;
        this.vehicle = vehicle;
        this.customer = customer;
        this.startDate = startDate;
        this.rentalDays = rentalDays;
        this.baseCost = vehicle.calculateRentalCost(rentalDays);
        this.isActive = true;
        this.delayDays = 0;
        this.earlyReturnCredit = 0;
        this.actualReturnDate = null;
    }

    public double calculateBaseCost() {
        return vehicle.calculateRentalCost(rentalDays);
    }

    public double calculatePenalty() {
        return 0.10 * delayDays * vehicle.getDailyPrice();
    }

    public double calculateDiscount() {
        return this.baseCost * this.customer.getDiscount();
    }

    public double calculateFinalCost() {
        return baseCost - calculateDiscount() - earlyReturnCredit + calculatePenalty();
    }

    public LocalDate getExpectedReturnDate() {
        return startDate.plusDays(rentalDays);
    }

    public void returnVehicle(LocalDate returnDate) {
        this.actualReturnDate = returnDate;
        
        LocalDate expectedDate = getExpectedReturnDate();
        long daysBetween = ChronoUnit.DAYS.between(expectedDate, returnDate);
        
        this.delayDays = (int) Math.max(0, daysBetween);
        
        if (ChronoUnit.DAYS.between(returnDate, expectedDate) >= 2) {
            this.earlyReturnCredit = baseCost * 0.03;
        } else {
            this.earlyReturnCredit = 0;
        }
        
        vehicle.setAvailable(true);
        this.isActive = false;
    }

    public void generateInvoice() {
        System.out.println("========== INVOICE ==========");
        System.out.println("Contract ID:   " + contractId);
        System.out.println("Customer:      " + customer.getName());
        System.out.println("Vehicle:       " + vehicle.getVehicleInfo());
        System.out.println("Start Date:    " + startDate);
        System.out.println("Rental Days:   " + rentalDays);
        System.out.println("Expected Return: " + getExpectedReturnDate());
        System.out.println("-----------------------------");
        System.out.println("Base Cost:     $" + baseCost);
        System.out.println("Discount:      -$" + calculateDiscount());
        System.out.println("Early Credit:  -$" + earlyReturnCredit);
        System.out.println("Penalty:       +$" + calculatePenalty());
        System.out.println("-----------------------------");
        System.out.println("Final Cost:    $" + calculateFinalCost());
        System.out.println("=============================");
    }

    public boolean isOverdue() {
        return delayDays > 0;
    }

    @Override
    public String toString() {
        return "Contract ID: " + contractId +
                "\nCustomer:    " + customer.getName() +
                "\nVehicle:     " + vehicle.getVehicleInfo() +
                "\nStart Date:  " + startDate +
                "\nRental Days: " + rentalDays +
                "\nExpected Return: " + getExpectedReturnDate() +
                "\nBase Cost:   $" + baseCost +
                "\nIs Active:   " + isActive +
                "\nOverdue:     " + isOverdue();
    }

    public String getContractId()   { return contractId; }
    public Vehicle getVehicle()     { return vehicle; }
    public Customer getCustomer()   { return customer; }
    public LocalDate getStartDate() { return startDate; }
    public boolean isActive()       { return isActive; }
}
