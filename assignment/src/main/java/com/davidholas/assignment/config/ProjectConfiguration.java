package com.davidholas.assignment.config;

import com.davidholas.assignment.model.Account.Account;
import com.davidholas.assignment.model.Address;
import com.davidholas.assignment.model.Currency;
import com.davidholas.assignment.model.Customer.Customer;
import com.davidholas.assignment.model.Role;
import com.davidholas.assignment.repositories.AccountRepository;
import com.davidholas.assignment.repositories.AddressRepository;
import com.davidholas.assignment.repositories.CustomerRepository;
import com.davidholas.assignment.repositories.RoleRepository;
import com.davidholas.assignment.security.PasswordEncoder;
import com.davidholas.assignment.services.AccountService;
import com.davidholas.assignment.services.ExchangeRatesService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableScheduling
public class ProjectConfiguration {

    private final String CZECH_REPUBLIC = "Česká Republika";

    @Bean(name = "restTemplate")
    public RestTemplate prepareRestTemplate() {

        return new RestTemplate();
    }

    @Component
    public class DataLoader implements ApplicationRunner {

        private CustomerRepository customerRepository;

        private AccountRepository accountRepository;

        private RoleRepository roleRepository;

        private AddressRepository addressRepository;

        private AccountService accountService;

        private ExchangeRatesService exchangeRatesService;

        private BCryptPasswordEncoder passwordEncoder;

        public DataLoader(PasswordEncoder passwordEncoder, CustomerRepository customerRepository,
                          AccountRepository accountRepository, AccountService accountService,
                          ExchangeRatesService exchangeRatesService, RoleRepository roleRepository,
                          AddressRepository addressRepository) {
            this.passwordEncoder = passwordEncoder.bCryptPasswordEncoder();
            this.customerRepository = customerRepository;
            this.accountRepository = accountRepository;
            this.accountService = accountService;
            this.exchangeRatesService = exchangeRatesService;
            this.roleRepository = roleRepository;
            this.addressRepository = addressRepository;
        }

        public void run(ApplicationArguments args) {

            exchangeRatesService.saveExchangeRates();

            Address bankAddress = addressRepository.save(new Address(CZECH_REPUBLIC, 56601, "Vysoké Mýto", "Slaninová", 666));
            Address c2Address = addressRepository.save(new Address(CZECH_REPUBLIC, 56601, "Vysoké Mýto", "Bůčková", 999));
            Address c3Address = addressRepository.save(new Address(CZECH_REPUBLIC, 53955, "Miřetice", "Dachov", 22));

            Customer c1 = customerRepository.save(new Customer("Bank", "Admin", "bankadmin@bank.cz", bankAddress));
            Customer c2 = customerRepository.save(new Customer("David", "Holas", "davidholas@bank.cz", c2Address));
            Customer c3 = customerRepository.save(new Customer("Monika", "Netolická", "monikanetolicka@bank.cz", c3Address));

            accountRepository.save(new Account(666, Currency.EUR, c1));
            accountRepository.save(new Account(101, Currency.USD, c2));
            accountRepository.save(new Account(102, Currency.GBP, c3));

            roleRepository.save(new Role("ADMIN"));
            roleRepository.save(new Role("USER"));

            /*TransferDetails td1 = new TransferDetails(2L, 1L, BigDecimal.valueOf(1000));
            TransferDetails td2 = new TransferDetails(3L, 1L, BigDecimal.valueOf(1000));

            accountService.transferMoney(td1);
            accountService.transferMoney(td2);*/
        }

    }
}
