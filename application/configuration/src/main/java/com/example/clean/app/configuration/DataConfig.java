package com.example.clean.app.configuration;

import com.example.clean.app.core.boundary.exit.CustomerEditRepository;
import com.example.clean.app.core.boundary.exit.CustomerRepository;
import com.example.clean.app.data.jpa.repository.CustomerJpaRepository;
import com.example.clean.app.data.repository.CustomerDetailsRepository;
import com.example.clean.app.data.repository.CustomerOperationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"com.example.clean.app.data.jpa.repository"})
public class DataConfig {

    @Autowired
    private CustomerJpaRepository customerJpaRepository;

    @Bean
    public CustomerRepository customerRepository() {
        return new CustomerDetailsRepository(customerJpaRepository);
    }

    @Bean
    public CustomerEditRepository customerEditRepository() {
        return new CustomerOperationsRepository(customerJpaRepository);
    }
}
