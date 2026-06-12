package Managers;

import Customer.Customer;
import java.util.ArrayList;

public class CustomerManager {

    private ArrayList<Customer> customers;

    public CustomerManager() {
        customers = new ArrayList<>();
    }

    public void addCustomer(Customer newcustomer) {
        for (Customer customer: customers) {
            if (customer.getCustomerId().equalsIgnoreCase(newcustomer.getCustomerId())) {
                System.out.println("ERROR!");
                System.out.println("A customer with that ID adress already exist!");
                return;
            }
        }
        customers.add(newcustomer);
    }

    public Customer findCustomer(String customerId) {

        for (Customer customer : customers) {
            if (customer.getCustomerId().equalsIgnoreCase(customerId)) {
                return customer;
            }
        }
        return null;
    }

    public Customer findByName(String name) {

        for (Customer customer : customers) {
            if (customer.getName().equalsIgnoreCase(name)) {
                return customer;
            }
        }
        return null;
    }

    public void displayAll() {

        if (customers.isEmpty()) {
            System.out.println("No customers found.");
            return;
        }

        for (Customer customer : customers) {
            System.out.println(customer);
            System.out.println("----------------------");
        }
    }

    public void updateCustomer(String id,
                               String field,
                               String value) {

        Customer customer = findCustomer(id);

        if (customer == null) {
            System.out.println("Customer not found!");
            return;
        }

        switch (field.toLowerCase()) {

            case "name":
                customer.setName(value);
                break;

            case "phone":
                customer.setPhoneNumber(value);
                break;

            case "address":
                customer.setAddress(value);
                break;

            default:
                System.out.println("Invalid field!");
        }
    }

    public ArrayList<Customer> getAllCustomers() {
        return customers;
    }
}
