package Managers;

import Vehicle.Vehicle;
import java.util.ArrayList;

public class VehicleManager {
    
    private ArrayList<Vehicle> vehicles;

    public VehicleManager() {
        this.vehicles = new ArrayList<>();
    }

    public void addVehicle(Vehicle newVehicle) {

        if (newVehicle.getDailyPrice() <= 0) {
            System.out.println("   ");
            System.out.println("Vehicle daily price cannot be 0 or less!");
            return;
        }

        for (Vehicle vehicle: vehicles) {
            if (vehicle.getPlateNumber().equalsIgnoreCase(newVehicle.getPlateNumber())) {
                System.out.println("  ");
                System.out.println("Error!");
                System.out.println("A vehicle with the same plate number already exist!");
                return;
            }
        }
        vehicles.add(newVehicle);
        System.out.println("  ");
        System.out.println("Vehicle added successfully: " + newVehicle.getVehicleBasicInfo());
    }

    public void removeVehicle(String plateNumber) {
        Vehicle target = findByPlate(plateNumber);
        if (target != null) {
            if (target.isAvailable() == false) {
                System.out.println("Cannot remove: vehicle is currently rented!");
                return;
            }
            vehicles.remove(target);
            System.out.println("Vehicle with plate " + plateNumber + " has been removed.");
        } else {
            System.out.println("Vehicle not found!");
        }
    }

    public Vehicle findByPlate(String plateNumber) {
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getPlateNumber().equalsIgnoreCase(plateNumber)) {
                return vehicle;
            }
        }
        return null;
    }

    public void displayAll() {
        if (vehicles.isEmpty()) {
            System.out.println("No vehicles in the system.");
            return;
        }
        System.out.println("--- All Vehicles ---");
        for (Vehicle vehicle : vehicles) {
            System.out.println("  ");
            System.out.println(vehicle);
            System.out.println("----------------------");
        }
    }

    public void displayAvailable() {
        System.out.println("--- Available Vehicles ---");
        boolean found = false;
        for (Vehicle vehicle : vehicles) {
            if (vehicle.isAvailable()) {
                System.out.println("  ");
                System.out.println(vehicle.toString());
                found = true;
            }
        }
        if (!found) {
            System.out.println("No available vehicles at the moment.");
        }
    }

    public ArrayList<Vehicle> getAllVehicles() {
        return vehicles;
    }
}
