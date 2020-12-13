package com.davidholas.assignment.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

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

    private BigDecimal amount;

    private BigDecimal rate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy kk:mm:ss")
    private LocalDateTime time;

    public TransferHistory() {
    }

    public TransferHistory(Long withdrawalAccountId, Long depositAccountId, BigDecimal amount, BigDecimal rate) {
        this.withdrawalAccountId = withdrawalAccountId;
        this.depositAccountId = depositAccountId;
        this.amount = amount;
        this.rate = rate;
        this.time = LocalDateTime.now();
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
