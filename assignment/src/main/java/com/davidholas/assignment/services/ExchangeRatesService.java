package com.davidholas.assignment.services;

import com.davidholas.assignment.exceptions.InvalidInputException;
import com.davidholas.assignment.exceptions.ResourceNotFoundException;
import com.davidholas.assignment.exceptions.ResultNotUniqueException;
import com.davidholas.assignment.model.Currency;
import com.davidholas.assignment.model.ExchangeRates;
import com.davidholas.assignment.model.Rates.ExchangeRatesResource;
import com.davidholas.assignment.model.Rates.RatesResource;
import com.davidholas.assignment.repositories.ExchangeRatesRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.persistence.NonUniqueResultException;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ExchangeRatesService {

    private static final String FX_RATES_API = "https://api.exchangeratesapi.io/latest";
    public static final String CRON_MINUTE_AFTER_MIDNIGHT = "0 1 0 * * ?";
    public static final String CRON_EVERY_MINUTE = "0 * * ? * *";



    private RestTemplate restTemplate;

    private ExchangeRatesRepository exchangeRatesRepository;

    public ExchangeRatesService(RestTemplate restTemplate, ExchangeRatesRepository exchangeRatesRepository) {
        this.restTemplate = restTemplate;
        this.exchangeRatesRepository = exchangeRatesRepository;
    }

    public ExchangeRatesResource getExchangeRatesForDate(String date, String base) {

        ExchangeRates exchangeRates;
        RatesResource ratesResource;
        ExchangeRatesResource exchangeRatesResource = null;

        try {
            exchangeRates = exchangeRatesRepository.getExchangeRatesByDate(date, base)
                    .orElseThrow(() -> new ResourceNotFoundException("Exchange rate for " + base + " from date " + date + " were not found."));

            ratesResource = new RatesResource(exchangeRates.getEur(), exchangeRates.getUsd(), exchangeRates.getGbp());

            exchangeRatesResource = new ExchangeRatesResource(exchangeRates.getBase(), exchangeRates.getDate(), ratesResource);

        } catch(Exception e) {
            if(e instanceof NonUniqueResultException){
                throw new ResultNotUniqueException("Found more results for exchange rates");
            }
        }

        return exchangeRatesResource;
    }

    // Possible problem with dates. Need to check
    @Scheduled(cron = CRON_MINUTE_AFTER_MIDNIGHT)
    public void saveExchangeRates() {

        List<Currency> currencies = Arrays.asList(Currency.values());
        String date = LocalDate.now().toString();

        for(Currency currency : currencies) {

            //Check to not make duplicate
            Optional<ExchangeRates> exchangeRatesResourceOpt = exchangeRatesRepository.getExchangeRatesByDate(date, currency.toString());

            if(exchangeRatesResourceOpt.isPresent()) {
                throw new InvalidInputException("Exchange rate for " + currency.toString() + " from date " + date + " already exists.");
            }

            // Get exchange ratesResource from https://exchangeratesapi.io/
            ExchangeRatesResource exchangeRates = restTemplate.getForObject(FX_RATES_API + "?base=" + currency.toString(), ExchangeRatesResource.class);

            //Since EUR is a default base, there is not a rate for eur in response
            BigDecimal eur;
            if(currency.equals(Currency.EUR)) {
                eur = BigDecimal.valueOf(1.0);
            } else {
                eur = exchangeRates.getRates().getEur();
            }

            BigDecimal usd = exchangeRates.getRates().getUsd();
            BigDecimal chf = exchangeRates.getRates().getChf();
            ExchangeRates rates = new ExchangeRates(exchangeRates.getBase(), eur, usd, chf, exchangeRates.getDate());

            exchangeRatesRepository.save(rates);
        }
    }
}
