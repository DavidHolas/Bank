package com.davidholas.assignment.model.Account;

import com.davidholas.assignment.model.Customer.Customer;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
public class SavingAccount extends Account {

    @Column(name = "interest_rate")
    private BigDecimal interestRate;

    public SavingAccount(int accountNumber, Customer customer, BigDecimal interestRate) {
        super(accountNumber, customer);
        this.interestRate = interestRate;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }
}
