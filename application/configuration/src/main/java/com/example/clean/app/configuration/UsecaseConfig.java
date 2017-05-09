package com.example.clean.app.configuration;

import com.example.clean.app.core.boundary.enter.CustomerEditService;
import com.example.clean.app.core.boundary.enter.CustomerService;
import com.example.clean.app.core.boundary.exit.CustomerEditRepository;
import com.example.clean.app.core.boundary.exit.CustomerRepository;
import com.example.clean.app.core.usecase.CustomerDetailsUseCase;
import com.example.clean.app.core.usecase.CustomerEditUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UsecaseConfig {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerEditRepository customerEditRepository;

    @Bean
    public CustomerService customerService() {
        return new CustomerDetailsUseCase(customerRepository);
    }

    @Bean
    public CustomerEditService customerEditService() {
        return new CustomerEditUseCase(customerEditRepository);
    }
}
