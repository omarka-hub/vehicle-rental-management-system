package Managers;

import Vehicle.Vehicle;
import java.util.ArrayList;

public class VehicleManager {
    
    private ArrayList<Vehicle> vehicles;

    public VehicleManager() {
        this.vehicles = new ArrayList<>();
    }

    public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
        System.out.println("Vehicle added successfully: " + vehicle.getVehicleInfo());
    }

    public void removeVehicle(String plateNumber) {
        Vehicle target = findByPlate(plateNumber);
        if (target != null) {
            vehicles.remove(target);
            System.out.println("Vehicle with plate " + plateNumber + " has been removed.");
        } else {
            System.out.println("Vehicle not found!");
        }
    }

    public Vehicle findByPlate(String plateNumber) {
        for (Vehicle v : vehicles) {
            if (v.getPlateNumber().equalsIgnoreCase(plateNumber)) {
                return v;
            }
        }
        return null;
    }

    public void displayAll() {
        System.out.println("--- All Vehicles ---");
        if (vehicles.isEmpty()) {
            System.out.println("No vehicles in the system.");
            return;
        }
        for (Vehicle v : vehicles) {
            System.out.println(v.toString());
        }
    }

    public void displayAvailable() {
        System.out.println("--- Available Vehicles ---");
        boolean found = false;
        for (Vehicle v : vehicles) {
            if (v.isAvailable()) {
                System.out.println(v.toString());
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
