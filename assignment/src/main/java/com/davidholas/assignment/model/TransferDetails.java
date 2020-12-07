package com.davidholas.assignment.model;

public class TransferDetails {

    private Long withdrawalAccountId;

    private Long depositAccountId;

    private double amount;

    public TransferDetails(Long withdrawalAccountId, Long depositAccountId, double amount) {
        this.withdrawalAccountId = withdrawalAccountId;
        this.depositAccountId = depositAccountId;
        this.amount = amount;
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
