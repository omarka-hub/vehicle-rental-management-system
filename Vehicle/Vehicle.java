package Vehicle;

public abstract class Vehicle {

    protected String plateNumber;
    protected String brand;
    protected String model;
    protected double dailyPrice;
    protected boolean isAvailable;


    public Vehicle(String plateNumber, String brand, String model, double dailyPrice) {
        this.plateNumber = plateNumber;
        this.brand = brand;
        this.model = model;
        this.dailyPrice = dailyPrice;
        this.isAvailable = true;
    }

    public double getDailyPrice() {
        return this.dailyPrice;
    }


    public abstract double calculateRentalCost(int days);


    public String getVehicleInfo() {
        return brand + " " + model + " ( " + plateNumber + " ) ";
    }


    public void setAvailable(boolean status) {
        this.isAvailable = status;
    }


    public String getPlateNumber() {
        return plateNumber;
    }
    public boolean isAvailable(){return isAvailable;}



    @Override
    public String toString() {
        return "Vehicle{" +
                "plateNumber='" + plateNumber + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", dailyPrice=" + dailyPrice +
                ", isAvailable=" + isAvailable +
                '}';
    }
}
