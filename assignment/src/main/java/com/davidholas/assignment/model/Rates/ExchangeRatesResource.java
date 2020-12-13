package com.davidholas.assignment.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ExchangeRatesResource {

    @JsonProperty("base")
    private String base;

    @JsonProperty("date")
    private String date;

    @JsonProperty("rates")
    private Rates rates;

    public ExchangeRatesResource() {
    }

    public ExchangeRatesResource(String base, String date, Rates rates) {
        this.base = base;
        this.date = date;
        this.rates = rates;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Rates getRates() {
        return rates;
    }

    public void setRates(Rates rates) {
        this.rates = rates;
    }
}
