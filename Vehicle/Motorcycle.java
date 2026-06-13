package Vehicle;

public class Motorcycle extends Vehicle {

    private double engineCapacity;
    private boolean hasSidecar;
    

    public Motorcycle(String plateNumber, String brand, String model, double dailyPrice, double engineCapacity, boolean hasSidecar) {
        super(plateNumber, brand, model, dailyPrice);
        this.engineCapacity = engineCapacity;
        this.hasSidecar = hasSidecar;
    }


    @Override
    public double calculateRentalCost(int days) {
        double base = dailyPrice * days;
        double extra = 0;

        if (hasSidecar) {
            extra += base * 0.02;
        }
        if (engineCapacity > 600) {
            extra += base * 0.10;
        }
        return base + extra;
    }


    public boolean hasSidecar() {
        return hasSidecar;
    }


    @Override
    public String toString() {
       return super.toString() +
        "\nType: Motorcycle" +
        "\nEngine: " + engineCapacity + "cc" +
        "\nSidecar: " + hasSidecar;

    }
} 

