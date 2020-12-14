package com.davidholas.assignment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableScheduling
public class ProjectConfiguration {

    @Bean(name = "restTemplate")
    public RestTemplate prepareRestTemplate() {

        return new RestTemplate();
    }
}
