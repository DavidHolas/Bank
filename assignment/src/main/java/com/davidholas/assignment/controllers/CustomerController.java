package com.davidholas.assignment.controllers;

import com.davidholas.assignment.model.Customer.Customer;
import com.davidholas.assignment.model.Customer.CustomerResource;
import com.davidholas.assignment.services.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<Customer>> getCustomerById() {

        List<Customer> customers = customerService.getAllCustomers();

        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long customerId) {

        Customer customer = customerService.getCustomerById(customerId);

        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @PostMapping("/addCustomer")
    public void addCustomer(@RequestBody CustomerResource customerResource) {

        customerService.addCustomer(customerResource);
    }
}
