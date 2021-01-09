package com.davidholas.assignment.model;

import javax.persistence.*;

@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private int postalCode;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String street;

    @Column(nullable = false)
    private int propertyNumber;

    public Address() {
    }

    public Address(String country, int postalCode, String city, String street, int propertyNumber) {
        this.country = country;
        this.postalCode = postalCode;
        this.city = city;
        this.street = street;
        this.propertyNumber = propertyNumber;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getPropertyNumber() {
        return propertyNumber;
    }

    public void setPropertyNumber(int propertyNumber) {
        this.propertyNumber = propertyNumber;
    }
}
