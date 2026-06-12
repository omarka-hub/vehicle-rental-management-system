package Vehicle;

public class Car extends Vehicle {

    private FuelType fuelType;
    private boolean hasAC;
    private int numSeats; 


    public Car(String plateNumber, String brand, String model, double dailyPrice, FuelType fuelType, boolean hasAC, int numSeats) {
        super(plateNumber, brand, model, dailyPrice); 
        this.fuelType = fuelType;
        this.hasAC = hasAC;
        this.numSeats = numSeats;
    }


    @Override
    public double calculateRentalCost(int days) {
        double base = dailyPrice * days;
        double extra = 0;



        if (fuelType == FuelType.ELECTRIC) {
            extra += base * 0.05;
        }


        if (this.numSeats > 5) {
            extra += base * 0.03;
        }

        return base+extra  ;
    }


    public FuelType getFuelType() {
        return fuelType;
    }

    @Override
    public String toString() {
        return "Car{" +
                "fuelType=" + fuelType +
                ", hasAC=" + hasAC +
                ", numSeats=" + numSeats +
                ", brand='" + brand + '\'' +
                ", dailyPrice=" + dailyPrice +
                ", isAvailable=" + isAvailable +
                ", model='" + model + '\'' +
                ", plateNumber='" + plateNumber + '\'' +
                '}';
    }
} 