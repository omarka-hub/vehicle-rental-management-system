package Customer;

import java.time.LocalDate;
import java.time.Period;

public class Individual extends Customer {

    private String licenseNumber;
    private LocalDate dateOfBirth;

    public Individual(String customerId, String name,
                      String address, String phoneNumber,
                      String licenseNumber, LocalDate dateOfBirth) {

        super(customerId, name, address, phoneNumber);
        this.licenseNumber = licenseNumber;
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public double getDiscount() {
        return 0.0;
    }

    public int getAge() {
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }

    @Override
    public String toString() {
        return super.toString() +
                "\nLicense Number: " + licenseNumber +
                "\nDate Of Birth: " + dateOfBirth +
                "\nAge: " + getAge();
    }
}
