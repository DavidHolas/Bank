package com.davidholas.assignment.services;

import com.davidholas.assignment.exceptions.ResourceNotFoundException;
import com.davidholas.assignment.model.Address;
import com.davidholas.assignment.model.Customer.Customer;
import com.davidholas.assignment.model.Customer.CustomerResource;
import com.davidholas.assignment.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CustomerService {

    private CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllCustomers() {

        List<Customer> customers = customerRepository.findAll();

        return customers;
    }

    public Customer getCustomerById(Long customerId) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Account with id: " + customerId + " was not found."));

        return customer;
    }

    public void addCustomer(CustomerResource customerResource) {

        String firstName = customerResource.getFirstName();
        String lastName = customerResource.getLastName();
        String email = customerResource.getEmail();
        Address address = customerResource.getAddress();

        Customer customer = new Customer(firstName, lastName, email, address);

        customerRepository.save(customer);
    }
}
