package Customer;

public class Company extends Customer {

    private String commercialRegister;
    private double discountRate;

    public Company(String customerId, String name,
                   String address, String phoneNumber,
                   String commercialRegister,
                   double discountRate) {

        super(customerId, name, address, phoneNumber);
        this.commercialRegister = commercialRegister;
        setDiscountRate(discountRate);
    }

    @Override
    public double getDiscount() {
        return discountRate;
    }

    public void setDiscountRate(double rate) {

        if (rate < 0)
            discountRate = 0;
        else if (rate > 0.30)
            discountRate = 0.30;
        else
            discountRate = rate;
    }

    @Override
    public String toString() {
        return super.toString() +
                "\nCommercial Register: " + commercialRegister +
                "\nDiscount Rate: " + (discountRate * 100) + "%";
    }
}
