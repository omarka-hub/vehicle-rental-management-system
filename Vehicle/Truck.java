package Vehicle;

public class Truck extends Vehicle {

    private double loadCapacity; 
    private boolean isRefrigerated;


    public Truck(String plateNumber, String brand, String model, double dailyPrice, double loadCapacity, boolean isRefrigerated) {
        super(plateNumber, brand, model, dailyPrice);
        this.loadCapacity = loadCapacity;
        this.isRefrigerated = isRefrigerated;
    }


    @Override
    public double calculateRentalCost(int days) {
        double base = dailyPrice * days;
        double extraPercent = 0;


        if (this.isRefrigerated) {
            extraPercent += 0.05;
        }


        if (this.loadCapacity > 5) {
            double extraTons = this.loadCapacity - 5;
            extraPercent += extraTons * 0.08;
        }


        if (extraPercent > 0.40) {
            extraPercent = 0.40;
        }

        return base + (base * extraPercent);
    }


    public boolean isRefrigerated() {
        return isRefrigerated;
    }

    @Override
    public String toString() {
        return super.toString() + " -> Truck{" +
                "loadCapacity=" + loadCapacity +
                ", isRefrigerated=" + isRefrigerated +
                '}';
    }
}