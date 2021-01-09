package com.davidholas.assignment.config;

import com.davidholas.assignment.model.Account.Account;
import com.davidholas.assignment.model.Currency;
import com.davidholas.assignment.model.Customer.Customer;
import com.davidholas.assignment.model.Role;
import com.davidholas.assignment.repositories.AccountRepository;
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

    @Bean(name = "restTemplate")
    public RestTemplate prepareRestTemplate() {

        return new RestTemplate();
    }

    @Component
    public class DataLoader implements ApplicationRunner {

        private CustomerRepository customerRepository;

        private AccountRepository accountRepository;

        private RoleRepository roleRepository;

        private AccountService accountService;

        private ExchangeRatesService exchangeRatesService;

        private BCryptPasswordEncoder passwordEncoder;

        public DataLoader(PasswordEncoder passwordEncoder, CustomerRepository customerRepository,
                          AccountRepository accountRepository, AccountService accountService,
                          ExchangeRatesService exchangeRatesService, RoleRepository roleRepository) {
            this.passwordEncoder = passwordEncoder.bCryptPasswordEncoder();
            this.customerRepository = customerRepository;
            this.accountRepository = accountRepository;
            this.accountService = accountService;
            this.exchangeRatesService = exchangeRatesService;
            this.roleRepository = roleRepository;
        }

        public void run(ApplicationArguments args) {

            exchangeRatesService.saveExchangeRates();

            Customer c1 = customerRepository.save(new Customer("Bank admin"));
            Customer c2 = customerRepository.save(new Customer("David"));
            Customer c3 = customerRepository.save(new Customer("Monika"));

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
