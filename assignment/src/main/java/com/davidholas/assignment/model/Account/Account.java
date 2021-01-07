package com.davidholas.assignment.model.Account;

import com.davidholas.assignment.model.Currency;
import com.davidholas.assignment.model.Customer.Customer;
import com.davidholas.assignment.model.TransferHistory;
import org.apache.tomcat.jni.Local;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int accountNumber;

    private BigDecimal balance = BigDecimal.valueOf(5000);

    @Enumerated(EnumType.STRING)
    private Currency currency = Currency.EUR;

    private LocalDate created;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public Account() {
    }

    public Account(int accountNumber, Customer customer) {
        this.created = LocalDate.now();
        this.accountNumber = accountNumber;
        this.customer = customer;
    }

    public Account(int accountNumber, Currency currency, Customer customer) {
        this.created = LocalDate.now();
        this.accountNumber = accountNumber;
        this.currency = currency;
        this.customer = customer;
    }

    public Account(Long id, int accountNumber, Customer customer) {
        this.created = LocalDate.now();
        this.id = id;
        this.accountNumber = accountNumber;
        this.customer = customer;
    }

    public Account(int accountNumber, BigDecimal balance) {
        this.created = LocalDate.now();
        this.accountNumber = accountNumber;
        this.balance = balance;
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

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
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

    public LocalDate getCreated() {
        return created;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }
}
