package com.example.clean.app.core.usecase;

import com.example.clean.app.core.boundary.enter.CustomerService;
import com.example.clean.app.core.boundary.exit.CustomerRepository;
import com.example.clean.app.core.domain.Customer;

import java.util.List;

import static org.apache.commons.lang3.Validate.notNull;

public class CustomerDetailsUseCase implements CustomerService {

    private CustomerRepository customerRepository;

    public CustomerDetailsUseCase(final CustomerRepository customerRepository) {
        this.customerRepository = notNull(customerRepository);
    }

    @Override
    public List<Customer> customers() {
        return customerRepository.customers();
    }

    @Override
    public Customer customer(final Customer.Id customerId) {
        notNull(customerId);

        return customerRepository.customer(customerId);
    }
}
