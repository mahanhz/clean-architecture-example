package com.example.clean.app.data.repository;

import com.example.clean.app.core.boundary.exit.CustomerEditRepository;
import com.example.clean.app.core.domain.Customer;
import com.example.clean.app.data.jpa.entity.CustomerEntity;
import com.example.clean.app.data.jpa.entity.Name;
import com.example.clean.app.data.jpa.repository.CustomerJpaRepository;

import static org.apache.commons.lang3.Validate.notNull;

public class CustomerOperationsRepository implements CustomerEditRepository {

    private CustomerJpaRepository customerJpaRepository;

    public CustomerOperationsRepository(final CustomerJpaRepository customerJpaRepository) {
        this.customerJpaRepository = notNull(customerJpaRepository);
    }

    @Override
    public void create(final Customer customer) {
        customerJpaRepository.save(customerEntity(customer));
    }

    @Override
    public void update(final Customer customer) {
        customerJpaRepository.save(customerEntity(customer));
    }

    @Override
    public void delete(final Customer.Id customerId) {
        customerJpaRepository.deleteById(customerId.getId());
    }

    private CustomerEntity customerEntity(final Customer customer) {
        final long id = customer.getId().getId();
        final Name name = name(customer.getName());

        return new CustomerEntity(id, name);
    }

    private Name name(final com.example.clean.app.core.domain.Name customerName) {
        final String firstName = customerName.firstName().value();
        final String middleName = customerName.middleName().value();
        final String lastName = customerName.lastName().value();
        final String suffix = customerName.suffix().value();

        return Name.create(firstName, middleName, lastName, suffix);
    }
}
