package com.example.clean.app.data.repository;

import com.example.clean.app.core.boundary.exit.CustomerRepository;
import com.example.clean.app.core.domain.*;
import com.example.clean.app.data.jpa.entity.CustomerEntity;
import com.example.clean.app.data.jpa.entity.Name;
import com.example.clean.app.data.jpa.repository.CustomerJpaRepository;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.Validate.notNull;

public class CustomerDetailsRepository implements CustomerRepository {

    private CustomerJpaRepository customerJpaRepository;

    public CustomerDetailsRepository(final CustomerJpaRepository customerJpaRepository) {
        this.customerJpaRepository = notNull(customerJpaRepository);
    }

    @Override
    public List<Customer> customers() {
        final List<CustomerEntity> customers = customerJpaRepository.findAll();

        return customers.stream()
                .map(this::customer)
                .collect(toList());
    }

    @Override
    public Customer customer(final Customer.Id customerId) {
        final Optional<CustomerEntity> cust = customerJpaRepository.findById(customerId.getId());

        return cust.map(this::customer)
                   .orElseThrow(() -> new NoSuchElementException("Customer '" + customerId.getId() + "' does not exist"));
    }

    private Customer customer(final CustomerEntity customer) {
        return Customer.of(id(customer), name(customer.getName()));
    }

    private Customer.Id id(final CustomerEntity customer) {
        return Customer.Id.of(customer.getId());
    }

    private com.example.clean.app.core.domain.Name name(final Name name) {
        final String firstName = name.getFirstName();
        final String middleName = StringUtils.defaultIfBlank(name.getMiddleName(), "");
        final String lastName = name.getLastName();
        final String suffix = StringUtils.defaultIfBlank(name.getSuffix(), "");

        return ImmutableName.builder()
                            .firstName(ImmutableFirstName.of(firstName))
                            .middleName(ImmutableMiddleName.of(middleName))
                            .lastName(ImmutableLastName.of(lastName))
                            .suffix(ImmutableSuffix.of(suffix))
                            .build();
    }
}
