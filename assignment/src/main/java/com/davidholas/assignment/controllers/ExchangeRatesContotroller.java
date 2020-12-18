package com.davidholas.assignment.controllers;

import com.davidholas.assignment.model.Rates.ExchangeRatesResource;
import com.davidholas.assignment.services.ExchangeRatesService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/exchangeRates")
public class ExchangeRatesContotroller {

    private ExchangeRatesService exchangeRatesService;

    public ExchangeRatesContotroller(ExchangeRatesService exchangeRatesService) {
        this.exchangeRatesService =  exchangeRatesService;
    }

    @GetMapping("/{date}/{base}")
    public ExchangeRatesResource getExchangeRatesForDate(@PathVariable String date, @PathVariable String base) {

        ExchangeRatesResource exchangeRatesResource = exchangeRatesService.getExchangeRatesForDate(date, base);

        return exchangeRatesResource;
    }
}
