package com.davidholas.assignment.model.Account;

import com.davidholas.assignment.model.Currency;
import com.davidholas.assignment.model.Customer.Customer;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
public class CheckingAccount extends Account {

    @Column(name = "managing_fee", nullable = false)
    private BigDecimal managingFee;

    public CheckingAccount(int accountNumber, Customer customer, BigDecimal managingFee) {
        super(accountNumber, customer);
        this.managingFee = managingFee;
    }

    public CheckingAccount(int accountNumber, Currency currency, Customer customer, BigDecimal managingFee) {
        super(accountNumber, currency, customer);
        this.managingFee = managingFee;
    }
}
