package com.davidholas.assignment.model.Rates;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class RatesResource {

    @JsonProperty("EUR")
    private BigDecimal eur;

    @JsonProperty("USD")
    private BigDecimal usd;

    @JsonProperty("CHF")
    private BigDecimal chf;

    public RatesResource() {
    }

    public RatesResource(BigDecimal eur, BigDecimal usd, BigDecimal chf) {
        this.eur = eur;
        this.usd = usd;
        this.chf = chf;
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

    public BigDecimal getChf() {
        return chf;
    }

    public void setChf(BigDecimal chf) {
        this.chf = chf;
    }
}
