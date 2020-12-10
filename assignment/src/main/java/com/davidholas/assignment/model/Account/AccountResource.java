package com.davidholas.assignment.model.Account;

import com.davidholas.assignment.model.Currency;
import com.davidholas.assignment.model.Customer.Customer;

public class AccountResource {

    private Long id;

    private int accountNumber;

    private double balance;

    private Currency currency;

    private Customer customer;

    public AccountResource(Long id, int accountNumber, double balance, Currency currency, Customer customer) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.currency = currency;
        this.customer = customer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
