package Customer;

public abstract class Customer {

    protected String customerId;
    protected String name;
    protected String address;
    protected String phoneNumber;

    public Customer(String customerId, String name,
                    String address, String phoneNumber) {
        this.customerId = customerId;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }


    public abstract double getDiscount();
    

    public String getCustomerInfo() {
        return "Customer ID: " + customerId +
                "\nName: " + name +
                "\nAddress: " + address +
                "\nPhone: " + phoneNumber;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return getCustomerInfo();
    }
}
