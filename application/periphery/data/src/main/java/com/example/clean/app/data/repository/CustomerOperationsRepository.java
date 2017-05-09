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
    public void delete(final Customer customer) {
        customerJpaRepository.delete(customer.getId().getId());
    }

    private CustomerEntity customerEntity(final Customer customer) {
        final CustomerEntity entity = new CustomerEntity();

        entity.setId(customer.getId().getId());
        entity.setName(name(customer.getName()));

        return entity;
    }

    private Name name(final com.example.clean.app.core.domain.Name customerName) {
        final Name name = new Name();
        name.setFirstName(customerName.firstName().value());
        name.setMiddleName(customerName.middleName().value());
        name.setLastName(customerName.lastName().value());
        name.setSuffix(customerName.suffix().value());

        return name;
    }
}
