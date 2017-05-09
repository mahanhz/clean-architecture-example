package com.example.clean.app.core.usecase;

import com.example.clean.app.core.boundary.enter.CustomerEditService;
import com.example.clean.app.core.boundary.exit.CustomerEditRepository;
import com.example.clean.app.core.domain.Customer;

import static org.apache.commons.lang3.Validate.notNull;

public class CustomerEditUseCase implements CustomerEditService {

    private CustomerEditRepository customerEditRepository;

    public CustomerEditUseCase(final CustomerEditRepository customerEditRepository) {
        this.customerEditRepository = notNull(customerEditRepository);
    }

    @Override
    public void create(final Customer customer) {
        notNull(customer);

        customerEditRepository.create(customer);
    }

    @Override
    public void update(final Customer customer) {
        notNull(customer);

        customerEditRepository.update(customer);
    }

    @Override
    public void delete(final Customer customer) {
        notNull(customer);

        customerEditRepository.delete(customer);
    }
}
