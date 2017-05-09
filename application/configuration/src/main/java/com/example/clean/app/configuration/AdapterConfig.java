package com.example.clean.app.configuration;

import com.example.clean.app.adapter.web.CustomerAdapter;
import com.example.clean.app.core.boundary.enter.CustomerEditService;
import com.example.clean.app.core.boundary.enter.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdapterConfig {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerEditService customerEditService;

    @Bean
    public CustomerAdapter customerAdapter() {
        return new CustomerAdapter(customerService, customerEditService);
    }
}
