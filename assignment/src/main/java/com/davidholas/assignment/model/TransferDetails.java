package com.davidholas.assignment.model;

import java.math.BigDecimal;

public class TransferDetails {

    private Long withdrawalAccountId;

    private Long depositAccountId;

    private BigDecimal amount;

    public TransferDetails(Long withdrawalAccountId, Long depositAccountId, BigDecimal amount) {
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
