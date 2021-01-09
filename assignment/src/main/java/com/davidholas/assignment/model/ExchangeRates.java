package com.davidholas.assignment.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class ExchangeRates {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String base;

    private BigDecimal eur;

    private BigDecimal usd;

    private BigDecimal gbp;

    private String date;

    public ExchangeRates() {}

    public ExchangeRates(String base, BigDecimal eur, BigDecimal usd, BigDecimal gbp, String date) {
        this.base = base;
        this.eur = eur;
        this.usd = usd;
        this.gbp = gbp;
        this.date = date;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public BigDecimal getEur() {
        return eur;
    }

    public void setEur(BigDecimal eur) {
        this.eur = eur;
    }

    public BigDecimal getUsd() {
        return usd;
    }

    public void setUsd(BigDecimal usd) {
        this.usd = usd;
    }

    public BigDecimal getGbp() {
        return gbp;
    }

    public void setGbp(BigDecimal gbp) {
        this.gbp = gbp;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
