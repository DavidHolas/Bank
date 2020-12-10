package com.davidholas.assignment.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Rates {

    @JsonProperty("EUR")
    private double eur;

    @JsonProperty("GBP")
    private double gbp;

    @JsonProperty("USD")
    private double usd;

    @JsonProperty("CHF")
    private double chf;

    public Rates() {
    }

    public Rates(double eur, double gbp, double usd, double chf) {
        this.eur = eur;
        this.gbp = gbp;
        this.usd = usd;
        this.chf = chf;
    }

    public double getEur() {
        return eur;
    }

    public void setEur(double eur) {
        this.eur = eur;
    }

    public double getGbp() {
        return gbp;
    }

    public void setGbp(double gbp) {
        this.gbp = gbp;
    }

    public double getUsd() {
        return usd;
    }

    public void setUsd(double usd) {
        this.usd = usd;
    }

    public double getChf() {
        return chf;
    }

    public void setChf(double chf) {
        this.chf = chf;
    }
}
