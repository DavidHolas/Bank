package com.davidholas.assignment.model;

import javax.persistence.*;

@Entity
@Table(name = "transfer_history")
public class TransferHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "withdrawal_account")
    private Long withdrawalAccountId;

    @Column(name = "deposit_account")
    private Long depositAccountId;

    private double amount;

    public TransferHistory() {
    }

    public TransferHistory(Long withdrawalAccountId, Long depositAccountId, double amount) {
        this.withdrawalAccountId = withdrawalAccountId;
        this.depositAccountId = depositAccountId;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWithdrawalAccountId() {
        return withdrawalAccountId;
    }

    public void setWithdrawalAccountId(Long withdrawalAccountId) {
        this.withdrawalAccountId = withdrawalAccountId;
    }

    public Long getDepositAccountId() {
        return depositAccountId;
    }

    public void setDepositAccountId(Long depositAccountId) {
        this.depositAccountId = depositAccountId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
