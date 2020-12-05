package com.davidholas.assignment.services;

import com.davidholas.assignment.model.Customer;
import com.davidholas.assignment.model.CustomerResource;
import com.davidholas.assignment.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
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

        Optional<Customer> customer = customerRepository.findById(customerId);

        return customer.get();
    }

    public void addCustomer(CustomerResource customerResource) {

        String name = customerResource.getName();

        Customer customer = new Customer(name);

        customerRepository.save(customer);
    }
}
