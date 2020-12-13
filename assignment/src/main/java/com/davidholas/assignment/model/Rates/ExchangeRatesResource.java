package com.davidholas.assignment.model.Rates;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.davidholas.assignment.model.Rates.RatesResource;

public class ExchangeRatesResource {

    @JsonProperty("base")
    private String base;

    @JsonProperty("date")
    private String date;

    @JsonProperty("rates")
    private RatesResource rates;

    public ExchangeRatesResource() {
    }

    public ExchangeRatesResource(String base, String date, RatesResource rates) {
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

    public RatesResource getRates() {
        return rates;
    }

    public void setRates(RatesResource rates) {
        this.rates = rates;
    }
}
