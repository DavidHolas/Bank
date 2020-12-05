package com.davidholas.assignment.controllers;

import com.davidholas.assignment.model.Customer;
import com.davidholas.assignment.model.CustomerResource;
import com.davidholas.assignment.services.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/customers")
public class CustomerController {

    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/all")
    public List<Customer> getCustomerById() {

        List<Customer> customers = customerService.getAllCustomers();

        return customers;
    }

    @GetMapping("/customer/{customerId}")
    public Customer getCustomerById(@PathVariable Long customerId) {

        Customer customer = customerService.getCustomerById(customerId);

        return customer;
    }

    @PostMapping("/addCustomer}")
    public void addCustomer(@RequestBody CustomerResource customerResource) {

        customerService.addCustomer(customerResource);
    }
}
